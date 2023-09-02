package ASM.inst;

import ASM.ASMVisitor;
import ASM.operand.Register;

public class ASMMv extends ASMInst{
    public Register rd;
    public Register rs;
    public ASMMv(Register rd,Register rs){
        this.rd=rd;
        this.rs=rs;
    }

    public String toString() {
        return "mv "+rd.toString()+", "+rs.toString();
    }
    public void accept(ASMVisitor visitor){
        visitor.visit(this);
    }
}
