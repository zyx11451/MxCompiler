package ASM.inst;

import ASM.ASMVisitor;
import ASM.operand.Register;

public class ASMLa extends ASMInst{
    public Register rd;
    public String name;
    public ASMLa(Register rd,String name){
        this.rd=rd;
        this.name=name;
    }

    public String toString() {
        return "la "+rd.toString()+", "+name;
    }
    public void accept(ASMVisitor visitor){
        visitor.visit(this);
    }
}
