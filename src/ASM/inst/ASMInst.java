package ASM.inst;

import ASM.ASMVisitor;

public abstract class ASMInst {
    public ASMInst prev=null;
    public ASMInst next=null;
    abstract public String toString();
    abstract public void accept(ASMVisitor visitor);
}
