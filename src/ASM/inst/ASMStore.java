package ASM.inst;

import ASM.ASMVisitor;
import ASM.operand.Register;

public class ASMStore extends ASMInst{
    public Register rd;
    public Register rs;
    public int offset;
    public ASMStore(Register rd,Register rs,int offset){
        this.rd=rd;
        this.rs=rs;
        this.offset=offset;
    }
    public String toString() {
        return "sw "+rd.toString()+", "+offset+"("+rs.toString()+")";
    }
    public void accept(ASMVisitor visitor){
        visitor.visit(this);
    }
}
