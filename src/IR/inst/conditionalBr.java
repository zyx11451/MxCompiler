package IR.inst;

import IR.entity.entity;

public class conditionalBr extends inst {
    public entity cond;
    public block ifTrue;
    public block ifFalse;
    public conditionalBr(entity cond, block ifTrue, block ifFalse){
        this.cond=cond;
        this.ifTrue=ifTrue;
        this.ifFalse=ifFalse;
    }

}
