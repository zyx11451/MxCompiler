package IR.inst;

import IR.IRTypes.IRType;
import IR.IRVisitor;
import IR.entity.variable;

public class alloca extends inst {
    public variable result;
    public IRType type;
    public alloca(variable result,IRType type){
        this.result=result;
        this.type=type;
    }
    public String toString(){
        return result.toString()+" = alloca "+type.toString();
    }
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
