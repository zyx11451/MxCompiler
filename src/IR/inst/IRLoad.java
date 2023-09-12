package IR.inst;

import IR.IRTypes.IRType;
import IR.IRVisitor;
import IR.entity.IRVariable;

public class IRLoad extends IRInst {
    public IRVariable result;
    public IRVariable pointer;
    //type已经能被pointer或result确定
    public IRLoad(IRVariable result, IRVariable pointer){
        this.result=result;
        this.pointer=pointer;
    }
    public IRType getType(){
        return result.type;
    }
    public String toString(){
        return result.toString()+" = load "+result.type.toString()+",ptr "+pointer.toString();
    }
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
