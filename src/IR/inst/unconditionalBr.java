package IR.inst;

import IR.IRVisitor;

public class unconditionalBr extends inst {
    public block dest;
    public unconditionalBr(block dest){
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
