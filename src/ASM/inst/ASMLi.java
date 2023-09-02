package ASM.inst;

import ASM.ASMVisitor;
import ASM.operand.Imm;
import ASM.operand.Register;

public class ASMLi extends ASMInst{
    public Register rd;
    public Imm imm;
    public ASMLi(Register rd,Imm imm){
        this.rd=rd;
        this.imm=imm;
    }

    public String toString() {
        return "li "+rd.toString()+", "+imm.toString();
    }
    public void accept(ASMVisitor visitor){
        visitor.visit(this);
    }
}
