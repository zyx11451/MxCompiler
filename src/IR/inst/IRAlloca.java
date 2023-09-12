package IR.inst;

import IR.IRTypes.IRType;
import IR.IRVisitor;
import IR.entity.IRVariable;

public class IRAlloca extends IRInst {
    public IRVariable result;
    public IRType type;
    public IRAlloca(IRVariable result, IRType type){
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
