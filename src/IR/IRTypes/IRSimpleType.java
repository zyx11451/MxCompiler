package IR.IRTypes;

public class IRSimpleType extends IRType{
    //ix 类型
    public int bits;
    public int size(){
        if(bits==32) return 4;
        else if(bits==1||bits==8) return 4;
        return 0;
    }
    public IRSimpleType(int bits){
        this.bits=bits;
    }
    public String toString(){
        return "i"+bits;
    }
}
