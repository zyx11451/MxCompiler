package ASM.inst;

import ASM.ASMVisitor;
import ASM.operand.ASMOperand;

public class ASMBinary extends ASMInst{
    public enum opType{
        addi,
        add,
        sub,
        mul,
        div,
        rem,
        and,
        or,
        xor,
        xori,
        sll,
        sra,
        slli,
        slt,
    }
    public opType op;
    public ASMOperand rd;
    public ASMOperand rs1;
    public ASMOperand rs2;
    public ASMBinary(opType op,ASMOperand rd,ASMOperand rs1,ASMOperand rs2){
        this.op=op;
        this.rd=rd;
        this.rs1=rs1;
        this.rs2=rs2;
    }
    public String toString(){
        return op.toString()+" "+rd.toString()+", "+rs1.toString()+", "+rs2.toString();
    }
    public void accept(ASMVisitor visitor){
        visitor.visit(this);
    }
}
