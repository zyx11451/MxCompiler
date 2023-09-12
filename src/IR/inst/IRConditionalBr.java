package IR.inst;

import IR.IRVisitor;
import IR.entity.IREntity;

public class IRConditionalBr extends IRInst {
    public IREntity cond;
    public IRBlock ifTrue;
    public IRBlock ifFalse;

    public IRConditionalBr(IREntity cond, IRBlock ifTrue, IRBlock ifFalse) {
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
