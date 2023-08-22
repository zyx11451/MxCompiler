package IR.entity;

import IR.IRTypes.IRType;
import IR.IRTypes.IRVoidType;

public class voidEntity extends entity{
    public IRType getType(){
        return new IRVoidType();
    }
    public String toString(){
        return "void";
    };
}
