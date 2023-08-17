package IR.inst;

import IR.IRTypes.IRPointerType;
import IR.entity.entity;

public class store extends inst{
    public entity value;
    public IRPointerType pointer;
    public store(entity value,IRPointerType pointer){
        this.value=value;
        this.pointer=pointer;
    }
}
