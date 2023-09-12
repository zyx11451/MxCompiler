package IR.inst;

import IR.IRVisitor;

public class IRUnconditionalBr extends IRInst {
    public IRBlock dest;
    public IRUnconditionalBr(IRBlock dest){
        super();
        this.dest=dest;
    }
    public String toString(){
        return "br label %"+dest.labelName;
    }
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
