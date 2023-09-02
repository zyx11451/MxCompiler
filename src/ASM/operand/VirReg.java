package ASM.operand;

public class VirReg extends Register{
    public int id;
    public VirReg(int id){
        this.id=id;
    }
    public String toString(){
        return "%"+id;
    }
}
