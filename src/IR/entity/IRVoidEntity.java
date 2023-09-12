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
}
