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

public class IRBuilder implements ASTVisitor {
    public IRRoot root;
    public GlobalScope MxGlobalScope;
    public HashMap<String, FunctionDec> funcs;
    public HashMap<String, ClassDef> types;
    public FunctionDef nowDefining = null;//正在定义哪个函数
    public ClassDef nowDefiningClass = null;
    public block nowBuildingBlock = null; //正在写哪个块。
    public block nowForStepBlock = null;//最里层for循环的step块，用于continue的跳转
    public block nowForEndBlock = null;//最里层
    public IRScope nowScope;
    public int blockNum = 0;
    public int variableNum = 0;
    public int constStringNum = 0;
    public int globalVariableNum = 0;
    String ArrayClass = "struct._array";//数组类型,内建
    String This = "%_this";//this指针，类方法
    String ArraySize = "_arraySize";//数组长度，同上,内建方法，靠c写，在节点中仅调用函数
    String ArrayElement = "_arrayElement";//取数组第n个i8处的指针
    FunctionDef initGlobal = new FunctionDef("_initGlobal", new Type("void"));

    public IRBuilder(IRRoot root, GlobalScope globalScope) {
        this.root = root;
        this.MxGlobalScope = globalScope;
        this.funcs = new HashMap<>();
        this.types = new HashMap<>();
        this.nowScope = new IRScope(null);
    }

    public void insertInst(inst i) {
        if (nowBuildingBlock == null) {
            nowDefining.functionBody.add(i);
        } else {
            nowBuildingBlock.statements.add(i);
        }
    }

    public block getNewBlock() {
        ++blockNum;
        return new block("_b." + Integer.toString(blockNum));
    }

    public variable getNewVariable(IRType t) {
        ++variableNum;
        return new variable(t, "%_" + variableNum);
    }

    public variable getNewGlobal(IRType t) {
        ++globalVariableNum;
        return new variable(t, "@_" + globalVariableNum);
    }

    public variable getNewConstString() {
        ++constStringNum;
        return new variable(new IRPointerType(new IRSimpleType(8)), "@.str" + constStringNum);
    }

