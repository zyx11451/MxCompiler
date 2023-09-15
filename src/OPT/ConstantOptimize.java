package OPT;

import IR.IRDefs.*;
import IR.IRTypes.IRSimpleType;
import IR.IRVisitor;
import IR.entity.IRConstantValue;
import IR.entity.IREntity;
import IR.entity.IRVariable;
import IR.inst.*;

import java.util.ArrayList;

public class ConstantOptimize implements IRVisitor {
    boolean nowInstDel=false;
    IREntity tryReplace(IREntity tar){
        if(tar instanceof IRVariable&&tar.isConstant()&&((IRSimpleType) ((IRVariable) tar).type).bits==32){
            return new IRConstantValue(tar.getConstant(),new IRSimpleType(32));
        }else{
            return tar;
        }
    }
    public void visit(IRRoot it) {
        it.definitions.forEach(def -> def.accept(this));
    }

    public void visit(IRClassDef it) {
    }

    public void visit(IRFunctionDef it) {
        it.functionBody.forEach(i -> {
            if(i instanceof IRBlock) i.accept(this);
        });
    }

    public void visit(IRGlobalDef it) {
    }

    public void visit(IRStringConstantDef it) {
    }

    public void visit(IRAlloca it) {
    }

    public void visit(IRBinary it) {
        if (it.operand1.isConstant()&&it.operand2.isConstant()){
            nowInstDel=true;
            it.lhs.isConstant=true;
            if(it.op== IRBinary.opType.add){
                it.lhs.val=it.operand1.getConstant()+it.operand2.getConstant();
            } else if (it.op== IRBinary.opType.sub) {
                it.lhs.val=it.operand1.getConstant()-it.operand2.getConstant();
            } else if (it.op== IRBinary.opType.mul) {
                it.lhs.val=it.operand1.getConstant()*it.operand2.getConstant();
            } else if (it.op== IRBinary.opType.sdiv) {
                if(it.operand2.getConstant()==0){
                    it.lhs.val=114514;
                    return;
                }
                it.lhs.val=it.operand1.getConstant()/it.operand2.getConstant();
            } else if (it.op== IRBinary.opType.srem) {
                if(it.operand2.getConstant()==0){
                    it.lhs.val=114514;
                    return;
                }
                it.lhs.val=it.operand1.getConstant()%it.operand2.getConstant();
            } else if (it.op== IRBinary.opType.shl) {
                it.lhs.val=it.operand1.getConstant()<<it.operand2.getConstant();
            } else if (it.op== IRBinary.opType.ashr) {
                it.lhs.val=it.operand1.getConstant()>>it.operand2.getConstant();
            } else if (it.op== IRBinary.opType.xor) {
                it.lhs.val=it.operand1.getConstant()^it.operand2.getConstant();
            } else if (it.op== IRBinary.opType.or) {
                it.lhs.val=it.operand1.getConstant()|it.operand2.getConstant();
            }else if (it.op== IRBinary.opType.and) {
                it.lhs.val=it.operand1.getConstant()&it.operand2.getConstant();
            }
            return;
        }
        it.operand1=tryReplace(it.operand1);
        it.operand2=tryReplace(it.operand2);
    }

    public void visit(IRBlock it) {
        for(int i=0;i<it.statements.size();){
            it.statements.get(i).accept(this);
            if(nowInstDel) it.statements.remove(i);
            else ++i;
            nowInstDel=false;
        }
    }

    public void visit(IRCall it) {
        it.parameters.replaceAll(this::tryReplace);
    }

    public void visit(IRCompare it) {
        it.operand1=tryReplace(it.operand1);
        it.operand2=tryReplace(it.operand2);
    }

    public void visit(IRConditionalBr it) {
    }

    public void visit(IRGetElementPtr it) {
        it.idx.replaceAll(this::tryReplace);
    }

    public void visit(IRLoad it) {
    }

    public void visit(IRPhi it) {
        it.valLabel.forEach((b,e)->{
            it.valLabel.replace(b,tryReplace(e));
        });
    }

    public void visit(IRRet it) {
        it.returnValue=tryReplace(it.returnValue);
    }

    public void visit(IRStore it) {
        it.value=tryReplace(it.value);
    }

    public void visit(IRUnconditionalBr it) {
    }
}