package IR.inst;

import IR.entity.entity;

public class ret extends inst {
    public entity returnValue;
    public ret(entity returnValue){
        this.returnValue=returnValue;
    }
}
