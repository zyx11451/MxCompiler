package IR.IRTypes;

public class IRPointerType extends IRType{
    public IRType targetType;
    public int size(){
        return 4;
    }
    public IRPointerType(IRType targetType){
        this.targetType=targetType;
    }
    public String toString() {
        return "ptr";
    }
}
