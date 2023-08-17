package SemanticChecker;

import AST.ASTVisitor;
import AST.DefNodes.*;
import AST.ExprNodes.*;
import AST.StmtNodes.*;
import Utils.*;
import Utils.error.SemanticError;

import java.util.ArrayList;
import java.util.Objects;

public class SemanticChecker implements ASTVisitor {
    public GlobalScope globalScope;
    public Scope nowScope;


    public SemanticChecker(GlobalScope globalScope) {
        this.globalScope = globalScope;
        this.nowScope = globalScope;
    }

    public void checkType(String t) {
        //检查是否是已定义的类
        if (!globalScope.types.containsKey(t)) throw new SemanticError("Class " + t + " is not defined", null);
    }

    public void visit(ClassDefNode it) {
        //检查类方法和类成员的定义（类成员的检查已在SymbolCollector里基本完成）
        nowScope = new ClassScope(globalScope, it.classname);
        Type nowClass = globalScope.getTypeFromName(it.classname);
        //将类成员和类方法添加进当前scope里
        nowClass.members.forEach((name, type) -> nowScope.defineVariable(name, type, it.pos));
        nowClass.functions.forEach((name, func) -> ((ClassScope) nowScope).addFunc(name, func));
        for (DefNode nowNode : it.elements) {
            if (nowNode instanceof FuncDefNode funcDefNode) {
                funcDefNode.accept(this);
            } else if (nowNode instanceof VarDefNode varDefNode) {
                varDefNode.accept(this);
            }
        }
        nowScope = nowScope.parentScope();
        nowScope.returnType = null;
    }

    public void visit(FuncDefNode it) {
        //对每个statement进行检查、核对是否有返回值和返回值类型是否正确
        //进入函数作用域
        nowScope = new Scope(nowScope);
        nowScope.isFunc = true;
        if (it.isConstructor) {
            //特判构造函数，检查是否在类作用域里，检查名字对不对
            if (!nowScope.inClassScope(true)) {
                throw new SemanticError("Constructor must be in Class scope", it.pos);
            }
            String className = nowScope.findClassName();
            if (!className.equals(it.func_name)) {
                throw new SemanticError("Wrong Constructor", it.pos);
            }
        } else checkType(it.return_type.type_name);
        nowScope.correctReturnType = it.return_type; //todo 这是后加的，下面或许可以优化
        //把参数加进作用域里
        for (int i = 0; i < it.parameter_list.size(); ++i) {
            nowScope.defineVariable(it.getParameterName(i), it.getParameterType(i), it.pos);
        }
        for (StmtNode nowNode : it.statements) {
            nowNode.accept(this);
        }
        if (it.isConstructor || it.return_type.isVoid()) {
            if (nowScope.returnType != null) throw new SemanticError("Return type mismatch", it.pos);
        } else if (nowScope.returnType == null) {
            if (!it.func_name.equals("main")) throw new SemanticError("Missing return type", it.pos);
        } else if (!nowScope.returnType.equals(it.return_type)) throw new SemanticError("Return type mismatch", it.pos);
        nowScope = nowScope.parentScope();
    }

    public void visit(VarDefNode it) {
        //检查类型是否合理，如果不是类成员的定义，则将变量添加进当前作用域中，检查变量的重复定义已在Scope中完成,对变量初始化类型进行检查。

        for (int i = 0; i < it.define_list.size(); ++i) {
            if (it.getDefineListType(i).isVoid()) {
                throw new SemanticError("Variable type cannot be void", it.pos);
            }
            checkType(it.getDefineListType(i).type_name);

            //先检查初始化
            if (it.getDefineListInit(i) != null) {
                it.getDefineListInit(i).accept(this);
                if (it.getDefineListInit(i) instanceof NullNode) {
                    if (it.getDefineListType(i).isInt() || it.getDefineListType(i).isBool())
                        throw new SemanticError("Null cannot be assigned to primitive type variable", it.pos);
                } else if (!it.getDefineListInit(i).type.equals(it.getDefineListType(i))) {
                    throw new SemanticError("Type mismatch", it.pos);
                }
            }
            if (!nowScope.isClass) {
                //不是成员变量，添加到当前作用域中
                nowScope.defineVariable(it.getDefineListName(i), it.getDefineListType(i), it.pos);
            }
        }

    }

