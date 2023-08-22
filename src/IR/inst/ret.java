package IR.inst;

import IR.entity.entity;
import IR.entity.voidEntity;

public class ret extends inst {
    public entity returnValue;
    public ret(entity returnValue){
        this.returnValue=returnValue;
    }
    public String toString(){
        if(returnValue instanceof voidEntity){
            return "ret void";
        }else{
            return "ret "+returnValue.getType().toString()+" "+returnValue.toString();
        }
    }
}
