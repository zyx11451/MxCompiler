package IR.inst;

import IR.IRTypes.IRType;
import IR.IRVisitor;
import IR.entity.variable;

public class load extends inst{
    public variable result;
    public variable pointer;
    //type已经能被pointer或result确定
    public load(variable result,variable pointer){
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
