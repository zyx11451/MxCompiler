package IR.inst;

import IR.IRTypes.IRSimpleType;
import IR.IRTypes.IRType;
import IR.IRVisitor;
import IR.entity.IREntity;
import IR.entity.IRVariable;

public class IRCompare extends IRInst {
    public IRVariable lhs;
    public IRType type;
    public IREntity operand1;
    public IREntity operand2;
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
    public IRCompare(IRVariable lhs, int type, IREntity operand1, IREntity operand2, condType op){
        super();
        this.lhs=lhs;
        this.type=new IRSimpleType(type);
        this.operand1=operand1;
        this.operand2=operand2;
        this.op=op;
    }
    public IRCompare(IRVariable lhs, IRType type, IREntity operand1, IREntity operand2, condType op){
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
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
