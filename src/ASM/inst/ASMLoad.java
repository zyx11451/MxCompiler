package ASM.inst;

import ASM.ASMVisitor;
import ASM.operand.Register;

public class ASMLoad extends ASMInst{
    public Register rd;
    public Register rs;
    public int offset;
    public ASMLoad(Register rd,Register rs,int offset){
        this.rd=rd;
        this.rs=rs;
        this.offset=offset;
    }
    public String toString() {
        return "lw "+rd.toString()+", "+offset+"("+rs.toString()+")";
    }
    public void accept(ASMVisitor visitor){
        visitor.visit(this);
    }
}