    public void visit(ArrayElementExprNode it) {
        //检查前面是不是array类型，index是不是int，更新自己的类型
        it.arrayExpr.accept(this);
        it.indexExpr.accept(this);
        if (it.arrayExpr.type.dimension == 0) {
            throw new SemanticError("This is not an array", it.pos);
        }
        if (!it.indexExpr.type.isInt()) {
            throw new SemanticError("Array index must be an int", it.pos);
        }
        it.type = new Type(it.arrayExpr.type.type_name, it.arrayExpr.type.dimension - 1);
    }

    public void visit(AssignExprNode it) {
        //检查左面是否可被赋值，检查左右类型是否一致（或者右面为null），更新自己的类型
        it.rhs.accept(this);
        it.lhs.accept(this);
        if (!(it.rhs instanceof NullNode)) {
            if (!it.lhs.type.equals(it.rhs.type)) throw new SemanticError("Assign expression type mismatch", it.pos);
        } else {
            if (it.lhs.type.isInt() || it.lhs.type.isBool())
                throw new SemanticError("Null cannot be assigned to primitive type variable", it.pos);
        }
        if (!it.lhs.isAssignable()) throw new SemanticError("Lhs is not assignable", it.pos);
        it.type = it.lhs.type;
    }

    public void visit(BinaryExprNode it) {
        //检查左右类型和运算符是否符合要求，更新自己的类型
        it.rhs.accept(this);
        it.lhs.accept(this);
        //null==null
        if (it.lhs instanceof NullNode || it.rhs instanceof NullNode) {
            if (!(it.op == BinaryExprNode.binaryOpType.equal || it.op == BinaryExprNode.binaryOpType.notEqual)) {
                throw new SemanticError("Null can only be in equal or unequal", it.pos);
            }
            it.type = new Type("bool");
            //可能还需要检查一下另一边是否为基本类型，错了再说
            //结果没错
            return;
        }
        //比较时只需相同类型即可
        if (it.op == BinaryExprNode.binaryOpType.greater || it.op == BinaryExprNode.binaryOpType.greaterEqual ||
                it.op == BinaryExprNode.binaryOpType.less || it.op == BinaryExprNode.binaryOpType.lessEqual ||
                it.op == BinaryExprNode.binaryOpType.equal || it.op == BinaryExprNode.binaryOpType.notEqual) {
            if (!it.lhs.type.equals(it.rhs.type))
                throw new SemanticError("Lhs and Rhs must have the same type", it.pos);
        } else if (it.op == BinaryExprNode.binaryOpType.plus) {
            //加法时，只能是int或string
            if (it.lhs.type.isInt()) {
                if (!it.rhs.type.isInt()) throw new SemanticError("Rhs must be an int", it.pos);
            } else if (it.lhs.type.isString()) {
                if (!it.rhs.type.isString()) throw new SemanticError("Rhs must be a string", it.pos);
            } else {
                throw new SemanticError("Lhs must be a string or an int", it.pos);
            }
        } else if (it.op == BinaryExprNode.binaryOpType.andand || it.op == BinaryExprNode.binaryOpType.oror) {
            //只能均为bool
            if (!it.lhs.type.isBool()) throw new SemanticError("Lhs must be an bool", it.pos);
            if (!it.rhs.type.isBool()) throw new SemanticError("Rhs must be an bool", it.pos);
        } else {
            if (!it.lhs.type.isInt()) throw new SemanticError("Lhs must be an int", it.pos);
            if (!it.rhs.type.isInt()) throw new SemanticError("Rhs must be an int", it.pos);
        }
        if (it.op == BinaryExprNode.binaryOpType.greater || it.op == BinaryExprNode.binaryOpType.greaterEqual ||
                it.op == BinaryExprNode.binaryOpType.less || it.op == BinaryExprNode.binaryOpType.lessEqual ||
                it.op == BinaryExprNode.binaryOpType.equal || it.op == BinaryExprNode.binaryOpType.notEqual) {
            it.type = new Type("bool");
        } else {
            it.type = it.lhs.type;
        }

    }

