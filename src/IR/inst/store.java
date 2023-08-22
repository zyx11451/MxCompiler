package IR.inst;

import IR.IRTypes.IRPointerType;
import IR.entity.entity;
import IR.entity.variable;

public class store extends inst{
    public entity value;
    public IRPointerType pointer;
    public variable target;
    public store(entity value,IRPointerType pointer,variable target){
        this.value=value;
        this.pointer=pointer;
        this.target=target;
    }
    public String toString(){
        return "store "+pointer.targetType.toString()+" "+value.toString()+",ptr "+target.toString();
    }
}
