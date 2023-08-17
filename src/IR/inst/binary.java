package IR.inst;

import IR.entity.entity;
import IR.entity.variable;

public class binary extends inst {
    public variable lhs;
    public int type;//i type
    public entity operand1;
    public entity operand2;
    public enum opType{
        add,
        sub ,
        mul ,
        sdiv ,
        srem ,
        shl ,
        ashr ,
        and ,
        or ,
        xor
    }
    public opType op;
    public binary(variable lhs, int type, entity operand1, entity operand2, opType op){
        super();
        this.lhs=lhs;
        this.type=type;
        this.operand1=operand1;
        this.operand2=operand2;
        this.op=op;
    }

}
