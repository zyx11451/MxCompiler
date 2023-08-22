package IR.entity;

import IR.IRTypes.IRPointerType;
import IR.IRTypes.IRType;

public class IRNull extends entity{
    public IRType getType(){
        return new IRPointerType(null);
    }
    public String toString(){
        return "null";
    };
}
