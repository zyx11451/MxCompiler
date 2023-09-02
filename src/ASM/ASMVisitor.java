package ASM;

import ASM.inst.*;

public interface ASMVisitor {
    public void visit(ASMModule it);
    public void visit(ASMFunction it);
    public void visit(ASMBlock it);
    public void visit(ASMBinary it);
    public void visit(ASMBranch it);
    public void visit(ASMCall it);
    public void visit(ASMJump it);
    public void visit(ASMLa it);
    public void visit(ASMLi it);
    public void visit(ASMLoad it);
    public void visit(ASMMv it);
    public void visit(ASMRet it);
    public void visit(ASMSet it);
    public void visit(ASMStore it);
}
