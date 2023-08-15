package SemanticChecker;

import AST.ASTVisitor;
import AST.DefNodes.*;
import AST.ExprNodes.*;
import AST.StmtNodes.*;
import Utils.GlobalScope;
import Utils.Function;
import Utils.Type;
import Utils.error.SemanticError;

import java.util.Objects;

public class SymbolCollector implements ASTVisitor {
    public GlobalScope globalScope;
    public Function nowDefiningFunc = null;
    public Type nowDefiningClass = null;
    public boolean mainFuncDefined = false;

    public SymbolCollector(GlobalScope globalScope) {
        this.globalScope = globalScope;
        addBuildIn();
    }

    public void addBuildIn() {
        //加入已有的类型和方法
        Type stringType = new Type("string");
        stringType.becomeClassDef();
        Function ordFunc = new Function("ord", new Type("int"));
        ordFunc.addParameter("pos", new Type("int"));
        stringType.addFunction("ord", ordFunc);
        Function lengthFunc = new Function("length", new Type("int"));
        stringType.addFunction("length", lengthFunc);
        Function parseIntFunc = new Function("parseInt", new Type("int"));
        stringType.addFunction("parseInt", parseIntFunc);
        Function substringFunc = new Function("substring", new Type("string"));
        substringFunc.addParameter("left", new Type("int"));
        substringFunc.addParameter("right", new Type("int"));
        stringType.addFunction("substring", substringFunc);
        globalScope.addType("string", stringType);
        Type intType = new Type("int");
        intType.becomeClassDef();
        globalScope.addType("int", intType);
        Type boolType = new Type("bool");
        boolType.becomeClassDef();
        globalScope.addType("bool", boolType);
        Type voidType = new Type("void");
        voidType.becomeClassDef();
        globalScope.addType("void", voidType);
        //加入内建函数
        Function printFunc = new Function("print", new Type("void"));
        printFunc.addParameter("str", new Type("string"));
        globalScope.addFunc("print", printFunc);
        Function printlnFunc = new Function("println", new Type("void"));
        printlnFunc.addParameter("str", new Type("string"));
        globalScope.addFunc("println", printlnFunc);
        Function printIntFunc = new Function("printInt", new Type("void"));
        printIntFunc.addParameter("n", new Type("int"));
        globalScope.addFunc("printInt", printIntFunc);
        Function printlnIntFunc = new Function("printlnInt", new Type("void"));
        printlnIntFunc.addParameter("n", new Type("int"));
        globalScope.addFunc("printlnInt", printlnIntFunc);
        Function getStringFunc = new Function("getString", new Type("string"));
        globalScope.addFunc("getString", getStringFunc);
        Function getIntFunc = new Function("getInt", new Type("int"));
        globalScope.addFunc("getInt", getIntFunc);
        Function toStringFunc = new Function("toString", new Type("string"));
        toStringFunc.addParameter("i", new Type("int"));
        globalScope.addFunc("toString", toStringFunc);

    }

    public void visit(ClassDefNode it) {
        nowDefiningClass = new Type(it.classname);
        nowDefiningClass.becomeClassDef();
        for (DefNode defs : it.elements) {
            if (defs instanceof FuncDefNode funcDefNode) {
                funcDefNode.accept(this);
                if (Objects.equals(nowDefiningFunc.functionName, "main"))
                    throw new SemanticError("Main Function cannot be defined in class", funcDefNode.pos);
                nowDefiningClass.addFunction(nowDefiningFunc.functionName, nowDefiningFunc);
                nowDefiningFunc = null;
            } else if (defs instanceof VarDefNode varDefNode) {
                for (int i = 0; i < varDefNode.define_list.size(); ++i) {
                    if (varDefNode.haveInit(i))
                        throw new SemanticError("Class Members cannot be initialized in Mx*", varDefNode.pos);
                    if (varDefNode.getDefineListType(i).isVoid())
                        throw new SemanticError("Variables cannot be defined as void", varDefNode.pos);
                    nowDefiningClass.addMember(varDefNode.getDefineListName(i), varDefNode.getDefineListType(i));
                }
            } else if (defs instanceof ClassDefNode classDefNode) {
                throw new SemanticError("Class cannot be declared in a class in Mx*", classDefNode.pos);
            }
        }
    }

    public void visit(FuncDefNode it) {
        nowDefiningFunc = new Function(it.func_name, it.return_type);
        if (it.isConstructor) nowDefiningFunc.isConstructor = true;
        nowDefiningFunc.returnType = it.return_type;
        nowDefiningFunc.functionName = it.func_name;
        for (int i = 0; i < it.parameter_list.size(); ++i) {
            nowDefiningFunc.addParameter(it.getParameterName(i), it.getParameterType(i));
        }
    }

    public void visit(VarDefNode it) {
    }

    public void visit(ArrayElementExprNode it) {
    }

    public void visit(AssignExprNode it) {
    }

    public void visit(BinaryExprNode it) {
    }

    public void visit(ErrorArrayExprNode it) {
    }

    public void visit(FunctionCallExprNode it) {
    }

    public void visit(MemberExprNode it) {
    }

    public void visit(NewArrayExprNode it) {
    }

    public void visit(NewExprNode it) {
    }

    public void visit(PrefixExprNode it) {
    }

    public void visit(SuffixExprNode it) {
    }

    public void visit(TernaryExprNode it) {
    }

    public void visit(UnaryExprNode it) {
    }

    public void visit(BlockStmtNode it) {
    }

    public void visit(BreakStmtNode it) {
    }

    public void visit(ContinueStmtNode it) {
    }

    public void visit(DefStmtNode it) {
    }

    public void visit(EmptyStmtNode it) {
    }

    public void visit(ForStmtNode it) {
    }

    public void visit(IfStmtNode it) {
    }

    public void visit(PureExprStmtNode it) {
    }

    public void visit(ReturnStmtNode it) {
    }

    public void visit(WhileStmtNode it) {
    }

    public void visit(RootNode it) {
        for (DefNode nowNode : it.defs) {
            if (nowNode instanceof ClassDefNode classDefNode) {
                classDefNode.accept(this);
                if (globalScope.func.containsKey(nowDefiningClass.type_name)) {
                    throw new SemanticError("Class name conflict with function name", it.pos);
                }
                globalScope.addType(nowDefiningClass.type_name, nowDefiningClass);
                nowDefiningClass = null;
            } else if (nowNode instanceof FuncDefNode funcDefNode) {
                funcDefNode.accept(this);
                if (globalScope.types.containsKey(nowDefiningFunc.functionName)) {
                    throw new SemanticError("Function name conflict with class name", it.pos);
                }
                globalScope.addFunc(nowDefiningFunc.functionName, nowDefiningFunc);
                if (Objects.equals(funcDefNode.func_name, "main")) {
                    if (!mainFuncDefined) mainFuncDefined = true;
                    else {
                        throw new SemanticError("More than one main functions", funcDefNode.pos);
                    }
                }
                nowDefiningFunc = null;
            } else if (nowNode instanceof VarDefNode varDefNode) {
                varDefNode.accept(this);
                //由于不支持全局变量前向引用，所以不用在此处做什么。
            }
        }
        if (!mainFuncDefined) {
            throw new SemanticError("No main function", null);
        }
    }

    public void visit(VarExprNode it) {
    }

    public void visit(StringLiteralNode it) {
    }

    public void visit(IntLiteralNode it) {
    }

    public void visit(BoolLiteralNode it) {
    }

    public void visit(ThisNode it) {
    }

    public void visit(NullNode it) {
    }

    public void visit(ClassMethodCallExprNode it) {
    }
}
