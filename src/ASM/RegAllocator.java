package ASM;

import ASM.inst.*;
import ASM.operand.*;

public class RegAllocator implements ASMVisitor {
    public ASMFunction nowFunc;
    public ASMBlock nowBlock;
    //为溢出变量重写代码，目前是全部重写状态
    public ASMOperand getPhyReg(ASMInst i, ASMOperand tar, PhyReg tmp, boolean isLoad) {
        if (tar instanceof VirReg) {
            if (!nowFunc.stackOffset.containsKey(tar)) {
                nowFunc.allocate((Register) tar);
            }
            int offset = -nowFunc.stackOffset.get(tar);
            if (offset >= -2048 && offset < 2048) {
                if(isLoad) nowBlock.insert_before(i,new ASMLoad(tmp,new PhyReg("s0"),offset));
                else nowBlock.insert_after(i,new ASMStore(tmp,new PhyReg("s0"),offset));
            }else{
                if(isLoad){
                    nowBlock.insert_before(i,new ASMLi(new PhyReg("t3"),new Imm(offset)));
                    nowBlock.insert_before(i,new ASMBinary(ASMBinary.opType.add,new PhyReg("t3"),new PhyReg("s0"),new PhyReg("t3")));
                    nowBlock.insert_before(i,new ASMLoad(tmp,new PhyReg("t3"),0));
                }else{
                    nowBlock.insert_after(i,new ASMStore(tmp,new PhyReg("t3"),0));
                    nowBlock.insert_after(i,new ASMBinary(ASMBinary.opType.add,new PhyReg("t3"),new PhyReg("s0"),new PhyReg("t3")));
                    nowBlock.insert_after(i,new ASMLi(new PhyReg("t3"),new Imm(offset)));
                }
            }
            return tmp;
        } else {
            return tar;
        }
    }

    public void visit(ASMModule it) {
        it.function.forEach(f->f.accept(this));
    }

    public void visit(ASMFunction it) {
        nowFunc=it;
        it.blocks.forEach(b->b.accept(this));
    }

    public void visit(ASMBlock it) {
        nowBlock=it;
        for(ASMInst i=it.head;i!=null;i=i.next){
            i.accept(this);
        }
    }

    public void visit(ASMBinary it) {
        it.rd=getPhyReg(it, it.rd, new PhyReg("t0"),false);
        it.rs1=getPhyReg(it,it.rs1,new PhyReg("t1"),true);
        it.rs2=getPhyReg(it, it.rs2, new PhyReg("t2"),true);
    }

    public void visit(ASMBranch it) {
        it.cond=getPhyReg(it,it.cond,new PhyReg("t0"),true);
    }

    public void visit(ASMCall it) {
    }

    public void visit(ASMJump it) {
    }

    public void visit(ASMLa it) {
        it.rd=(Register) getPhyReg(it, it.rd, new PhyReg("t0"),false);
    }

    public void visit(ASMLi it) {
        it.rd=(Register) getPhyReg(it,it.rd,new PhyReg("t0"),false);
    }

    public void visit(ASMLoad it) {
        it.rd=(Register) getPhyReg(it,it.rd,new PhyReg("t0"),false);
        it.rs=(Register) getPhyReg(it,it.rs,new PhyReg("t1"),true);
    }

    public void visit(ASMMv it) {
        it.rd=(Register) getPhyReg(it,it.rd,new PhyReg("t0"),false);
        it.rs=(Register) getPhyReg(it,it.rs,new PhyReg("t1"),true);
    }

    public void visit(ASMRet it) {
    }

    public void visit(ASMSet it) {
        it.rd=(Register) getPhyReg(it, it.rd, new PhyReg("t0"),false);
        it.rs=(Register) getPhyReg(it,it.rs,new PhyReg("t1"),true);
    }

    public void visit(ASMStore it) {
        it.rd=(Register) getPhyReg(it,it.rd,new PhyReg("t0"),true);
        it.rs=(Register) getPhyReg(it,it.rs,new PhyReg("t1"),true);
    }
}
