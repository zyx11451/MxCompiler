package IR.inst;

import IR.IRTypes.IRPointerType;
import IR.IRVisitor;
import IR.entity.IREntity;
import IR.entity.IRVariable;

public class IRStore extends IRInst {
    public IREntity value;
    public IRPointerType pointer;
    public IRVariable target;
    public IRStore(IREntity value, IRPointerType pointer, IRVariable target){
        this.value=value;
        this.pointer=pointer;
        this.target=target;
    }
    public String toString(){
        return "store "+pointer.targetType.toString()+" "+value.toString()+",ptr "+target.toString();
    }
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
