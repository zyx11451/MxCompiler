package OPT;

import IR.IRDefs.*;
import IR.IRTypes.IRSimpleType;
import IR.IRVisitor;
import IR.entity.IRConstantBool;
import IR.entity.IRConstantValue;
import IR.entity.IREntity;
import IR.entity.IRVariable;
import IR.inst.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ConstantOptimize implements IRVisitor {
    boolean nowInstDel = false;
    boolean nowInstReplace = false;
    IRUnconditionalBr rpl = null;
    IRFunctionDef nowFunc=null;

    HashMap<IRVariable, IREntity> allocaNearestStore;
    HashMap<IRVariable,IREntity> nearestLoad;//每个非alloca的变量只能被赋值一次，所以Load的结果可在函数内共享

    void collectAlloca(IRFunctionDef func) {
        func.functionBody.forEach(i -> {
            if (i instanceof IRBlock) {
                ((IRBlock) i).statements.forEach(a -> {
                    if (a instanceof IRAlloca) {
                        func.activeAlloca.put(((IRAlloca) a).result, new HashSet<>());
                    }
                });
            }
        });
        func.functionBody.forEach(i -> {
            if (i instanceof IRBlock) {
                ((IRBlock) i).statements.forEach(a -> {
                    if (a instanceof IRLoad) {
                        if (func.activeAlloca.containsKey(((IRLoad) a).pointer))
                            func.activeAlloca.get(((IRLoad) a).pointer).add((IRBlock) i);

                    } else if (a instanceof IRStore) {
                        if (func.activeAlloca.containsKey(((IRStore) a).target))
                            func.activeAlloca.get(((IRStore) a).target).add((IRBlock) i);
                    }
                });
            }
        });
    }

    IREntity tryReplace(IREntity tar) {
        if (tar instanceof  IRVariable && nearestLoad.containsKey(tar)){
            return nearestLoad.get(tar);
        }
        if (tar instanceof IRVariable && tar.isConstant()) {
            if (((IRSimpleType) ((IRVariable) tar).type).bits == 32) {
                return new IRConstantValue(tar.getConstant(), new IRSimpleType(32));
            } else {
                return tryReplaceBool(tar);
            }
        } else {
            return tar;
        }
    }

    IREntity tryReplaceBool(IREntity tar) {
        if (tar instanceof IRVariable && tar.isConstant()) {
            return new IRConstantBool(tar.getConstant() == 1);
        } else {
            return tar;
        }
    }

    public void visit(IRRoot it) {
        it.definitions.forEach(def -> def.accept(this));
    }

    public void visit(IRClassDef it) {
    }

    public void visit(IRFunctionDef it) {
        nowFunc=it;
        collectAlloca(nowFunc);
        nearestLoad=new HashMap<>();
        it.functionBody.forEach(i -> {
            if (i instanceof IRBlock) i.accept(this);
        });
    }

    public void visit(IRGlobalDef it) {
    }

    public void visit(IRStringConstantDef it) {
    }

    public void visit(IRAlloca it) {
        if(nowFunc.activeAlloca.get(it.result).size()==1){
            //只在这个块被使用
            nowInstDel=true;
        }
    }

    public void visit(IRBinary it) {
        if (it.operand1.isConstant() && it.operand2.isConstant()) {
            nowInstDel = true;
            it.lhs.isConstant = true;
            if (it.op == IRBinary.opType.add) {
                it.lhs.val = it.operand1.getConstant() + it.operand2.getConstant();
            } else if (it.op == IRBinary.opType.sub) {
                it.lhs.val = it.operand1.getConstant() - it.operand2.getConstant();
            } else if (it.op == IRBinary.opType.mul) {
                it.lhs.val = it.operand1.getConstant() * it.operand2.getConstant();
            } else if (it.op == IRBinary.opType.sdiv) {
                if (it.operand2.getConstant() == 0) {
                    it.lhs.val = 114514;
                    return;
                }
                it.lhs.val = it.operand1.getConstant() / it.operand2.getConstant();
            } else if (it.op == IRBinary.opType.srem) {
                if (it.operand2.getConstant() == 0) {
                    it.lhs.val = 114514;
                    return;
                }
                it.lhs.val = it.operand1.getConstant() % it.operand2.getConstant();
            } else if (it.op == IRBinary.opType.shl) {
                it.lhs.val = it.operand1.getConstant() << it.operand2.getConstant();
            } else if (it.op == IRBinary.opType.ashr) {
                it.lhs.val = it.operand1.getConstant() >> it.operand2.getConstant();
            } else if (it.op == IRBinary.opType.xor) {
                it.lhs.val = it.operand1.getConstant() ^ it.operand2.getConstant();
            } else if (it.op == IRBinary.opType.or) {
                it.lhs.val = it.operand1.getConstant() | it.operand2.getConstant();
            } else if (it.op == IRBinary.opType.and) {
                it.lhs.val = it.operand1.getConstant() & it.operand2.getConstant();
            }
            return;
        }
        it.operand1 = tryReplace(it.operand1);
        it.operand2 = tryReplace(it.operand2);
    }

    public void visit(IRBlock it) {
        allocaNearestStore = new HashMap<>();
        for (int i = 0; i < it.statements.size(); ) {
            it.statements.get(i).accept(this);
            if (nowInstReplace) it.statements.set(i, rpl);
            if (nowInstDel) it.statements.remove(i);
            else ++i;
            nowInstDel = false;
            nowInstReplace = false;
        }
    }

    public void visit(IRCall it) {
        it.parameters.replaceAll(this::tryReplace);
    }

    public void visit(IRCompare it) {
        if (it.operand1.isConstant() && it.operand2.isConstant()) {
            nowInstDel = true;
            it.lhs.isConstant = true;
            if (it.op == IRCompare.condType.eq) {
                it.lhs.val = (it.operand1.getConstant() == it.operand2.getConstant()) ? 1 : 0;
            } else if (it.op == IRCompare.condType.ne) {
                it.lhs.val = (it.operand1.getConstant() != it.operand2.getConstant()) ? 1 : 0;
            } else if (it.op == IRCompare.condType.slt) {
                it.lhs.val = (it.operand1.getConstant() < it.operand2.getConstant()) ? 1 : 0;
            } else if (it.op == IRCompare.condType.sgt) {
                it.lhs.val = (it.operand1.getConstant() > it.operand2.getConstant()) ? 1 : 0;
            } else if (it.op == IRCompare.condType.sle) {
                it.lhs.val = (it.operand1.getConstant() <= it.operand2.getConstant()) ? 1 : 0;
            } else if (it.op == IRCompare.condType.sge) {
                it.lhs.val = (it.operand1.getConstant() >= it.operand2.getConstant()) ? 1 : 0;
            }
            return;
        }
        it.operand1 = tryReplace(it.operand1);
        it.operand2 = tryReplace(it.operand2);
    }

    public void visit(IRConditionalBr it) {
        it.cond = tryReplace(it.cond);
        if (it.cond instanceof IRConstantBool) {
            nowInstReplace = true;
            if (((IRConstantBool) it.cond).value) {
                rpl = new IRUnconditionalBr(it.ifTrue);
            } else {
                rpl = new IRUnconditionalBr(it.ifFalse);
            }
        }
    }

    public void visit(IRGetElementPtr it) {
        it.ptrVal= (IRVariable) tryReplace(it.ptrVal);
        it.idx.replaceAll(this::tryReplace);
    }

    public void visit(IRLoad it) {
        if(allocaNearestStore.containsKey(it.pointer)){
            if(nearestLoad.containsKey(it.result)){
                nearestLoad.replace(it.result,allocaNearestStore.get(it.pointer));
            }else{
                nearestLoad.put(it.result,allocaNearestStore.get(it.pointer));
            }
            nowInstDel=true;
        }
    }

    public void visit(IRPhi it) {
        it.valLabel.forEach((b, e) -> {
            it.valLabel.replace(b, tryReplace(e));
        });
    }

    public void visit(IRRet it) {
        it.returnValue = tryReplace(it.returnValue);
    }

    public void visit(IRStore it) {
        it.value=tryReplace(it.value);
        if(allocaNearestStore.containsKey(it.target)){
            allocaNearestStore.replace(it.target,it.value);
        }else{
            allocaNearestStore.put(it.target,it.value);
        }
        if(nowFunc.activeAlloca.containsKey(it.target)){
            if(nowFunc.activeAlloca.get(it.target).size()==1){
                //只在这个块被使用
                nowInstDel=true;
            }
        }
    }

    public void visit(IRUnconditionalBr it) {
    }
}