    public variable getPointerTarget(variable v) {
        //因为变量都是指针形式，所以要靠这个获取它们的值，用于return等场合
        //能做左值的返回指针
        //根据节点是否assignable 确定是否扒一层指针
        if (v.type instanceof IRPointerType) {
            variable ans = getNewVariable(((IRPointerType) v.type).targetType);
            nowBuildingBlock.addStatements(new load(ans, v));
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
        nowScope.isClassScope = true;
        nowDefiningClass = types.get(it.classname);
        it.elements.forEach(defNode -> {
            if (defNode instanceof FuncDefNode) {
                defNode.accept(this);
                nowDefining.functionName = it.classname + "." + nowDefining.functionName;
                nowDefining.parameters.add(0, new variable(new IRPointerType(new IRBasicClassType(it.classname)), "%_this"));
                root.definitions.add(nowDefining);
            }
        });
        nowScope = nowScope.parent;

    }

    public void visit(FuncDefNode it) {
        nowScope = new IRScope(nowScope);
        nowBuildingBlock = getNewBlock();
        if (it.func_name.equals("main")) {
            //main函数
            nowDefining = new FunctionDef("main", it.return_type);
        } else {
            nowDefining = new FunctionDef(it.func_name, it.return_type);
            for (int i = 0; i < it.parameter_list.size(); ++i) {
                IRType t = TransTypeToIRType(it.getParameterType(i));
                variable v = getNewVariable(t);
                variable p = getNewVariable(new IRPointerType(t));
                nowBuildingBlock.addStatements(new alloca(p, t));
                nowBuildingBlock.addStatements(new store(v, (IRPointerType) p.type, p));
                nowDefining.parameters.add(v);
                nowScope.var.put(it.getParameterName(i), p);
            }
        }

        nowDefining.functionBody.add(nowBuildingBlock);
        it.statements.forEach(t -> t.accept(this));
        nowScope = nowScope.parent;
    }

    public void visit(VarDefNode it) {
        if (nowScope.parent == null) {
            //全局变量
            for (int i = 0; i < it.define_list.size(); ++i) {
                IRType t = TransTypeToIRType(it.getDefineListType(i));
                variable v = getNewGlobal(new IRPointerType(t));
                root.definitions.add(new GlobalDef(v.name, t));
                if (it.getDefineListInit(i) != null) {
                    nowBuildingBlock = getNewBlock();
                    it.getDefineListInit(i).accept(this);
                    entity init = it.getDefineListInit(i).ent;
                    if (it.getDefineListInit(i).isAssignable()) {
                        variable v1 = getNewVariable(((IRPointerType) init.getType()).targetType);
                        nowBuildingBlock.addStatements(new load(v1, (variable) init));
                        init = v;
                    }
                    nowBuildingBlock.addStatements(new store(init, (IRPointerType) v.type, v));
                    initGlobal.functionBody.add(nowBuildingBlock);
                    nowBuildingBlock = null;
                }
                nowScope.var.put(it.getDefineListName(i), v);
            }
            return;
        }
        //先不考虑全局变量
        for (int i = 0; i < it.define_list.size(); ++i) {
            IRType t = TransTypeToIRType(it.getDefineListType(i));
            variable v = getNewVariable(new IRPointerType(t));
            nowBuildingBlock.addStatements(new alloca(v, t));
            if (it.getDefineListInit(i) != null) {
                it.getDefineListInit(i).accept(this);
                entity init = it.getDefineListInit(i).ent;
                if (it.getDefineListInit(i).isAssignable()) {
                    variable v1 = getNewVariable(((IRPointerType) init.getType()).targetType);
                    nowBuildingBlock.addStatements(new load(v1, (variable) init));
                    init = v;
                }
                nowBuildingBlock.addStatements(new store(init, (IRPointerType) v.type, v));
            }
            nowScope.var.put(it.getDefineListName(i), v);
        }
    }

    public void visit(ArrayElementExprNode it) {
        it.arrayExpr.accept(this);
        it.indexExpr.accept(this);
        entity ar = it.arrayExpr.ent;
        entity in = it.indexExpr.ent;
        if (it.arrayExpr.isAssignable()) {
            variable v = getNewVariable(((IRPointerType) ar.getType()).targetType);
            nowBuildingBlock.addStatements(new load(v, (variable) ar));
            ar = v;
        }
        if (it.indexExpr.isAssignable()) {
            variable v = getNewVariable(((IRPointerType) in.getType()).targetType);
            nowBuildingBlock.addStatements(new load(v, (variable) in));
            in = v;
        }
        //数组实际上为指向结构体(即IRBasicArrayType代表的){i32 size,ptr([size x elementType])}的指针
        IRType elementType = ((IRBasicArrayType) ar.getType()).elementType;
        variable ans = getNewVariable(new IRPointerType(elementType));
        call newCall = new call(ArrayElement, new IRPointerType(elementType));
        newCall.result = ans;
        newCall.parameters.add(ar);
        newCall.parameters.add(in);
        nowBuildingBlock.addStatements(newCall);
        it.ent = ans;
    }

    public void visit(AssignExprNode it) {
        it.lhs.accept(this);
        it.rhs.accept(this);
        entity lhs = it.lhs.ent;
        entity rhs = it.rhs.ent;
        if (it.rhs.isAssignable()) {
            variable v = getNewVariable(new IRSimpleType(32));
            nowBuildingBlock.addStatements(new load(v, (variable) rhs));
            rhs = v;
        }
        nowBuildingBlock.addStatements(new store(rhs, (IRPointerType) lhs.getType(), (variable) lhs));
    }

    public void visit(BinaryExprNode it) {

        if (it.lhs.type.isInt()) {
            it.lhs.accept(this);
            it.rhs.accept(this);
            if (it.type.isInt()) {
                //i32运算指令
                entity lhs = it.lhs.ent;
                entity rhs = it.rhs.ent;
                if (it.lhs.isAssignable()) {
                    variable v = getNewVariable(new IRSimpleType(32));
                    nowBuildingBlock.addStatements(new load(v, (variable) lhs));
                    lhs = v;
                }
                if (it.rhs.isAssignable()) {
                    variable v = getNewVariable(new IRSimpleType(32));
                    nowBuildingBlock.addStatements(new load(v, (variable) rhs));
                    rhs = v;
                }
                variable ans = getNewVariable(new IRSimpleType(32));
                if (it.op == BinaryExprNode.binaryOpType.plus) {
                    nowBuildingBlock.addStatements(new binary(ans, 32, lhs, rhs, binary.opType.add));
                } else if (it.op == BinaryExprNode.binaryOpType.minus) {
                    nowBuildingBlock.addStatements(new binary(ans, 32, lhs, rhs, binary.opType.sub));
                } else if (it.op == BinaryExprNode.binaryOpType.multiply) {
                    nowBuildingBlock.addStatements(new binary(ans, 32, lhs, rhs, binary.opType.mul));
                } else if (it.op == BinaryExprNode.binaryOpType.divide) {
                    nowBuildingBlock.addStatements(new binary(ans, 32, lhs, rhs, binary.opType.sdiv));
                } else if (it.op == BinaryExprNode.binaryOpType.mod) {
                    nowBuildingBlock.addStatements(new binary(ans, 32, lhs, rhs, binary.opType.srem));
                } else if (it.op == BinaryExprNode.binaryOpType.or) {
                    nowBuildingBlock.addStatements(new binary(ans, 32, lhs, rhs, binary.opType.or));
                } else if (it.op == BinaryExprNode.binaryOpType.and) {
                    nowBuildingBlock.addStatements(new binary(ans, 32, lhs, rhs, binary.opType.and));
                } else if (it.op == BinaryExprNode.binaryOpType.caret) {
                    nowBuildingBlock.addStatements(new binary(ans, 32, lhs, rhs, binary.opType.xor));
                } else if (it.op == BinaryExprNode.binaryOpType.leftShift) {
                    nowBuildingBlock.addStatements(new binary(ans, 32, lhs, rhs, binary.opType.shl));
                } else if (it.op == BinaryExprNode.binaryOpType.rightShift) {
                    nowBuildingBlock.addStatements(new binary(ans, 32, lhs, rhs, binary.opType.ashr));
                }
                it.ent = ans;
            } else {
                //icmp指令
                entity lhs = it.lhs.ent;
                entity rhs = it.rhs.ent;
                if (it.lhs.isAssignable()) {
                    variable v = getNewVariable(new IRSimpleType(32));
                    nowBuildingBlock.addStatements(new load(v, (variable) lhs));
                    lhs = v;
                }
                if (it.rhs.isAssignable()) {
                    variable v = getNewVariable(new IRSimpleType(32));
                    nowBuildingBlock.addStatements(new load(v, (variable) rhs));
                    rhs = v;
                }
                variable ans = getNewVariable(new IRSimpleType(1));
                if (it.op == BinaryExprNode.binaryOpType.equal) {
                    nowBuildingBlock.addStatements(new compare(ans, 32, lhs, rhs, compare.condType.eq));
                } else if (it.op == BinaryExprNode.binaryOpType.notEqual) {
                    nowBuildingBlock.addStatements(new compare(ans, 32, lhs, rhs, compare.condType.ne));
                } else if (it.op == BinaryExprNode.binaryOpType.less) {
                    nowBuildingBlock.addStatements(new compare(ans, 32, lhs, rhs, compare.condType.slt));
                } else if (it.op == BinaryExprNode.binaryOpType.lessEqual) {
                    nowBuildingBlock.addStatements(new compare(ans, 32, lhs, rhs, compare.condType.sle));
                } else if (it.op == BinaryExprNode.binaryOpType.greater) {
                    nowBuildingBlock.addStatements(new compare(ans, 32, lhs, rhs, compare.condType.sgt));
                } else if (it.op == BinaryExprNode.binaryOpType.greaterEqual) {
                    nowBuildingBlock.addStatements(new compare(ans, 32, lhs, rhs, compare.condType.sge));
                }
                it.ent = ans;
            }
        } else if (it.lhs.type.isBool()) {
            //（短路求值）逻辑运算
            variable ans = getNewVariable(new IRSimpleType(1));
            it.lhs.accept(this);
            entity lhs = it.lhs.ent;
            if (it.lhs.isAssignable()) {
                variable v = getNewVariable(new IRSimpleType(32));
                nowBuildingBlock.addStatements(new load(v, (variable) lhs));
                lhs = v;
            }
            block itBlock = nowBuildingBlock;//nowBuildingBlock最后要变成endBlock
            block rhsBlock = getNewBlock();
            nowBuildingBlock = rhsBlock;
            it.rhs.accept(this);
            entity rhs = it.rhs.ent;
            if (it.rhs.isAssignable()) {
                variable v = getNewVariable(new IRSimpleType(32));
                nowBuildingBlock.addStatements(new load(v, (variable) rhs));
                rhs = v;
            }
            block endBlock = getNewBlock();
            nowBuildingBlock = endBlock;//之后就一直是endBlock
            phi phiInst = new phi(ans, ans.type);
            phiInst.valLabel.put(itBlock.labelName, lhs);
            phiInst.valLabel.put(rhsBlock.labelName, rhs);
            endBlock.addStatements(phiInst);
            if (it.op == BinaryExprNode.binaryOpType.oror) {
                //为itBlock和rhsBlock添加跳转
                itBlock.addStatements(new conditionalBr(lhs, endBlock, rhsBlock));
                rhsBlock.addStatements(new unconditionalBr(endBlock));
                //把新增block添加进nowDefining中（block没有包含关系）
                nowDefining.functionBody.add(rhsBlock);
                nowDefining.functionBody.add(endBlock);
            } else if (it.op == BinaryExprNode.binaryOpType.andand) {
                //为itBlock和rhsBlock添加跳转
                itBlock.addStatements(new conditionalBr(lhs, rhsBlock, endBlock));
                rhsBlock.addStatements(new unconditionalBr(endBlock));
                //把新增block添加进nowDefining中（block没有包含关系）
                nowDefining.functionBody.add(rhsBlock);
                nowDefining.functionBody.add(endBlock);
            }
            it.ent = ans;
        } else if (it.lhs.type.isString()) {
            if (it.op == BinaryExprNode.binaryOpType.plus) {
                //string加法
                variable ans = getNewVariable(new IRPointerType(new IRSimpleType(8)));
                call stringPlus = new call("_string_plus", new IRPointerType(new IRSimpleType(8)));
                stringPlus.result = ans;
                entity lhs = it.lhs.ent;
                entity rhs = it.rhs.ent;
                if (it.lhs.isAssignable()) {
                    variable v = getNewVariable(new IRPointerType(new IRSimpleType(8)));
                    nowBuildingBlock.addStatements(new load(v, (variable) lhs));
                    lhs = v;
                }
                if (it.rhs.isAssignable()) {
                    variable v = getNewVariable(new IRPointerType(new IRSimpleType(8)));
                    nowBuildingBlock.addStatements(new load(v, (variable) rhs));
                    rhs = v;
                }
                stringPlus.parameters.add(lhs);
                stringPlus.parameters.add(rhs);
                nowBuildingBlock.addStatements(stringPlus);
                it.ent = ans;
            } else {
                //string比较
                variable ans = getNewVariable(new IRSimpleType(1));
                call stringCmp = null;
                if (it.op == BinaryExprNode.binaryOpType.equal) {
                    stringCmp = new call("_string_eq", new IRSimpleType(1));
                } else if (it.op == BinaryExprNode.binaryOpType.notEqual) {
                    stringCmp = new call("_string_ne", new IRSimpleType(1));
                } else if (it.op == BinaryExprNode.binaryOpType.greater) {
                    stringCmp = new call("_string_g", new IRSimpleType(1));
                } else if (it.op == BinaryExprNode.binaryOpType.greaterEqual) {
                    stringCmp = new call("_string_ge", new IRSimpleType(1));
                } else if (it.op == BinaryExprNode.binaryOpType.less) {
                    stringCmp = new call("_string_l", new IRSimpleType(1));
                } else if (it.op == BinaryExprNode.binaryOpType.lessEqual) {
                    stringCmp = new call("_string_le", new IRSimpleType(1));
                }
                entity lhs = it.lhs.ent;
                entity rhs = it.rhs.ent;
                if (it.lhs.isAssignable()) {
                    variable v = getNewVariable(new IRPointerType(new IRSimpleType(8)));
                    nowBuildingBlock.addStatements(new load(v, (variable) lhs));
                    lhs = v;
                }
                if (it.rhs.isAssignable()) {
                    variable v = getNewVariable(new IRPointerType(new IRSimpleType(8)));
                    nowBuildingBlock.addStatements(new load(v, (variable) rhs));
                    rhs = v;
                }
                assert stringCmp != null;
                stringCmp.result = ans;
                stringCmp.parameters.add(lhs);
                stringCmp.parameters.add(rhs);
            }
        }


    }

    public void visit(ErrorArrayExprNode it) {
        //会在SemanticCheck阶段寄掉
    }

    public void visit(FunctionCallExprNode it) {
        it.parameters.forEach(p -> p.accept(this));
        call callFunc = new call(it.funcName, TransTypeToIRType(it.type));
        it.parameters.forEach(p -> {
            entity pV = p.ent;
            if (p.isAssignable()) {
                variable v = getNewVariable(((IRPointerType) p.ent.getType()).targetType);
                nowBuildingBlock.addStatements(new load(v, (variable) pV));
                pV = v;
            }
            callFunc.parameters.add(pV);
        });
        if (!(callFunc.returnType instanceof IRVoidType)) {
            variable rt = getNewVariable(callFunc.returnType);
            callFunc.result = rt;
            it.ent = rt;
        }
        nowBuildingBlock.addStatements(callFunc);
    }

    public void visit(MemberExprNode it) {
        it.familyExpr.accept(this);
        entity family = it.familyExpr.ent;//此处应该是class**或this
        if (it.familyExpr.isAssignable()) {
            variable v = getNewVariable(((IRPointerType) family.getType()).targetType);
            nowBuildingBlock.addStatements(new load(v, (variable) family));
            family = v;
        }
        //此处family变为class*
        int pos = types.get(it.familyExpr.type.type_name).index.get(it.memberName);
        IRType targetType = types.get(it.familyExpr.type.type_name).elements.get(pos);
        variable ans = getNewVariable(new IRPointerType(targetType));
        getElementPtr gep = new getElementPtr(((IRPointerType) family.getType()).targetType, (variable) family, ans);
        gep.idx.add(new constantValue(0, new IRSimpleType(32)));//取第0个class
        gep.idx.add(new constantValue(pos, new IRSimpleType(32)));//取class的第pos个元素
        nowBuildingBlock.addStatements(gep);
        it.ent = ans;
    }

    public void newArray(ArrayList<ExprNode> in, variable ans, int dimension) {
        if (in.isEmpty()) {
            //in是空的就直接赋值为null
            return;
        }
        in.get(0).accept(this);
        entity size = in.get(0).ent;
        if (in.get(0).isAssignable()) {
            variable v = getNewVariable(((IRPointerType) size.getType()).targetType);
            nowBuildingBlock.addStatements(new load(v, (variable) size));
            size = v;
        }
        variable bytes = getNewVariable(new IRSimpleType(32));
        IRType elementType = ((IRBasicArrayType) ((IRPointerType) ans.type).targetType).elementType;
        nowBuildingBlock.addStatements(new binary(bytes, bytes.getType(), size, new constantValue(elementType.size(), new IRSimpleType(32)), binary.opType.mul));
        nowBuildingBlock.addStatements(new alloca(ans, new IRBasicClassType(ArrayClass)));
        variable arrayHeadPtr = getNewVariable(new IRPointerType(new IRPointerType(elementType)));
        getElementPtr getArrayHeadPtr = new getElementPtr(new IRBasicClassType(ArrayClass), ans, arrayHeadPtr);
        getArrayHeadPtr.addIdx(new constantValue(0, new IRSimpleType(32)));
        getArrayHeadPtr.addIdx(new constantValue(1, new IRSimpleType(32)));
        nowBuildingBlock.addStatements(getArrayHeadPtr);
        variable arrayHead = getNewVariable(new IRPointerType(elementType));
        nowBuildingBlock.addStatements(new load(arrayHead, arrayHeadPtr));
        call mallocFunc = new call("_malloc", arrayHead.getType());
        mallocFunc.result = arrayHead;
        mallocFunc.parameters.add(bytes);
        nowBuildingBlock.addStatements(mallocFunc);
        variable arraySizePtr = getNewVariable(new IRPointerType(new IRSimpleType(32)));
        getElementPtr getArraySizePtr = new getElementPtr(new IRBasicClassType(ArrayClass), ans, arraySizePtr);
        getArrayHeadPtr.addIdx(new constantValue(0, new IRSimpleType(32)));
        getArrayHeadPtr.addIdx(new constantValue(0, new IRSimpleType(32)));
        nowBuildingBlock.addStatements(getArraySizePtr);
        variable arraySize = getNewVariable(new IRSimpleType(32));
        nowBuildingBlock.addStatements(new load(arraySize, arraySizePtr));
        //需要：一个循环来对所有元素进行内存分配和调用构造函数，必须是写在IR里的，因为我不知道数组大小
        variable counter = getNewVariable(new IRPointerType(new IRSimpleType(32)));
        nowBuildingBlock.addStatements(new alloca(counter, new IRSimpleType(32)));
        nowBuildingBlock.addStatements(new store(new constantValue(0, new IRSimpleType(32)), (IRPointerType) counter.type, counter));
        block condBlk = getNewBlock();
        block stepBlk = getNewBlock();
        block loopBlk = getNewBlock();
        block endBlk = getNewBlock();
        nowBuildingBlock.addStatements(new unconditionalBr(condBlk));
        variable counterValue1 = getNewVariable(new IRSimpleType(32));
        condBlk.addStatements(new load(counterValue1, counter));
        variable cmp = getNewVariable(new IRSimpleType(1));
        condBlk.addStatements(new compare(cmp, 32, counterValue1, arraySize, compare.condType.slt));
        condBlk.addStatements(new conditionalBr(cmp, loopBlk, endBlk));
        nowDefining.functionBody.add(condBlk);
        variable counterValue2 = getNewVariable(new IRSimpleType(32));
        stepBlk.addStatements(new load(counterValue2, counter));
        variable newCounterValue = getNewVariable(new IRSimpleType(32));
        stepBlk.addStatements(new binary(newCounterValue, 32, counterValue2, new constantValue(1, new IRSimpleType(32)), binary.opType.add));
        stepBlk.addStatements(new unconditionalBr(condBlk));
        nowDefining.functionBody.add(stepBlk);
        nowBuildingBlock = loopBlk;
        variable counterValue3 = getNewVariable(new IRSimpleType(32));
        loopBlk.addStatements(new load(counterValue3, counter));
        variable elementPtr = getNewVariable(new IRPointerType(elementType));
        call getArrayElementPtr = new call(ArrayElement, elementPtr.getType());
        getArrayElementPtr.result = elementPtr;
        getArrayElementPtr.parameters.add(ans);
        getArrayElementPtr.parameters.add(counterValue3);
        loopBlk.addStatements(getArrayElementPtr);
        if (elementType instanceof IRPointerType) {
            if (((IRPointerType) elementType).targetType instanceof IRBasicClassType) {
                //为类分配内存，调用构造函数
                variable element = getNewVariable(elementType);
                loopBlk.addStatements(new load(element, elementPtr));
                String className = ((IRBasicClassType) ((IRPointerType) elementType).targetType).className;
                loopBlk.addStatements(new alloca(element, new IRBasicClassType(className)));
                call construct = new call(className + "." + className, new IRVoidType());
                construct.parameters.add(element);
            } else if (((IRPointerType) elementType).targetType instanceof IRBasicArrayType) {
                //构造下一层
                if (in.size() > 1) {
                    variable element = getNewVariable(elementType);
                    loopBlk.addStatements(new load(element, elementPtr));
                    ArrayList<ExprNode> newIn = new ArrayList<>();
                    for (int i = 1; i < in.size(); ++i) {
                        newIn.add(in.get(i));
                    }
                    newArray(newIn, element, dimension - 1);
                } else {
                    //如果是最后一层，则把所有元素都赋值为null
                    loopBlk.addStatements(new store(new IRNull(), (IRPointerType) elementPtr.type, elementPtr));
                }
            }
        }
        nowDefining.functionBody.add(loopBlk);
        nowDefining.functionBody.add(endBlk);
        nowBuildingBlock = endBlk;

    }

    public void visit(NewArrayExprNode it) {
        variable ans = getNewVariable(TransTypeToIRType(it.type));
        newArray(it.bounds, ans, it.type.dimension);
        it.ent = ans;
    }

    public void visit(NewExprNode it) {
        variable v = getNewVariable(new IRPointerType(new IRBasicClassType(it.type.type_name)));
        nowBuildingBlock.addStatements(new alloca(v, new IRBasicClassType(it.type.type_name)));
        call construct = new call(it.type.type_name + "." + it.type.type_name, new IRVoidType());
        construct.parameters.add(v);
        nowBuildingBlock.addStatements(construct);//添加构造函数
        it.ent = v;
    }

    public void visit(PrefixExprNode it) {
        //--a,++a,按可被赋值而设计
        it.expression.accept(this);
        IRSimpleType t = new IRSimpleType(32);
        IRPointerType p = new IRPointerType(t);
        variable bef = getNewVariable(t);
        variable aft = getNewVariable(t);
        nowBuildingBlock.addStatements(new load(bef, (variable) it.expression.ent));
        long add;
        if (it.op == PrefixExprNode.prefixOpType.addadd) {
            add = 1;
        } else {
            add = -1;
        }
        nowBuildingBlock.addStatements(new binary(aft, 32, bef, new constantValue(add, t), binary.opType.add));
        nowBuildingBlock.addStatements(new store(aft, p, (variable) it.expression.ent));
        it.ent = it.expression.ent;
    }

    public void visit(SuffixExprNode it) {
        //a++;a--
        //理论上来讲expression一定是个指针（可被赋值）
        it.expression.accept(this);
        IRSimpleType t = new IRSimpleType(32);
        IRPointerType p = new IRPointerType(t);
        variable bef = getNewVariable(t);
        variable aft = getNewVariable(t);
        nowBuildingBlock.addStatements(new load(bef, (variable) it.expression.ent));
        long add;
        if (it.op == SuffixExprNode.suffixOpType.addadd) {
            add = 1;
        } else {
            add = -1;
        }
        nowBuildingBlock.addStatements(new binary(aft, 32, bef, new constantValue(add, t), binary.opType.add));
        nowBuildingBlock.addStatements(new store(aft, p, (variable) it.expression.ent));
        it.ent = bef;
    }

    public void visit(TernaryExprNode it) {
        it.judgeExpr.accept(this);
        entity cond = it.judgeExpr.ent;
        if (it.judgeExpr.isAssignable()) {
            variable v = getNewVariable(new IRSimpleType(1));
            nowBuildingBlock.addStatements(new load(v, (variable) cond));
            cond = v;
        }
        block itBlk = nowBuildingBlock;
        block trueBlk = getNewBlock();
        block falseBlk = getNewBlock();
        block endBlk = getNewBlock();
        itBlk.addStatements(new conditionalBr(cond, trueBlk, falseBlk));
        nowBuildingBlock = trueBlk;
        it.trueExpr.accept(this);
        entity t = it.trueExpr.ent;
        if (it.trueExpr.isAssignable()) {
            variable v = getNewVariable(((IRPointerType) t.getType()).targetType);
            nowBuildingBlock.addStatements(new load(v, (variable) t));
            t = v;
        }
        trueBlk.addStatements(new unconditionalBr(endBlk));
        nowDefining.functionBody.add(trueBlk);
        nowBuildingBlock = falseBlk;
        it.falseExpr.accept(this);
        entity f = it.falseExpr.ent;
        if (it.falseExpr.isAssignable()) {
            variable v = getNewVariable(((IRPointerType) f.getType()).targetType);
            nowBuildingBlock.addStatements(new load(v, (variable) f));
            f = v;
        }
        falseBlk.addStatements(new unconditionalBr(endBlk));
        nowDefining.functionBody.add(falseBlk);
        nowBuildingBlock = endBlk;
        variable ans = getNewVariable(t.getType());
        phi phiInst = new phi(ans, ans.getType());
        phiInst.valLabel.put(trueBlk.labelName, t);
        phiInst.valLabel.put(falseBlk.labelName, f);
        endBlk.addStatements(phiInst);
        it.ent = ans;
    }

    public void visit(UnaryExprNode it) {
        it.expression.accept(this);
        if (it.expression.type.isBool()) {
            //必然是not
            entity expr = it.expression.ent;
            if (it.expression.isAssignable()) {
                variable v = getNewVariable(new IRSimpleType(1));
                nowBuildingBlock.addStatements(new load(v, (variable) expr));
                expr = v;
            }
            variable ans = getNewVariable(new IRSimpleType(1));
            nowBuildingBlock.addStatements(new binary(ans, 1, expr, new constantBool(true), binary.opType.xor));
            it.ent = ans;
        } else {
            entity expr = it.expression.ent;
            if (it.expression.isAssignable()) {
                variable v = getNewVariable(new IRSimpleType(32));
                nowBuildingBlock.addStatements(new load(v, (variable) expr));
                expr = v;
            }
            if (it.op == UnaryExprNode.unaryOpType.tilde) {
                variable ans = getNewVariable(new IRSimpleType(32));
                nowBuildingBlock.addStatements(new binary(ans, 32, expr, new constantValue(-1, new IRSimpleType(32)), binary.opType.xor));
                it.ent = ans;
            } else if (it.op == UnaryExprNode.unaryOpType.minus) {
                variable ans = getNewVariable(new IRSimpleType(32));
                nowBuildingBlock.addStatements(new binary(ans, 32, new constantValue(0, new IRSimpleType(32)), expr, binary.opType.sub));
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
        nowBuildingBlock.addStatements(new unconditionalBr(nowForEndBlock));
    }

    public void visit(ContinueStmtNode it) {
        nowBuildingBlock.addStatements(new unconditionalBr(nowForStepBlock));
    }

    public void visit(DefStmtNode it) {
        it.def.accept(this);
    }

    public void visit(EmptyStmtNode it) {
    }

    public void visit(ForStmtNode it) {
        nowScope = new IRScope(nowScope);
        it.init.accept(this);
        block itBlk = nowBuildingBlock;
        block condBlk = getNewBlock();
        block loopBlk = getNewBlock();
        block stepBlk = getNewBlock();
        block endBlk = getNewBlock();
        itBlk.addStatements(new unconditionalBr(condBlk));
        nowBuildingBlock = condBlk;
        if (it.condition != null) {
            it.condition.accept(this);
            entity condition = it.condition.ent;
            if (it.condition.isAssignable()) {
                variable v = getNewVariable(new IRSimpleType(32));
                nowBuildingBlock.addStatements(new load(v, (variable) condition));
                condition = v;
            }
            condBlk.addStatements(new conditionalBr(condition, loopBlk, endBlk));
        } else {
            condBlk.addStatements(new unconditionalBr(loopBlk));
        }
        nowDefining.functionBody.add(condBlk);
        nowBuildingBlock = stepBlk;
        if (it.step != null) {
            it.step.accept(this);
        }
        stepBlk.addStatements(new unconditionalBr(condBlk));
        nowDefining.functionBody.add(stepBlk);
        nowBuildingBlock = loopBlk;
        block formerStepBlk = nowForStepBlock;
        block formerEndBlk = nowForEndBlock;
        nowForStepBlock = stepBlk;
        nowForEndBlock = endBlk;
        it.thenStmt.accept(this);
        loopBlk.addStatements(new unconditionalBr(stepBlk));
        nowDefining.functionBody.add(loopBlk);
        nowForEndBlock = formerEndBlk;
        nowForStepBlock = formerStepBlk;
        nowBuildingBlock = endBlk;
        nowDefining.functionBody.add(endBlk);
        nowScope = nowScope.parent;
    }

    public void visit(IfStmtNode it) {
        it.condition.accept(this);
        entity condition = it.condition.ent;
        if (it.condition.isAssignable()) {
            variable v = getNewVariable(new IRSimpleType(32));
            nowBuildingBlock.addStatements(new load(v, (variable) condition));
            condition = v;
        }
        if (it.elseStmt == null) {
            block itBlk = nowBuildingBlock;
            block thenBlk = getNewBlock();
            block endBlk = getNewBlock();
            itBlk.addStatements(new conditionalBr(condition, thenBlk, endBlk));
            nowBuildingBlock = thenBlk;
            nowDefining.functionBody.add(thenBlk);
            it.thenStmt.accept(this);
            thenBlk.addStatements(new unconditionalBr(endBlk));
            nowBuildingBlock = endBlk;
            nowDefining.functionBody.add(endBlk);
        } else {
            block itBlk = nowBuildingBlock;
            block thenBlk = getNewBlock();
            block elseBlk = getNewBlock();
            block endBlk = getNewBlock();
            itBlk.addStatements(new conditionalBr(condition, thenBlk, elseBlk));
            nowBuildingBlock = thenBlk;
            nowDefining.functionBody.add(thenBlk);
            it.thenStmt.accept(this);
            thenBlk.addStatements(new unconditionalBr(endBlk));
            nowBuildingBlock = elseBlk;
            nowDefining.functionBody.add(elseBlk);
            it.elseStmt.accept(this);
            elseBlk.addStatements(new unconditionalBr(endBlk));
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
                this.nowBuildingBlock.addStatements(new ret(getPointerTarget((variable) it.return_expression.ent)));
            else {
                this.nowBuildingBlock.addStatements(new ret(it.return_expression.ent));
            }
        } else {
            this.nowBuildingBlock.addStatements(new ret(new voidEntity()));
        }

    }

    public void visit(WhileStmtNode it) {
        block itBlk = nowBuildingBlock;
        block conditionBlk = getNewBlock();
        block loopBlk = getNewBlock();
        block endBlk = getNewBlock();
        itBlk.addStatements(new unconditionalBr(conditionBlk));
        nowBuildingBlock = conditionBlk;
        nowDefining.functionBody.add(conditionBlk);
        it.condition.accept(this);
        entity condition = it.condition.ent;
        if (it.condition.isAssignable()) {
            variable v = getNewVariable(new IRSimpleType(32));
            nowBuildingBlock.addStatements(new load(v, (variable) condition));
            condition = v;
        }
        conditionBlk.addStatements(new conditionalBr(condition, loopBlk, endBlk));
        nowBuildingBlock = loopBlk;
        block formerStepBlk = nowForStepBlock;
        block formerEndBlk = nowForEndBlock;
        nowForStepBlock = conditionBlk;
        nowForEndBlock = endBlk;
        nowDefining.functionBody.add(loopBlk);
        it.thenStmt.accept(this);
        loopBlk.addStatements(new unconditionalBr(conditionBlk));
        nowForEndBlock = formerEndBlk;
        nowForStepBlock = formerStepBlk;
        nowBuildingBlock = endBlk;
        nowDefining.functionBody.add(endBlk);
    }

    public void visit(RootNode it) {
        root.definitions.add(initGlobal);
        it.defs.forEach(t -> {
            if (t instanceof ClassDefNode) {
                ClassDef c = new ClassDef(((ClassDefNode) t).classname, MxGlobalScope.types.get(((ClassDefNode) t).classname));
                types.put(((ClassDefNode) t).classname, c);
                root.definitions.add(c);
            }
        });
        it.defs.forEach(t -> {
            t.accept(this);
            if (t instanceof FuncDefNode) root.definitions.add(nowDefining);
        });
        initGlobal.functionBody.add(new ret(new voidEntity()));
    }

    public void visit(VarExprNode it) {
        if (nowScope.isClassMember(it.name)) {
            //类方法中的类成员处理
            //转成this.
            int pos = nowDefiningClass.index.get(it.name);
            IRType targetType = nowDefiningClass.elements.get(pos);
            variable ans = getNewVariable(new IRPointerType(targetType));
            getElementPtr gep = new getElementPtr(new IRBasicClassType(nowDefiningClass.name), new variable(new IRPointerType(new IRBasicClassType(nowDefiningClass.name)), "%_this"), ans);
            gep.idx.add(new constantValue(0, new IRSimpleType(32)));//取第0个class
            gep.idx.add(new constantValue(pos, new IRSimpleType(32)));//取class的第pos个元素
            nowBuildingBlock.addStatements(gep);
            it.ent = ans;
        } else {
            it.ent = nowScope.findVar(it.name, true);
        }

    }

    public void visit(StringLiteralNode it) {
        //不再搞一个StringType了，直接认为是个指向i8的指针
        variable v = getNewConstString();
        //在全局中定义字符串变量
        root.definitions.add(new stringConstantDef(v, it.value.length() + 1, it.value));
        it.ent = v;
    }

    public void visit(IntLiteralNode it) {
        IRSimpleType t = new IRSimpleType(32);
        it.ent = new constantValue(it.value, t);
    }

    public void visit(BoolLiteralNode it) {
        it.ent = new constantBool(it.value);
    }

    public void visit(ThisNode it) {
        it.ent = new variable(new IRPointerType(new IRBasicClassType(nowDefiningClass.name)), "%_this");
    }

    public void visit(NullNode it) {
        it.ent = new IRNull();
    }

    public void visit(ClassMethodCallExprNode it) {
        it.classExpr.accept(this);
        entity family = it.classExpr.ent;
        if (it.classExpr.isAssignable()) {
            variable v = getNewVariable(((IRPointerType) family.getType()).targetType);
            nowBuildingBlock.addStatements(new load(v, (variable) family));
            family = v;
        }
        it.parameters.forEach(p -> p.accept(this));
        call callFunc;
        if (it.classExpr.type.dimension > 0) {
            callFunc = new call(ArraySize,new IRSimpleType(32));//array.size()
        } else if (it.classExpr.type.isString()) {
            callFunc = new call("_string_" + it.methodName, TransTypeToIRType(it.type));
        } else {
            callFunc = new call(it.classExpr.type.type_name + "." + it.methodName, TransTypeToIRType(it.type));
        }
        callFunc.parameters.add(family);
        it.parameters.forEach(p -> {
            entity pV = p.ent;
            if (p.isAssignable()) {
                variable v = getNewVariable(((IRPointerType) p.ent.getType()).targetType);
                nowBuildingBlock.addStatements(new load(v, (variable) pV));
                pV = v;
            }
            callFunc.parameters.add(pV);
        });
        if (!(callFunc.returnType instanceof IRVoidType)) {
            variable rt = getNewVariable(callFunc.returnType);
            callFunc.result = rt;
            it.ent = rt;
        }
        nowBuildingBlock.addStatements(callFunc);
    }
}
