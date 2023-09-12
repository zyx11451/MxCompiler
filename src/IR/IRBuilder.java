package IR;

import AST.ASTVisitor;
import AST.DefNodes.*;
import AST.ExprNodes.*;
import AST.StmtNodes.*;
import IR.IRDefs.*;
import IR.IRTypes.*;
import IR.entity.*;
import IR.inst.*;
import Utils.GlobalScope;
import Utils.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class IRBuilder implements ASTVisitor {
    public IRRoot root;
    public GlobalScope MxGlobalScope;
    public HashMap<String, IRClassDef> types;
    public IRFunctionDef nowDefining = null;//正在定义哪个函数
    public IRClassDef nowDefiningClass = null;
    public IRBlock nowBuildingBlock = null; //正在写哪个块。
    public IRBlock nowForStepBlock = null;//最里层for循环的step块，用于continue的跳转
    public IRBlock nowForEndBlock = null;//最里层
    public IRScope nowScope;
    public int blockNum = 0;
    public int variableNum = 0;
    public int constStringNum = 0;
    public int globalVariableNum = 0;
    String This = "%_this";//this指针，类方法
    IRVariable nowThis=null;
    IRFunctionDef initGlobal = new IRFunctionDef("_initGlobal", new Type("void"));
    HashSet<String> nowClassMethodNames = null;

    public IRBuilder(IRRoot root, GlobalScope globalScope) {
        this.root = root;
        this.MxGlobalScope = globalScope;
        this.types = new HashMap<>();
        this.nowScope = new IRScope(null);
    }
    public IRBlock getNewBlock() {
        ++blockNum;
        return new IRBlock("_b." + Integer.toString(blockNum));
    }

    public IRVariable getNewVariable(IRType t) {
        ++variableNum;
        return new IRVariable(t, "%_" + variableNum);
    }

    public IRVariable getNewGlobal(IRType t) {
        ++globalVariableNum;
        return new IRVariable(t, "@_" + globalVariableNum);
    }

    public IRVariable getNewConstString() {
        ++constStringNum;
        return new IRVariable(new IRPointerType(new IRSimpleType(8)), "@.str" + constStringNum);
    }

    public IRVariable getPointerTarget(IRVariable v) {
        //因为变量都是指针形式，所以要靠这个获取它们的值，用于return等场合
        //能做左值的返回指针
        //根据节点是否assignable 确定是否扒一层指针
        if (v.type instanceof IRPointerType) {
            IRVariable ans = getNewVariable(((IRPointerType) v.type).targetType);
            nowBuildingBlock.addStatements(new IRLoad(ans, v));
            return ans;
        } else {
            return v;
        }
    }

    IRType TransTypeToIRType(Type type) {
        if (type.dimension == 0) {
            if (type.isInt()) return new IRSimpleType(32);
            else if (type.isBool()) return new IRSimpleType(1);
            else if (type.isString()) return new IRPointerType(new IRSimpleType(8));
            else return new IRPointerType(new IRBasicClassType(type.type_name));
        } else {
            Type elementType = new Type(type.type_name, type.dimension - 1);
            return new IRPointerType(new IRBasicArrayType(TransTypeToIRType(elementType)));
        }

    }

    public void visit(ClassDefNode it) {
        //类本身已先手添加
        nowScope = new IRScope(nowScope);
        nowClassMethodNames = new HashSet<>();
        nowScope.isClassScope = true;
        nowDefiningClass = types.get(it.classname);
        nowThis=new IRVariable(new IRPointerType(new IRBasicClassType(it.classname)), This);
        nowDefiningClass.index.forEach((s, i) -> nowScope.var.put(s, new IRVariable(new IRSimpleType(32), "should_not_in_IR_" + s)));
        it.elements.forEach(defNode -> {
            if (defNode instanceof FuncDefNode) {
                nowClassMethodNames.add(((FuncDefNode) defNode).funcName);
            }
        });
        it.elements.forEach(defNode -> {
            if (defNode instanceof FuncDefNode) {
                defNode.accept(this);
                nowDefining.functionName = it.classname + "." + nowDefining.functionName;
                nowDefining.parameters.add(0, nowThis);
                root.definitions.add(nowDefining);
            }
        });
        if (!nowDefiningClass.constructFunctionDefined) {
            IRFunctionDef constructor = new IRFunctionDef(it.classname + "." + it.classname, new Type("void"));
            constructor.functionBody.add(new IRRet(new IRVoidEntity()));
            root.definitions.add(constructor);
        }
        nowScope = nowScope.parent;
        nowClassMethodNames = null;
    }

    public void visit(FuncDefNode it) {
        nowScope = new IRScope(nowScope);
        nowBuildingBlock = getNewBlock();
        if (it.funcName.equals("main")) {
            //main函数
            nowDefining = new IRFunctionDef("main", it.returnType);
            nowDefining.functionBody.add(new IRCall("_initGlobal", new IRVoidType()));
            nowDefining.functionBody.add(new IRUnconditionalBr(nowBuildingBlock));
        } else {
            if (it.returnType != null) {
                nowDefining = new IRFunctionDef(it.funcName, it.returnType);
            } else {
                nowDefiningClass.constructFunctionDefined = true;
                nowDefining = new IRFunctionDef(it.funcName, new Type("void"));
                it.returnType = new Type("void");//构造AST的时候sb了
            }

            for (int i = 0; i < it.parameterList.size(); ++i) {
                IRType t = TransTypeToIRType(it.getParameterType(i));
                IRVariable v = getNewVariable(t);
                IRVariable p = getNewVariable(new IRPointerType(t));
                nowBuildingBlock.addStatements(new IRAlloca(p, t));
                nowBuildingBlock.addStatements(new IRStore(v, (IRPointerType) p.type, p));
                nowDefining.parameters.add(v);
                nowScope.var.put(it.getParameterName(i), p);
            }
        }

        nowDefining.functionBody.add(nowBuildingBlock);
        it.statements.forEach(t -> t.accept(this));
        nowScope = nowScope.parent;
        /*if (it.func_name.equals("main")) {
            //main函数
            nowDefining.functionBody.add(new ret(new constantValue(0, new IRSimpleType(32))));
        }
        if (it.return_type.isVoid()) {
            //void返回值函数
            nowDefining.functionBody.add(new ret(new voidEntity()));
        }*/
        //清除空块，但理论上不应有空块
        IRRet defaultRet;
        if (it.returnType.isInt()) defaultRet = new IRRet(new IRConstantValue(0, new IRSimpleType(32)));
        else if (it.returnType.isBool()) defaultRet = new IRRet(new IRConstantBool(false));
        else if (it.returnType.isVoid()) defaultRet = new IRRet(new IRVoidEntity());
        else defaultRet = new IRRet(new IRNull());
        for (int i = nowDefining.functionBody.size() - 1; i >= 0; --i) {
            if (!(nowDefining.functionBody.get(i) instanceof IRBlock)) continue;
            if (((IRBlock) nowDefining.functionBody.get(i)).statements.isEmpty()) {
                ((IRBlock) nowDefining.functionBody.get(i)).statements.add(defaultRet);
            }
            boolean haveTerminal=false;
            for(int j = 0; j<((IRBlock) nowDefining.functionBody.get(i)).statements.size(); ++j){
                IRInst in=((IRBlock) nowDefining.functionBody.get(i)).statements.get(j);
                if(in instanceof IRUnconditionalBr ||in instanceof IRConditionalBr ||in instanceof IRRet){
                    haveTerminal=true;
                    break;
                }
            }
            if(!haveTerminal){
                ((IRBlock) nowDefining.functionBody.get(i)).statements.add(defaultRet);
            }
        }
    }

    public void visit(VarDefNode it) {
        if (nowScope.parent == null) {
            //全局变量
            nowDefining = initGlobal;
            if (initGlobal.functionBody.isEmpty()) {
                nowBuildingBlock = getNewBlock();
                nowDefining.functionBody.add(nowBuildingBlock);
            } else nowBuildingBlock = (IRBlock) initGlobal.functionBody.get(initGlobal.functionBody.size() - 1);

            for (int i = 0; i < it.defineList.size(); ++i) {
                IRType t = TransTypeToIRType(it.getDefineListType(i));
                IRVariable v = getNewGlobal(new IRPointerType(t));
                root.definitions.add(new IRGlobalDef(v.name, t));
                if (it.getDefineListInit(i) != null) {
                    it.getDefineListInit(i).accept(this);
                    IREntity init = it.getDefineListInit(i).ent;
                    if (it.getDefineListInit(i).isAssignable()) {
                        IRVariable v1 = getNewVariable(((IRPointerType) init.getType()).targetType);
                        nowBuildingBlock.addStatements(new IRLoad(v1, (IRVariable) init));
                        init = v1;
                    }
                    nowBuildingBlock.addStatements(new IRStore(init, (IRPointerType) v.type, v));
                }
                nowScope.var.put(it.getDefineListName(i), v);
            }
            nowDefining = null;
            return;
        }
        //先不考虑全局变量
        for (int i = 0; i < it.defineList.size(); ++i) {
            IRType t = TransTypeToIRType(it.getDefineListType(i));
            IRVariable v = getNewVariable(new IRPointerType(t));
            nowBuildingBlock.addStatements(new IRAlloca(v, t));
            if (it.getDefineListInit(i) != null) {
                it.getDefineListInit(i).accept(this);
                IREntity init = it.getDefineListInit(i).ent;
                if (it.getDefineListInit(i).isAssignable()) {
                    IRVariable v1 = getNewVariable(((IRPointerType) init.getType()).targetType);
                    nowBuildingBlock.addStatements(new IRLoad(v1, (IRVariable) init));
                    init = v1;
                }
                nowBuildingBlock.addStatements(new IRStore(init, (IRPointerType) v.type, v));
            }
            nowScope.var.put(it.getDefineListName(i), v);
        }
    }

    public void visit(ArrayElementExprNode it) {
        it.arrayExpr.accept(this);
        it.indexExpr.accept(this);
        IREntity ar = it.arrayExpr.ent;
        IREntity in = it.indexExpr.ent;
        if (it.arrayExpr.isAssignable()) {
            IRVariable v = getNewVariable(((IRPointerType) ar.getType()).targetType);
            nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) ar));
            ar = v;
        }
        if (it.indexExpr.isAssignable()) {
            IRVariable v = getNewVariable(((IRPointerType) in.getType()).targetType);
            nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) in));
            in = v;
        }
        //数组实际上为指向数组头的指针，数组的每个元素都占4个bytes
        IRType elementType = ((IRBasicArrayType) ((IRPointerType) ar.getType()).targetType).elementType;
        IRVariable ans = getNewVariable(new IRPointerType(elementType));
        IRGetElementPtr getArrayElementPointer = new IRGetElementPtr(new IRSimpleType(32), (IRVariable) ar, ans);
        getArrayElementPointer.addIdx(in);
        nowBuildingBlock.addStatements(getArrayElementPointer);
        it.ent = ans;
    }

    public void visit(AssignExprNode it) {
        it.lhs.accept(this);
        it.rhs.accept(this);
        IREntity lhs = it.lhs.ent;
        IREntity rhs = it.rhs.ent;
        if (it.rhs.isAssignable()) {
            IRVariable v = getNewVariable(((IRPointerType) rhs.getType()).targetType);
            nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) rhs));
            rhs = v;
        }
        nowBuildingBlock.addStatements(new IRStore(rhs, (IRPointerType) lhs.getType(), (IRVariable) lhs));
    }

    public void visit(BinaryExprNode it) {

        if (it.lhs.type.isInt()) {
            it.lhs.accept(this);
            it.rhs.accept(this);
            if (it.type.isInt()) {
                //i32运算指令
                IREntity lhs = it.lhs.ent;
                IREntity rhs = it.rhs.ent;
                if (it.lhs.isAssignable()) {
                    IRVariable v = getNewVariable(new IRSimpleType(32));
                    nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) lhs));
                    lhs = v;
                }
                if (it.rhs.isAssignable()) {
                    IRVariable v = getNewVariable(new IRSimpleType(32));
                    nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) rhs));
                    rhs = v;
                }
                IRVariable ans = getNewVariable(new IRSimpleType(32));
                if (it.op == BinaryExprNode.binaryOpType.plus) {
                    nowBuildingBlock.addStatements(new IRBinary(ans, 32, lhs, rhs, IRBinary.opType.add));
                } else if (it.op == BinaryExprNode.binaryOpType.minus) {
                    nowBuildingBlock.addStatements(new IRBinary(ans, 32, lhs, rhs, IRBinary.opType.sub));
                } else if (it.op == BinaryExprNode.binaryOpType.multiply) {
                    nowBuildingBlock.addStatements(new IRBinary(ans, 32, lhs, rhs, IRBinary.opType.mul));
                } else if (it.op == BinaryExprNode.binaryOpType.divide) {
                    nowBuildingBlock.addStatements(new IRBinary(ans, 32, lhs, rhs, IRBinary.opType.sdiv));
                } else if (it.op == BinaryExprNode.binaryOpType.mod) {
                    nowBuildingBlock.addStatements(new IRBinary(ans, 32, lhs, rhs, IRBinary.opType.srem));
                } else if (it.op == BinaryExprNode.binaryOpType.or) {
                    nowBuildingBlock.addStatements(new IRBinary(ans, 32, lhs, rhs, IRBinary.opType.or));
                } else if (it.op == BinaryExprNode.binaryOpType.and) {
                    nowBuildingBlock.addStatements(new IRBinary(ans, 32, lhs, rhs, IRBinary.opType.and));
                } else if (it.op == BinaryExprNode.binaryOpType.caret) {
                    nowBuildingBlock.addStatements(new IRBinary(ans, 32, lhs, rhs, IRBinary.opType.xor));
                } else if (it.op == BinaryExprNode.binaryOpType.leftShift) {
                    nowBuildingBlock.addStatements(new IRBinary(ans, 32, lhs, rhs, IRBinary.opType.shl));
                } else if (it.op == BinaryExprNode.binaryOpType.rightShift) {
                    nowBuildingBlock.addStatements(new IRBinary(ans, 32, lhs, rhs, IRBinary.opType.ashr));
                }
                it.ent = ans;
            } else {
                //icmp指令
                IREntity lhs = it.lhs.ent;
                IREntity rhs = it.rhs.ent;
                if (it.lhs.isAssignable()) {
                    IRVariable v = getNewVariable(new IRSimpleType(32));
                    nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) lhs));
                    lhs = v;
                }
                if (it.rhs.isAssignable()) {
                    IRVariable v = getNewVariable(new IRSimpleType(32));
                    nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) rhs));
                    rhs = v;
                }
                IRVariable ans = getNewVariable(new IRSimpleType(1));
                if (it.op == BinaryExprNode.binaryOpType.equal) {
                    nowBuildingBlock.addStatements(new IRCompare(ans, 32, lhs, rhs, IRCompare.condType.eq));
                } else if (it.op == BinaryExprNode.binaryOpType.notEqual) {
                    nowBuildingBlock.addStatements(new IRCompare(ans, 32, lhs, rhs, IRCompare.condType.ne));
                } else if (it.op == BinaryExprNode.binaryOpType.less) {
                    nowBuildingBlock.addStatements(new IRCompare(ans, 32, lhs, rhs, IRCompare.condType.slt));
                } else if (it.op == BinaryExprNode.binaryOpType.lessEqual) {
                    nowBuildingBlock.addStatements(new IRCompare(ans, 32, lhs, rhs, IRCompare.condType.sle));
                } else if (it.op == BinaryExprNode.binaryOpType.greater) {
                    nowBuildingBlock.addStatements(new IRCompare(ans, 32, lhs, rhs, IRCompare.condType.sgt));
                } else if (it.op == BinaryExprNode.binaryOpType.greaterEqual) {
                    nowBuildingBlock.addStatements(new IRCompare(ans, 32, lhs, rhs, IRCompare.condType.sge));
                }
                it.ent = ans;
            }
        } else if (it.lhs.type.isBool()) {
            //（短路求值）逻辑运算
            IRVariable ans = getNewVariable(new IRSimpleType(1));
            it.lhs.accept(this);
            IREntity lhs = it.lhs.ent;
            if (it.lhs.isAssignable()) {
                IRVariable v = getNewVariable(new IRSimpleType(1));
                nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) lhs));
                lhs = v;
            }
            IRBlock itBlock = nowBuildingBlock;//nowBuildingBlock最后要变成endBlock
            IRBlock rhsBlock = getNewBlock();
            nowBuildingBlock = rhsBlock;
            it.rhs.accept(this);
            IREntity rhs = it.rhs.ent;
            if (it.rhs.isAssignable()) {
                IRVariable v = getNewVariable(new IRSimpleType(1));
                nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) rhs));
                rhs = v;
            }
            IRBlock endBlock = getNewBlock();
            nowBuildingBlock = endBlock;//之后就一直是endBlock
            IRPhi phiInst = new IRPhi(ans, ans.type);
            phiInst.valLabel.put(itBlock, lhs);
            phiInst.valLabel.put(rhsBlock, rhs);
            endBlock.addStatements(phiInst);
            if (it.op == BinaryExprNode.binaryOpType.oror) {
                //为itBlock和rhsBlock添加跳转
                itBlock.addStatements(new IRConditionalBr(lhs, endBlock, rhsBlock));
                rhsBlock.addStatements(new IRUnconditionalBr(endBlock));
                //把新增block添加进nowDefining中（block没有包含关系）
                nowDefining.functionBody.add(rhsBlock);
                nowDefining.functionBody.add(endBlock);
            } else if (it.op == BinaryExprNode.binaryOpType.andand) {
                //为itBlock和rhsBlock添加跳转
                itBlock.addStatements(new IRConditionalBr(lhs, rhsBlock, endBlock));
                rhsBlock.addStatements(new IRUnconditionalBr(endBlock));
                //把新增block添加进nowDefining中（block没有包含关系）
                nowDefining.functionBody.add(rhsBlock);
                nowDefining.functionBody.add(endBlock);
            }
            it.ent = ans;
        } else if (it.lhs.type.isString()) {
            if (it.op == BinaryExprNode.binaryOpType.plus) {
                //string加法
                IRVariable ans = getNewVariable(new IRPointerType(new IRSimpleType(8)));
                IRCall stringPlus = new IRCall("_string.plus", new IRPointerType(new IRSimpleType(8)));
                stringPlus.result = ans;
                it.lhs.accept(this);
                it.rhs.accept(this);
                IREntity lhs = it.lhs.ent;
                IREntity rhs = it.rhs.ent;
                if (it.lhs.isAssignable()) {
                    IRVariable v = getNewVariable(new IRPointerType(new IRSimpleType(8)));
                    nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) lhs));
                    lhs = v;
                }
                if (it.rhs.isAssignable()) {
                    IRVariable v = getNewVariable(new IRPointerType(new IRSimpleType(8)));
                    nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) rhs));
                    rhs = v;
                }
                stringPlus.parameters.add(lhs);
                stringPlus.parameters.add(rhs);
                nowBuildingBlock.addStatements(stringPlus);
                it.ent = ans;
            } else {
                //string比较
                IRVariable ans = getNewVariable(new IRSimpleType(1));
                IRCall stringCmp = null;
                if (it.op == BinaryExprNode.binaryOpType.equal) {
                    stringCmp = new IRCall("_string.eq", new IRSimpleType(1));
                } else if (it.op == BinaryExprNode.binaryOpType.notEqual) {
                    stringCmp = new IRCall("_string.ne", new IRSimpleType(1));
                } else if (it.op == BinaryExprNode.binaryOpType.greater) {
                    stringCmp = new IRCall("_string.g", new IRSimpleType(1));
                } else if (it.op == BinaryExprNode.binaryOpType.greaterEqual) {
                    stringCmp = new IRCall("_string.ge", new IRSimpleType(1));
                } else if (it.op == BinaryExprNode.binaryOpType.less) {
                    stringCmp = new IRCall("_string.l", new IRSimpleType(1));
                } else if (it.op == BinaryExprNode.binaryOpType.lessEqual) {
                    stringCmp = new IRCall("_string.le", new IRSimpleType(1));
                }
                it.lhs.accept(this);
                it.rhs.accept(this);
                IREntity lhs = it.lhs.ent;
                IREntity rhs = it.rhs.ent;
                if (it.lhs.isAssignable()) {
                    IRVariable v = getNewVariable(new IRPointerType(new IRSimpleType(8)));
                    nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) lhs));
                    lhs = v;
                }
                if (it.rhs.isAssignable()) {
                    IRVariable v = getNewVariable(new IRPointerType(new IRSimpleType(8)));
                    nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) rhs));
                    rhs = v;
                }
                assert stringCmp != null;
                stringCmp.result = ans;
                stringCmp.parameters.add(lhs);
                stringCmp.parameters.add(rhs);
                nowBuildingBlock.addStatements(stringCmp);
                it.ent = ans;
            }
        } else {
            //类==null
            IRVariable ans = getNewVariable(new IRSimpleType(1));
            it.lhs.accept(this);
            it.rhs.accept(this);
            IREntity lhs = it.lhs.ent;
            IREntity rhs = it.rhs.ent;
            if (it.lhs.isAssignable()) {
                IRVariable v = getNewVariable(((IRPointerType) lhs.getType()).targetType);
                nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) lhs));
                lhs = v;
            }
            if (it.rhs.isAssignable()) {
                IRVariable v = getNewVariable(((IRPointerType) rhs.getType()).targetType);
                nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) rhs));
                rhs = v;
            }
            if (it.op == BinaryExprNode.binaryOpType.equal) {
                nowBuildingBlock.addStatements(new IRCompare(ans, lhs.getType(), lhs, rhs, IRCompare.condType.eq));
            } else {
                nowBuildingBlock.addStatements(new IRCompare(ans, lhs.getType(), lhs, rhs, IRCompare.condType.ne));
            }
            it.ent = ans;
        }


    }

    public void visit(ErrorArrayExprNode it) {
        //会在SemanticCheck阶段寄掉
    }

    public void visit(FunctionCallExprNode it) {
        //类内调用类成员函数
        if (nowClassMethodNames != null) {
            if (nowClassMethodNames.contains(it.funcName)) {
                it.parameters.forEach(p -> p.accept(this));
                IRCall callFunc = new IRCall(nowDefiningClass.name + "." + it.funcName, TransTypeToIRType(it.type));
                callFunc.parameters.add(nowThis);
                it.parameters.forEach(p -> {
                    IREntity pV = p.ent;
                    if (p.isAssignable()) {
                        IRVariable v = getNewVariable(((IRPointerType) p.ent.getType()).targetType);
                        nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) pV));
                        pV = v;
                    }
                    callFunc.parameters.add(pV);
                });
                if (!(callFunc.returnType instanceof IRVoidType)) {
                    IRVariable rt = getNewVariable(callFunc.returnType);
                    callFunc.result = rt;
                    it.ent = rt;
                }
                nowBuildingBlock.addStatements(callFunc);
                return;
            }
        }
        it.parameters.forEach(p -> p.accept(this));
        IRCall callFunc = new IRCall(it.funcName, TransTypeToIRType(it.type));
        it.parameters.forEach(p -> {
            IREntity pV = p.ent;
            if (p.isAssignable()) {
                IRVariable v = getNewVariable(((IRPointerType) p.ent.getType()).targetType);
                nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) pV));
                pV = v;
            }
            callFunc.parameters.add(pV);
        });
        if (!(callFunc.returnType instanceof IRVoidType)) {
            IRVariable rt = getNewVariable(callFunc.returnType);
            callFunc.result = rt;
            it.ent = rt;
        }
        nowBuildingBlock.addStatements(callFunc);
    }

    public void visit(MemberExprNode it) {
        it.familyExpr.accept(this);
        IREntity family = it.familyExpr.ent;//此处应该是class**或this
        if (it.familyExpr.isAssignable()) {
            IRVariable v = getNewVariable(((IRPointerType) family.getType()).targetType);
            nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) family));
            family = v;
        }
        //此处family变为class*
        int pos = types.get(it.familyExpr.type.type_name).index.get(it.memberName);
        IRType targetType = types.get(it.familyExpr.type.type_name).elements.get(pos);
        IRVariable ans = getNewVariable(new IRPointerType(targetType));
        IRGetElementPtr gep = new IRGetElementPtr(((IRPointerType) family.getType()).targetType, (IRVariable) family, ans);
        gep.idx.add(new IRConstantValue(0, new IRSimpleType(32)));//取第0个class
        gep.idx.add(new IRConstantValue(pos, new IRSimpleType(32)));//取class的第pos个元素
        nowBuildingBlock.addStatements(gep);
        it.ent = ans;
    }

    public IRVariable newArray(ArrayList<ExprNode> in, IRType elementType, int dimension) {

        IRVariable ans = getNewVariable(new IRPointerType(elementType));//真正的数组0号位开头
        in.get(0).accept(this);
        IREntity size = in.get(0).ent;
        if (in.get(0).isAssignable()) {
            IRVariable v = getNewVariable(((IRPointerType) size.getType()).targetType);
            nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) size));
            size = v;
        }
        IRVariable ints = getNewVariable(new IRSimpleType(32));
        nowBuildingBlock.addStatements(new IRBinary(ints, ints.getType(), size, new IRConstantValue(1, new IRSimpleType(32)), IRBinary.opType.add));
        IRVariable bytes = getNewVariable(new IRSimpleType(32));
        nowBuildingBlock.addStatements(new IRBinary(bytes, bytes.getType(), ints, new IRConstantValue(4, new IRSimpleType(32)), IRBinary.opType.mul));
        IRVariable arrayHead = getNewVariable(new IRPointerType(elementType));//数组这段空间的一开头
        IRCall mallocFunc = new IRCall("_malloc", arrayHead.getType());
        mallocFunc.result = arrayHead;
        mallocFunc.parameters.add(bytes);
        nowBuildingBlock.addStatements(mallocFunc);
        IRVariable arraySizePtr = getNewVariable(new IRPointerType(new IRSimpleType(32)));
        IRGetElementPtr getArraySizePtr = new IRGetElementPtr(new IRSimpleType(32), arrayHead, arraySizePtr);
        getArraySizePtr.addIdx(new IRConstantValue(0, new IRSimpleType(32)));
        nowBuildingBlock.addStatements(getArraySizePtr);
        nowBuildingBlock.addStatements(new IRStore(size, (IRPointerType) arraySizePtr.type, arraySizePtr));
        IRGetElementPtr getTrueArrayHeadPtr = new IRGetElementPtr(new IRSimpleType(32), arrayHead, ans);//令ans是数组0号位开头
        getTrueArrayHeadPtr.addIdx(new IRConstantValue(1, new IRSimpleType(32)));
        nowBuildingBlock.addStatements(getTrueArrayHeadPtr);
        //需要：一个循环来对所有元素进行内存分配和调用构造函数，必须是写在IR里的，因为我不知道数组大小
        IRVariable counter = getNewVariable(new IRPointerType(new IRSimpleType(32)));
        nowBuildingBlock.addStatements(new IRAlloca(counter, new IRSimpleType(32)));
        nowBuildingBlock.addStatements(new IRStore(new IRConstantValue(0, new IRSimpleType(32)), (IRPointerType) counter.type, counter));
        IRBlock condBlk = getNewBlock();
        IRBlock stepBlk = getNewBlock();
        IRBlock loopBlk = getNewBlock();
        IRBlock endBlk = getNewBlock();
        nowBuildingBlock.addStatements(new IRUnconditionalBr(condBlk));
        IRVariable counterValue1 = getNewVariable(new IRSimpleType(32));
        condBlk.addStatements(new IRLoad(counterValue1, counter));
        IRVariable cmp = getNewVariable(new IRSimpleType(1));
        condBlk.addStatements(new IRCompare(cmp, 32, counterValue1, size, IRCompare.condType.slt));
        condBlk.addStatements(new IRConditionalBr(cmp, loopBlk, endBlk));
        nowDefining.functionBody.add(condBlk);
        IRVariable counterValue2 = getNewVariable(new IRSimpleType(32));
        stepBlk.addStatements(new IRLoad(counterValue2, counter));
        IRVariable newCounterValue = getNewVariable(new IRSimpleType(32));
        stepBlk.addStatements(new IRBinary(newCounterValue, 32, counterValue2, new IRConstantValue(1, new IRSimpleType(32)), IRBinary.opType.add));
        stepBlk.addStatements(new IRStore(newCounterValue, (IRPointerType) counter.type, counter));
        stepBlk.addStatements(new IRUnconditionalBr(condBlk));
        nowDefining.functionBody.add(stepBlk);
        nowBuildingBlock = loopBlk;
        IRVariable counterValue3 = getNewVariable(new IRSimpleType(32));
        nowBuildingBlock.addStatements(new IRLoad(counterValue3, counter));
        IRVariable elementPtr = getNewVariable(new IRPointerType(elementType));
        IRGetElementPtr getArrayElementPtr = new IRGetElementPtr(elementType, ans, elementPtr);
        getArrayElementPtr.addIdx(counterValue3);
        nowBuildingBlock.addStatements(getArrayElementPtr);
        if (elementType instanceof IRPointerType) {
            if (((IRPointerType) elementType).targetType instanceof IRBasicClassType) {
                //为类分配内存，调用构造函数
                IRVariable element = getNewVariable(elementType);
                String className = ((IRBasicClassType) ((IRPointerType) elementType).targetType).className;
                nowBuildingBlock.addStatements(new IRAlloca(element, new IRBasicClassType(className)));
                IRCall construct = new IRCall(className + "." + className, new IRVoidType());
                construct.parameters.add(element);
                nowBuildingBlock.addStatements(construct);
                nowBuildingBlock.addStatements(new IRStore(element, (IRPointerType) elementPtr.type, elementPtr));
            } else if (((IRPointerType) elementType).targetType instanceof IRBasicArrayType) {
                //构造下一层
                if (in.size() > 1) {
                    ArrayList<ExprNode> newIn = new ArrayList<>();
                    for (int i = 1; i < in.size(); ++i) {
                        newIn.add(in.get(i));
                    }
                    IRVariable element = (IRVariable) newArray(newIn, ((IRBasicArrayType) ((IRPointerType) elementType).targetType).elementType, dimension - 1);
                    nowBuildingBlock.addStatements(new IRStore(element, (IRPointerType) elementPtr.type, elementPtr));
                } else {
                    //如果是最后一层，则把所有元素都赋值为null
                    nowBuildingBlock.addStatements(new IRStore(new IRNull(), (IRPointerType) elementPtr.type, elementPtr));
                }
            }
        }
        nowBuildingBlock.addStatements(new IRUnconditionalBr(stepBlk));
        nowDefining.functionBody.add(loopBlk);
        nowDefining.functionBody.add(endBlk);
        nowBuildingBlock = endBlk;
        return ans;
    }

    public void visit(NewArrayExprNode it) {
        it.ent = newArray(it.bounds, ((IRBasicArrayType) ((IRPointerType) TransTypeToIRType(it.type)).targetType).elementType, it.type.dimension);
    }

    public void visit(NewExprNode it) {
        IRVariable v = getNewVariable(new IRPointerType(new IRBasicClassType(it.type.type_name)));
        IRCall mallocFunc = new IRCall("_malloc", new IRPointerType(null));
        mallocFunc.result = v;
        mallocFunc.parameters.add(new IRConstantValue(types.get(it.type.type_name).elements.size() * 4L, new IRSimpleType(32)));
        nowBuildingBlock.addStatements(mallocFunc);
        IRCall construct = new IRCall(it.type.type_name + "." + it.type.type_name, new IRVoidType());
        construct.parameters.add(v);
        nowBuildingBlock.addStatements(construct);//添加构造函数
        it.ent = v;
    }

    public void visit(PrefixExprNode it) {
        //--a,++a,按可被赋值而设计
        it.expression.accept(this);
        IRSimpleType t = new IRSimpleType(32);
        IRPointerType p = new IRPointerType(t);
        IRVariable bef = getNewVariable(t);
        IRVariable aft = getNewVariable(t);
        nowBuildingBlock.addStatements(new IRLoad(bef, (IRVariable) it.expression.ent));
        long add;
        if (it.op == PrefixExprNode.prefixOpType.addadd) {
            add = 1;
        } else {
            add = -1;
        }
        nowBuildingBlock.addStatements(new IRBinary(aft, 32, bef, new IRConstantValue(add, t), IRBinary.opType.add));
        nowBuildingBlock.addStatements(new IRStore(aft, p, (IRVariable) it.expression.ent));
        it.ent = it.expression.ent;
    }

    public void visit(SuffixExprNode it) {
        //a++;a--
        //理论上来讲expression一定是个指针（可被赋值）
        it.expression.accept(this);
        IRSimpleType t = new IRSimpleType(32);
        IRPointerType p = new IRPointerType(t);
        IRVariable bef = getNewVariable(t);
        IRVariable aft = getNewVariable(t);
        nowBuildingBlock.addStatements(new IRLoad(bef, (IRVariable) it.expression.ent));
        long add;
        if (it.op == SuffixExprNode.suffixOpType.addadd) {
            add = 1;
        } else {
            add = -1;
        }
        nowBuildingBlock.addStatements(new IRBinary(aft, 32, bef, new IRConstantValue(add, t), IRBinary.opType.add));
        nowBuildingBlock.addStatements(new IRStore(aft, p, (IRVariable) it.expression.ent));
        it.ent = bef;
    }

    public void visit(TernaryExprNode it) {
        it.judgeExpr.accept(this);
        IREntity cond = it.judgeExpr.ent;
        if (it.judgeExpr.isAssignable()) {
            IRVariable v = getNewVariable(new IRSimpleType(1));
            nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) cond));
            cond = v;
        }
        IRBlock itBlk = nowBuildingBlock;
        IRBlock trueBlk = getNewBlock();
        IRBlock falseBlk = getNewBlock();
        IRBlock endBlk = getNewBlock();
        itBlk.addStatements(new IRConditionalBr(cond, trueBlk, falseBlk));
        nowBuildingBlock = trueBlk;
        it.trueExpr.accept(this);
        IREntity t = it.trueExpr.ent;
        if (it.trueExpr.isAssignable()) {
            IRVariable v = getNewVariable(((IRPointerType) t.getType()).targetType);
            nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) t));
            t = v;
        }
        nowBuildingBlock.addStatements(new IRUnconditionalBr(endBlk));
        nowDefining.functionBody.add(trueBlk);
        IRBlock trueBlkEndBlk = nowBuildingBlock;
        nowBuildingBlock = falseBlk;
        it.falseExpr.accept(this);
        IREntity f = it.falseExpr.ent;
        if (it.falseExpr.isAssignable()) {
            IRVariable v = getNewVariable(((IRPointerType) f.getType()).targetType);
            nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) f));
            f = v;
        }
        nowBuildingBlock.addStatements(new IRUnconditionalBr(endBlk));
        nowDefining.functionBody.add(falseBlk);
        IRBlock falseBlkEndBlk = nowBuildingBlock;
        nowBuildingBlock = endBlk;
        nowDefining.functionBody.add(endBlk);
        IRVariable ans = getNewVariable(t.getType());
        IRPhi phiInst = new IRPhi(ans, ans.getType());
        phiInst.valLabel.put(trueBlkEndBlk, t);
        phiInst.valLabel.put(falseBlkEndBlk, f);
        endBlk.addStatements(phiInst);
        it.ent = ans;
    }

    public void visit(UnaryExprNode it) {
        it.expression.accept(this);
        if (it.expression.type.isBool()) {
            //必然是not
            IREntity expr = it.expression.ent;
            if (it.expression.isAssignable()) {
                IRVariable v = getNewVariable(new IRSimpleType(1));
                nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) expr));
                expr = v;
            }
            IRVariable ans = getNewVariable(new IRSimpleType(1));
            nowBuildingBlock.addStatements(new IRBinary(ans, 1, expr, new IRConstantBool(true), IRBinary.opType.xor));
            it.ent = ans;
        } else {
            IREntity expr = it.expression.ent;
            if (it.expression.isAssignable()) {
                IRVariable v = getNewVariable(new IRSimpleType(32));
                nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) expr));
                expr = v;
            }
            if (it.op == UnaryExprNode.unaryOpType.tilde) {
                IRVariable ans = getNewVariable(new IRSimpleType(32));
                nowBuildingBlock.addStatements(new IRBinary(ans, 32, expr, new IRConstantValue(-1, new IRSimpleType(32)), IRBinary.opType.xor));
                it.ent = ans;
            } else if (it.op == UnaryExprNode.unaryOpType.minus) {
                IRVariable ans = getNewVariable(new IRSimpleType(32));
                nowBuildingBlock.addStatements(new IRBinary(ans, 32, new IRConstantValue(0, new IRSimpleType(32)), expr, IRBinary.opType.sub));
                it.ent = ans;
            } else {
                it.ent = expr;
            }
        }
    }

    public void visit(BlockStmtNode it) {
        nowScope = new IRScope(nowScope);
        it.stmts.forEach(s -> s.accept(this));
        nowScope = nowScope.parent;
    }

    public void visit(BreakStmtNode it) {
        nowBuildingBlock.addStatements(new IRUnconditionalBr(nowForEndBlock));
    }

    public void visit(ContinueStmtNode it) {
        nowBuildingBlock.addStatements(new IRUnconditionalBr(nowForStepBlock));
    }

    public void visit(DefStmtNode it) {
        it.def.accept(this);
    }

    public void visit(EmptyStmtNode it) {
    }

    public void visit(ForStmtNode it) {
        nowScope = new IRScope(nowScope);
        it.init.accept(this);
        IRBlock itBlk = nowBuildingBlock;
        IRBlock condBlk = getNewBlock();
        IRBlock loopBlk = getNewBlock();
        IRBlock stepBlk = getNewBlock();
        IRBlock endBlk = getNewBlock();
        itBlk.addStatements(new IRUnconditionalBr(condBlk));
        nowBuildingBlock = condBlk;
        if (it.condition != null) {
            it.condition.accept(this);
            IREntity condition = it.condition.ent;
            if (it.condition.isAssignable()) {
                IRVariable v = getNewVariable(new IRSimpleType(32));
                nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) condition));
                condition = v;
            }
            nowBuildingBlock.addStatements(new IRConditionalBr(condition, loopBlk, endBlk));
        } else {
            nowBuildingBlock.addStatements(new IRUnconditionalBr(loopBlk));
        }
        nowDefining.functionBody.add(condBlk);
        nowBuildingBlock = stepBlk;
        if (it.step != null) {
            it.step.accept(this);
        }
        nowBuildingBlock.addStatements(new IRUnconditionalBr(condBlk));//存在换nowBuildingBlock的情况
        nowDefining.functionBody.add(stepBlk);
        nowBuildingBlock = loopBlk;
        IRBlock formerStepBlk = nowForStepBlock;
        IRBlock formerEndBlk = nowForEndBlock;
        nowForStepBlock = stepBlk;
        nowForEndBlock = endBlk;
        it.thenStmt.accept(this);
        nowBuildingBlock.addStatements(new IRUnconditionalBr(stepBlk));
        nowDefining.functionBody.add(loopBlk);
        nowForEndBlock = formerEndBlk;
        nowForStepBlock = formerStepBlk;
        nowBuildingBlock = endBlk;
        nowDefining.functionBody.add(endBlk);
        nowScope = nowScope.parent;
    }

    public void visit(IfStmtNode it) {
        it.condition.accept(this);
        IREntity condition = it.condition.ent;
        if (it.condition.isAssignable()) {
            IRVariable v = getNewVariable(new IRSimpleType(1));
            nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) condition));
            condition = v;
        }
        if (it.elseStmt == null) {
            IRBlock itBlk = nowBuildingBlock;
            IRBlock thenBlk = getNewBlock();
            IRBlock endBlk = getNewBlock();
            itBlk.addStatements(new IRConditionalBr(condition, thenBlk, endBlk));
            nowBuildingBlock = thenBlk;
            nowDefining.functionBody.add(thenBlk);
            it.thenStmt.accept(this);
            nowBuildingBlock.addStatements(new IRUnconditionalBr(endBlk));
            nowBuildingBlock = endBlk;
            nowDefining.functionBody.add(endBlk);
        } else {
            IRBlock itBlk = nowBuildingBlock;
            IRBlock thenBlk = getNewBlock();
            IRBlock elseBlk = getNewBlock();
            IRBlock endBlk = getNewBlock();
            itBlk.addStatements(new IRConditionalBr(condition, thenBlk, elseBlk));
            nowBuildingBlock = thenBlk;
            nowDefining.functionBody.add(thenBlk);
            it.thenStmt.accept(this);
            nowBuildingBlock.addStatements(new IRUnconditionalBr(endBlk));
            nowBuildingBlock = elseBlk;
            nowDefining.functionBody.add(elseBlk);
            it.elseStmt.accept(this);
            nowBuildingBlock.addStatements(new IRUnconditionalBr(endBlk));
            nowBuildingBlock = endBlk;
            nowDefining.functionBody.add(endBlk);
        }

    }

    public void visit(PureExprStmtNode it) {
        it.expression.accept(this);
    }

    public void visit(ReturnStmtNode it) {
        if (it.return_expression != null) {
            it.return_expression.accept(this);
            if (it.return_expression.isAssignable())
                this.nowBuildingBlock.addStatements(new IRRet(getPointerTarget((IRVariable) it.return_expression.ent)));
            else {
                this.nowBuildingBlock.addStatements(new IRRet(it.return_expression.ent));
            }
        } else {
            this.nowBuildingBlock.addStatements(new IRRet(new IRVoidEntity()));
        }

    }

    public void visit(WhileStmtNode it) {
        IRBlock itBlk = nowBuildingBlock;
        IRBlock conditionBlk = getNewBlock();
        IRBlock loopBlk = getNewBlock();
        IRBlock endBlk = getNewBlock();
        itBlk.addStatements(new IRUnconditionalBr(conditionBlk));
        nowBuildingBlock = conditionBlk;
        nowDefining.functionBody.add(conditionBlk);
        it.condition.accept(this);
        IREntity condition = it.condition.ent;
        if (it.condition.isAssignable()) {
            IRVariable v = getNewVariable(new IRSimpleType(32));
            nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) condition));
            condition = v;
        }
        nowBuildingBlock.addStatements(new IRConditionalBr(condition, loopBlk, endBlk));
        nowBuildingBlock = loopBlk;
        IRBlock formerStepBlk = nowForStepBlock;
        IRBlock formerEndBlk = nowForEndBlock;
        nowForStepBlock = conditionBlk;
        nowForEndBlock = endBlk;
        nowDefining.functionBody.add(loopBlk);
        it.thenStmt.accept(this);
        nowBuildingBlock.addStatements(new IRUnconditionalBr(conditionBlk));
        nowForEndBlock = formerEndBlk;
        nowForStepBlock = formerStepBlk;
        nowBuildingBlock = endBlk;
        nowDefining.functionBody.add(endBlk);
    }

    public void visit(RootNode it) {

        it.defs.forEach(t -> {
            if (t instanceof ClassDefNode) {
                IRClassDef c = new IRClassDef(((ClassDefNode) t).classname, MxGlobalScope.types.get(((ClassDefNode) t).classname));
                types.put(((ClassDefNode) t).classname, c);
                root.definitions.add(c);
            }
        });
        root.definitions.add(initGlobal);
        it.defs.forEach(t -> {
            t.accept(this);
            if (t instanceof FuncDefNode) root.definitions.add(nowDefining);
        });
        initGlobal.functionBody.add(new IRRet(new IRVoidEntity()));
    }

    public void visit(VarExprNode it) {
        if (nowScope.isClassMember(it.name)) {
            //类方法中的类成员处理
            //转成this.
            int pos = nowDefiningClass.index.get(it.name);
            IRType targetType = nowDefiningClass.elements.get(pos);
            IRVariable ans = getNewVariable(new IRPointerType(targetType));
            IRGetElementPtr gep = new IRGetElementPtr(new IRBasicClassType(nowDefiningClass.name), nowThis, ans);
            gep.idx.add(new IRConstantValue(0, new IRSimpleType(32)));//取第0个class
            gep.idx.add(new IRConstantValue(pos, new IRSimpleType(32)));//取class的第pos个元素
            nowBuildingBlock.addStatements(gep);
            it.ent = ans;
        } else {
            it.ent = nowScope.findVar(it.name, true);
        }

    }

    public void visit(StringLiteralNode it) {
        //不再搞一个StringType了，直接认为是个指向i8的指针
        IRVariable v = getNewConstString();
        //在全局中定义字符串变量
        root.definitions.add(new IRStringConstantDef(v, it.value.length() + 1, it.value));
        it.ent = v;
    }

    public void visit(IntLiteralNode it) {
        IRSimpleType t = new IRSimpleType(32);
        it.ent = new IRConstantValue(it.value, t);
    }

    public void visit(BoolLiteralNode it) {
        it.ent = new IRConstantBool(it.value);
    }

    public void visit(ThisNode it) {
        it.ent = nowThis;
    }

    public void visit(NullNode it) {
        it.ent = new IRNull();
    }

    public void visit(ClassMethodCallExprNode it) {

        it.classExpr.accept(this);
        IREntity family = it.classExpr.ent;
        if (it.classExpr.isAssignable()) {
            IRVariable v = getNewVariable(((IRPointerType) family.getType()).targetType);
            nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) family));
            family = v;
        }
        it.parameters.forEach(p -> p.accept(this));
        if (it.classExpr.type.dimension > 0) {
            //array.size()
            IRVariable sizePtr = getNewVariable(new IRPointerType(new IRSimpleType(32)));
            IRGetElementPtr getArraySizePtr = new IRGetElementPtr(new IRSimpleType(32), (IRVariable) family, sizePtr);
            getArraySizePtr.addIdx(new IRConstantValue(-1, new IRSimpleType(32)));
            nowBuildingBlock.addStatements(getArraySizePtr);
            IRVariable size = getNewVariable(new IRSimpleType(32));
            nowBuildingBlock.addStatements(new IRLoad(size, sizePtr));
            it.ent = size;
            return;
        }
        IRCall callFunc;

        if (it.classExpr.type.isString()) {
            callFunc = new IRCall("_string." + it.methodName, TransTypeToIRType(it.type));
        } else {
            callFunc = new IRCall(it.classExpr.type.type_name + "." + it.methodName, TransTypeToIRType(it.type));
        }
        callFunc.parameters.add(family);
        it.parameters.forEach(p -> {
            IREntity pV = p.ent;
            if (p.isAssignable()) {
                IRVariable v = getNewVariable(((IRPointerType) p.ent.getType()).targetType);
                nowBuildingBlock.addStatements(new IRLoad(v, (IRVariable) pV));
                pV = v;
            }
            callFunc.parameters.add(pV);
        });
        if (!(callFunc.returnType instanceof IRVoidType)) {
            IRVariable rt = getNewVariable(callFunc.returnType);
            callFunc.result = rt;
            it.ent = rt;
        }
        nowBuildingBlock.addStatements(callFunc);
    }
}
