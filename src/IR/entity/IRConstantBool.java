package IR.entity;

import IR.IRTypes.IRSimpleType;
import IR.IRTypes.IRType;

public class IRConstantBool extends IREntity {
    public boolean value;
    public IRType type;
    public IRConstantBool(boolean value){
        //TODO 无法解决-2147483648这种状况
        super();
        this.value=value;
        this.type=new IRSimpleType(1);
    }
    public IRType getType(){
        return type;
    }
    public String toString(){
        if(value) return "true";
        else return "false";
    };
}
