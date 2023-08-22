package IR.IRTypes;

public class IRBasicArrayType extends IRType{
    public IRType elementType;//多维数组的此处为指向（多-1）维数组的指针
    public IRBasicArrayType(IRType t){
        elementType=t;
    }
    public int size(){
        return 0;//应该不需要
    }
    public String toString(){
        return "struct._array";
    }
}
