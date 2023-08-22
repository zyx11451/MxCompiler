package IR.IRTypes;

import IR.entity.constantValue;

public class IRArrayType extends IRType{
    public IRType elementType;
    public constantValue size;
    public IRArrayType(IRType t,constantValue s){
        this.elementType=t;
        this.size=s;
    }
    public int size(){
        return 0;//应该不需要
    }
    public String toString() {
        return "["+size.value+" x "+elementType.toString()+"]";
    }
}
