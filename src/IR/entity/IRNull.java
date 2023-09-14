package IR.entity;

import IR.IRTypes.IRPointerType;
import IR.IRTypes.IRType;

public class IRNull extends IREntity {
    public IRType getType(){
        return new IRPointerType(null);
    }
    public String toString(){
        return "null";
    };
    public boolean isConstant(){
        return true;
    }
    public int getConstant(){
        return 0;
    }
}