    public void visit(ErrorArrayExprNode it) {
        throw new SemanticError("Cannot initialize array in this way", it.pos);
    }

    public void visit(FunctionCallExprNode it) {
        //检查是否存在，核对参数类型,检查参数表达式，更新自己类型
        Type returnType = globalScope.getReturnTypeFromName(it.funcName);
        Function func = globalScope.func.get(it.funcName);
        //可能是在类内调用类方法
        if (nowScope.inClassScope(true)) {
            Function method = nowScope.findClassMethod(it.funcName);
            if (method != null) {
                func = method;
                returnType = nowScope.findClassMethod(it.funcName).returnType;
            }
        }
        if (returnType == null) throw new SemanticError("Function not found", it.pos);

        if (it.parameters.size() != func.parameterNum()) {
            throw new SemanticError((func.parameterNum()) + " parameters expected,but found " + it.parameters.size(), it.pos);
        }
        ArrayList<Type> parameterTypes = new ArrayList<>();
        for (int i = 0; i < it.parameters.size(); ++i) {
            it.parameters.get(i).accept(this);
            parameterTypes.add(it.parameters.get(i).type);
        }
        if (!func.parameterTypeCheck(parameterTypes)) {
            throw new SemanticError("parameter type mismatch", it.pos);
        }
        it.type = returnType;
    }

    public void visit(MemberExprNode it) {
        //检查是否存在类，检查是否存在该成员，更新自己类型
        it.familyExpr.accept(this);
        Type family = it.familyExpr.type;
        checkType(family.type_name);
        if (!globalScope.types.get(family.type_name).members.containsKey(it.memberName)) {
            throw new SemanticError("Member not found in class " + family.type_name, it.pos);
        }
        it.type = globalScope.types.get(family.type_name).members.get(it.memberName);
    }

    public void visit(NewArrayExprNode it) {
        //检查初始化大小是否是int，自己类型已在ASTBuilder里确定，检查一下是否存在就行
        for (ExprNode i : it.bounds) {
            i.accept(this);
            if (!i.type.isInt()) {
                throw new SemanticError("Index must be an int", it.pos);
            }
        }
        checkType(it.type.type_name);
    }

    public void visit(NewExprNode it) {
        //自己类型已在ASTBuilder里确定，检查一下是否存在就行
        checkType(it.type.type_name);
    }

    public void visit(PrefixExprNode it) {
        //检查是否是int,是否可被赋值，更新自己的类型
        it.expression.accept(this);
        if (!it.expression.type.isInt()) {
            throw new SemanticError("Must be an int", it.pos);
        }
        if (!it.expression.isAssignable()) {
            throw new SemanticError("Not assignable", it.pos);
        }
        it.type = new Type("int");
    }

    public void visit(SuffixExprNode it) {
        //检查是否是int,是否可被赋值，更新自己的类型
        it.expression.accept(this);
        if (!it.expression.type.isInt()) {
            throw new SemanticError("Must be an int", it.pos);
        }
        if (!it.expression.isAssignable()) {
            throw new SemanticError("Not assignable", it.pos);
        }
        it.type = new Type("int");
    }

    public void visit(TernaryExprNode it) {
        //检查三者类型，更新自己类型
        it.judgeExpr.accept(this);
        it.trueExpr.accept(this);
        it.falseExpr.accept(this);
        if (!it.judgeExpr.type.isBool()) {
            throw new SemanticError("Condition expression must be a bool", it.pos);
        }
        if (!((it.trueExpr instanceof NullNode) || (it.falseExpr instanceof NullNode))) {
            if (!it.trueExpr.type.equals(it.falseExpr.type)) {
                throw new SemanticError("The two expressions must have the same type", it.pos);
            }
        }
        if ((it.trueExpr instanceof NullNode) && (it.falseExpr instanceof NullNode)) {
            throw new SemanticError("The trueExpr and falseExpr cannot be null at same time", it.pos);
        }
        if (it.falseExpr instanceof NullNode) {
            it.type = it.trueExpr.type;
        } else {
            it.type = it.falseExpr.type;
        }
    }

