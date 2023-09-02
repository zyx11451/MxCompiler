package ASM.inst;

import ASM.ASMVisitor;

public class ASMJump extends ASMInst{
    public String labelName;
    public ASMJump(String name){
        this.labelName=name;
    }
    public String toString(){
        return "j "+labelName;
    }
    public void accept(ASMVisitor visitor){
        visitor.visit(this);
    }
}
