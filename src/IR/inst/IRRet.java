package IR.inst;

import IR.IRVisitor;
import IR.entity.IREntity;
import IR.entity.IRVoidEntity;

public class IRRet extends IRInst {
    public IREntity returnValue;
    public IRRet(IREntity returnValue){
        this.returnValue=returnValue;
    }
    public String toString(){
        if(returnValue instanceof IRVoidEntity){
            return "ret void";
        }else{
            return "ret "+returnValue.getType().toString()+" "+returnValue.toString();
        }
    }
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
