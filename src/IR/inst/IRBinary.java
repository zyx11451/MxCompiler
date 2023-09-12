package IR.inst;

import IR.IRTypes.IRSimpleType;
import IR.IRTypes.IRType;
import IR.IRVisitor;
import IR.entity.IREntity;
import IR.entity.IRVariable;

public class IRBinary extends IRInst {
    public IRVariable lhs;
    public IRType type;
    public IREntity operand1;
    public IREntity operand2;

    public enum opType {
        add,
        sub,
        mul,
        sdiv,
        srem,
        shl,
        ashr,
        and,
        or,
        xor
    }

    public opType op;

    public IRBinary(IRVariable lhs, int type, IREntity operand1, IREntity operand2, opType op) {
        super();
        this.lhs = lhs;
        this.type = new IRSimpleType(type);
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.op = op;
    }
    public IRBinary(IRVariable lhs, IRType type, IREntity operand1, IREntity operand2, opType op) {
        super();
        this.lhs = lhs;
        this.type = type;
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.op = op;
    }

    public String toString() {
        return lhs.toString() +
                " = " +
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
