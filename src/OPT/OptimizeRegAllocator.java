package OPT;

import ASM.ASMFunction;
import ASM.ASMModule;
import ASM.RegAllocator;
import ASM.inst.*;
import ASM.operand.*;

public class OptimizeRegAllocator extends RegAllocator {
    public ASMOperand getReg(ASMInst i, ASMOperand tar, PhyReg tmp, boolean isLoad) {
        if ((tar instanceof VirReg) && (((VirReg) tar).reg != null)) {
            return new PhyReg(((VirReg) tar).reg);
        } else {
            return getPhyReg(i, tar, tmp, isLoad);
        }
    }

    public void visit(ASMModule it) {
        new LiveAnalysis().visit(it);
        it.function.forEach(f -> f.accept(this));
    }

    public void visit(ASMFunction it) {
        nowFunc = it;
        new ConflictGraph(it);
        it.blocks.forEach(b -> b.accept(this));
    }

    public void visit(ASMBinary it) {
        it.rd = getReg(it, it.rd, new PhyReg("t0"), false);
        it.rs1 = getReg(it, it.rs1, new PhyReg("t1"), true);
        it.rs2 = getReg(it, it.rs2, new PhyReg("t2"), true);
    }

    public void visit(ASMBranch it) {
        it.cond = getReg(it, it.cond, new PhyReg("t0"), true);
    }

    public void visit(ASMLa it) {
        it.rd = (Register) getReg(it, it.rd, new PhyReg("t0"), false);
    }

    public void visit(ASMLi it) {
        it.rd = (Register) getReg(it, it.rd, new PhyReg("t0"), false);
    }

    public void visit(ASMLoad it) {
        it.rd = (Register) getReg(it, it.rd, new PhyReg("t0"), false);
        it.rs = (Register) getReg(it, it.rs, new PhyReg("t1"), true);
    }

    public void visit(ASMMv it) {
        it.rd = (Register) getReg(it, it.rd, new PhyReg("t0"), false);
        it.rs = (Register) getReg(it, it.rs, new PhyReg("t1"), true);
    }

    public void visit(ASMSet it) {
        it.rd = getReg(it, it.rd, new PhyReg("t0"), false);
        it.rs = getReg(it, it.rs, new PhyReg("t1"), true);
    }

    public void visit(ASMStore it) {
        it.rd = (Register) getReg(it, it.rd, new PhyReg("t0"), true);
        it.rs = (Register) getReg(it, it.rs, new PhyReg("t1"), true);
    }
}