    public void visit(UnaryExprNode it) {
        //检查expression类型，更新自己的类型
        it.expression.accept(this);
        if (it.op == UnaryExprNode.unaryOpType.not) {
            if (!it.expression.type.isBool()) {
                throw new SemanticError("Must be a bool", it.pos);
            }
        } else {
            if (!it.expression.type.isInt()) {
                throw new SemanticError("Must be an int", it.pos);
            }
        }
        it.type = it.expression.type;
    }

    //对所有涉及Scope的statement，都需要向上传一下返回值
    public void visit(BlockStmtNode it) {
        //检查每个statement，更新自己父作用域的returnType（return的合理性在ReturnStmtNode里检查）
        nowScope = new Scope(nowScope);
        for (int i = 0; i < it.stmts.size(); ++i) {
            it.stmts.get(i).accept(this);
        }
        if (nowScope.returnType != null) {
            if (nowScope.parentScope().returnType == null) {
                nowScope.parentScope().returnType = nowScope.returnType;
            }
        }
        nowScope = nowScope.parentScope();
    }

    public void visit(BreakStmtNode it) {
        if (!nowScope.inLoopScope(true)) {
            throw new SemanticError("Break statements can only be in Loop Scopes", it.pos);
        }
    }

    public void visit(ContinueStmtNode it) {
        if (!nowScope.inLoopScope(true)) {
            throw new SemanticError("Continue statements can only be in Loop Scopes", it.pos);
        }
    }

    public void visit(DefStmtNode it) {
        //变量添加已在VarDefNode里完成
        it.def.accept(this);
    }

    public void visit(EmptyStmtNode it) {
        //都empty了自然啥都不干
    }

    public void visit(ForStmtNode it) {
        //对相关节点进行检查，检查condition类型
        nowScope = new Scope(nowScope);
        nowScope.isLoop = true;
        if (it.condition != null) {
            it.condition.accept(this);
            if (!it.condition.type.isBool()) {
                throw new SemanticError("Loop condition must be a bool", it.pos);
            }
        }
        if (it.init != null) it.init.accept(this);//实际上init不可能为null，最多就是Empty而已
        if (it.step != null) it.step.accept(this);

        it.thenStmt.accept(this);
        if (nowScope.returnType != null) {
            if (nowScope.parentScope().returnType == null) {
                nowScope.parentScope().returnType = nowScope.returnType;
            }
        }
        nowScope = nowScope.parentScope();
    }

    public void visit(IfStmtNode it) {
        //对相关节点检查，检查condition类型,更新returnType
        it.condition.accept(this);

        if (!it.condition.type.isBool()) {
            throw new SemanticError("If statement condition must be a bool", it.pos);
        }
        //加两个Scope用来正确地更新returnType和解决Scope的问题
        nowScope = new Scope(nowScope);
        it.thenStmt.accept(this);
        Type thenStmtReturnType = nowScope.returnType;
        nowScope = nowScope.parentScope();
        nowScope = new Scope(nowScope);
        if (it.elseStmt != null) it.elseStmt.accept(this);
        Type elseStmtReturnType = nowScope.returnType;
        if (thenStmtReturnType != null && elseStmtReturnType != null) {
            nowScope.parentScope().returnType = thenStmtReturnType;
        }
        nowScope = nowScope.parentScope();
    }

    public void visit(PureExprStmtNode it) {
        //检查Expr
        it.expression.accept(this);
    }

