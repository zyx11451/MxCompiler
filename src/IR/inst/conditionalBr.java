package IR.inst;

import IR.IRVisitor;
import IR.entity.entity;

public class conditionalBr extends inst {
    public entity cond;
    public block ifTrue;
    public block ifFalse;

    public conditionalBr(entity cond, block ifTrue, block ifFalse) {
        this.cond = cond;
        this.ifTrue = ifTrue;
        this.ifFalse = ifFalse;
    }

    public String toString() {
        return "br " + cond.getType().toString() + " " + cond.toString() + ",label %" + ifTrue.labelName + ",label %" + ifFalse.labelName;
    }
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
