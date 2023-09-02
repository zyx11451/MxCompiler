package IR;

import IR.IRDefs.*;
import IR.inst.*;

public interface IRVisitor {
    public void visit(IRRoot it);
    public void visit(ClassDef it);
    public void visit(FunctionDef it);
    public void visit(FunctionDec it);
    public void visit(GlobalDef it);
    public void visit(stringConstantDef it);
    public void visit(alloca it);
    public void visit(binary it);
    public void visit(block it);
    public void visit(call it);
    public void visit(compare it);
    public void visit(conditionalBr it);
    public void visit(getElementPtr it);
    public void visit(load it);
    public void visit(phi it);
    public void visit(ret it);
    public void visit(store it);
    public void visit(unconditionalBr it);
}
