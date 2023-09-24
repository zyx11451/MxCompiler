package ASM.inst;

import ASM.ASMVisitor;
import ASM.operand.VirReg;

import java.util.HashSet;

public abstract class ASMInst {
    public ASMInst prev=null;
    public ASMInst next=null;
    abstract public String toString();
    abstract public void accept(ASMVisitor visitor);
    public HashSet<VirReg> use=new HashSet<>();
    public HashSet<VirReg> def=new HashSet<>();
    public HashSet<VirReg> in=new HashSet<>();
    public HashSet<VirReg> out=new HashSet<>();
}
