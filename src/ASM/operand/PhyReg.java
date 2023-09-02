package ASM.operand;

public class PhyReg extends Register{
    public String name;
    public PhyReg(String name){
        this.name=name;
    }
    public String toString(){
        return name;
    }
}
