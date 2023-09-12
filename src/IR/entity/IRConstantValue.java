package IR.entity;

import IR.IRTypes.IRType;

public class IRConstantValue extends IREntity {
    public int value;
    public IRType type;
    public IRConstantValue(long value, IRType type){
        //TODO 无法解决-2147483648这种状况
        super();
        this.value=(int) value;
        this.type=type;
    }
    public IRType getType(){
        return type;
    }
    public String toString(){
        return Integer.toString(value);
    };
}
