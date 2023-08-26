package IR.inst;

import IR.IRTypes.IRSimpleType;
import IR.IRTypes.IRType;
import IR.entity.entity;
import IR.entity.variable;

public class compare extends inst {
    public variable lhs;
    public IRType type;
    public entity operand1;
    public entity operand2;
    public enum condType{
        eq,
        ne,
        ugt,
        uge,
        ult,
        ule,
        sgt,
        sge,
        slt,
        sle
    }
    public condType op;
    public compare(variable lhs, int type, entity operand1, entity operand2, condType op){
        super();
        this.lhs=lhs;
        this.type=new IRSimpleType(type);
        this.operand1=operand1;
        this.operand2=operand2;
        this.op=op;
    }
    public compare(variable lhs, IRType type, entity operand1, entity operand2, condType op){
        super();
        this.lhs=lhs;
        this.type=type;
        this.operand1=operand1;
        this.operand2=operand2;
        this.op=op;
    }
    public String toString(){
        return lhs.toString() +
                " = icmp " +
                op.toString() +
                " " +
                type.toString() +
                " " +
                operand1.toString() +
                "," +
                operand2.toString();
    }
}
