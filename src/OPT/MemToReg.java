package OPT;

import IR.IRDefs.*;
import IR.IRVisitor;
import IR.inst.*;

public class MemToReg implements IRVisitor {
    public void visit(IRRoot it){
    }
    public void visit(IRClassDef it){}
    public void visit(IRFunctionDef it){}
    public void visit(IRGlobalDef it){}
    public void visit(IRStringConstantDef it){}
    public void visit(IRAlloca it){}
    public void visit(IRBinary it){}
    public void visit(IRBlock it){}
    public void visit(IRCall it){}
    public void visit(IRCompare it){}
    public void visit(IRConditionalBr it){}
    public void visit(IRGetElementPtr it){}
    public void visit(IRLoad it){}
    public void visit(IRPhi it){}
    public void visit(IRRet it){}
    public void visit(IRStore it){}
    public void visit(IRUnconditionalBr it){}
}
