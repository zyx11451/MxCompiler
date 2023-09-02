package ASM.inst;

import ASM.ASMVisitor;

public class ASMRet extends ASMInst{
    public String toString() {
        return "ret";
    }
    public void accept(ASMVisitor visitor){
        visitor.visit(this);
    }
}
