package IR.entity;

import IR.IRTypes.IRType;
import IR.entity.entity;

public class constantValue extends entity {
    public int value;
    public IRType type;
    public constantValue(long value){
        //TODO 无法解决-2147483648这种状况
        super();
        this.value=(int) value;
    }
    public IRType getType(){
        return type;
    }
}
