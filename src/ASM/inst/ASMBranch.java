package ASM.inst;

import ASM.ASMVisitor;
import ASM.operand.ASMOperand;

public class ASMBranch extends ASMInst{
    public enum opType{
        beqz,
        bnez
    }
    public opType op;
    public ASMOperand cond;
    public String label;
    public ASMBranch(opType op,ASMOperand cond,String labelName){
        this.op=op;
        this.cond=cond;
        this.label=labelName;
    }
    public String toString(){
        return op.toString()+" "+cond.toString()+", "+label;
    }
    public void accept(ASMVisitor visitor){
        visitor.visit(this);
    }
}
