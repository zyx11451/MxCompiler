package ASM.inst;

import ASM.ASMVisitor;

public class ASMCall extends ASMInst{
    public String funcName;
    public ASMCall(String funcName){
        this.funcName=funcName;
    }

    public String toString() {
        return "call "+funcName;
    }
    public void accept(ASMVisitor visitor){
        visitor.visit(this);
    }
}
