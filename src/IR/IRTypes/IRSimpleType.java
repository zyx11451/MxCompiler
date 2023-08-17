package IR.IRTypes;

public class IRSimpleType extends IRType{
    //ix 类型
    public int bits;
    public IRSimpleType(int bits){
        this.bits=bits;
    }
    public String toString(){
        return "i"+bits;
    }
}