    public void visit(ReturnStmtNode it) {
        //更新本作用域的返回值,检查return的位置是否合理
        if (it.return_expression != null) it.return_expression.accept(this);
        if (!nowScope.inFuncScope(true)) {
            throw new SemanticError("Return statements can only be in function scope", it.pos);
        }
        if (it.return_expression == null) {
            nowScope.returnType = null;
        } else {
            //特殊处理 return null
            if (it.return_expression instanceof NullNode) {
                if (nowScope.findReturnType().isVoid()) {
                    throw new SemanticError("Cannot return null in return-type-void function", it.pos);
                }
                nowScope.returnType = nowScope.findReturnType();
            } else {
                nowScope.returnType = it.return_expression.type;
            }
        }


        if (nowScope.findReturnType() == null || nowScope.findReturnType().isVoid()) {
            if (nowScope.returnType != null) throw new SemanticError("Return type mismatch", it.pos);
        } else if (!nowScope.returnType.equals(nowScope.findReturnType()))
            throw new SemanticError("Return type mismatch", it.pos);
    }

    public void visit(WhileStmtNode it) {
        it.condition.accept(this);
        nowScope = new Scope(nowScope);
        nowScope.isLoop = true;
        it.thenStmt.accept(this);
        if (!it.condition.type.isBool()) {
            throw new SemanticError("While statement condition must be a bool", it.pos);
        }
        nowScope = nowScope.parentScope();
    }

    public void visit(RootNode it) {
        //主要干两件事：按序收集全局变量（因为其不支持前向引用）；访问检查类、全局变量、函数的定义。
        for (DefNode nowNode : it.defs) {
            if (nowNode instanceof ClassDefNode classDefNode) {
                classDefNode.accept(this);
            } else if (nowNode instanceof FuncDefNode funcDefNode) {
                funcDefNode.accept(this);
            } else if (nowNode instanceof VarDefNode varDefNode) {
                varDefNode.accept(this);
            }
        }
    }

    public void visit(VarExprNode it) {
        if (!nowScope.containsVariable(it.name, true)) {
            throw new SemanticError("Variable not found", it.pos);
        }
        it.type = nowScope.getVarTypeFromName(it.name, true);
    }

    public void visit(StringLiteralNode it) {
        it.type = new Type("string");
    }

    public void visit(IntLiteralNode it) {
        it.type = new Type("int");
    }

    public void visit(BoolLiteralNode it) {
        it.type = new Type("bool");
    }

    public void visit(ThisNode it) {
        //检查是否在类作用域中
        if (!nowScope.inClassScope(true)) {
            throw new SemanticError("This can only be used in Class Scope", it.pos);
        }
        it.type = new Type(nowScope.findClassName());
    }

    public void visit(NullNode it) {
    }

    public void visit(ClassMethodCallExprNode it) {
        it.classExpr.accept(this);
        Type family = it.classExpr.type;
        if (family.dimension > 0) {
            //数组只有size方法
            if (it.methodName.equals("size")) {
                if (it.parameters.size() != 0) {
                    throw new SemanticError(0 + " parameters expected,but found " + it.parameters.size(), it.pos);
                }
                it.type = new Type("int");
                return;
            } else {
                throw new SemanticError("Method not found in class " + family.type_name, it.pos);
            }
        }
        checkType(family.type_name);
        if (!globalScope.types.get(family.type_name).functions.containsKey(it.methodName)) {
            throw new SemanticError("Method not found in class " + family.type_name, it.pos);
        }
        Function func = globalScope.types.get(family.type_name).functions.get(it.methodName);
        if (it.parameters.size() != func.parameterNum()) {
            throw new SemanticError((func.parameterNum()) + " parameters expected,but found " + it.parameters.size(), it.pos);
        }
        ArrayList<Type> parameterTypes = new ArrayList<>();
        for (int i = 0; i < it.parameters.size(); ++i) {
            it.parameters.get(i).accept(this);
            parameterTypes.add(it.parameters.get(i).type);
        }
        if (!func.parameterTypeCheck(parameterTypes)) {
            throw new SemanticError("parameter type mismatch", it.pos);
        }
        it.type = func.returnType;
    }
}
