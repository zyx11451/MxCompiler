package ASM.inst;

import ASM.ASMVisitor;
import ASM.operand.ASMOperand;

public class ASMSet extends ASMInst{
    public enum opType{
        seqz,
        snez
    }
    public opType op;
    public ASMOperand rd;
    public ASMOperand rs;
    public ASMSet(opType op,ASMOperand rd,ASMOperand rs){
        this.op=op;
        this.rd=rd;
        this.rs=rs;
    }

    public String toString() {
        return op.toString()+" "+rd.toString()+", "+rs.toString();
    }
    public void accept(ASMVisitor visitor){
        visitor.visit(this);
    }
}
