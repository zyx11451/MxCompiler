package IR.entity;

import IR.IRTypes.IRType;
import IR.IRTypes.IRVoidType;

public class IRVoidEntity extends IREntity {
    public IRType getType(){
        return new IRVoidType();
    }
    public String toString(){
        return "void";
    };
    public boolean isConstant(){
        return true;
    }
    public int getConstant(){
        return 0;
    }
}
