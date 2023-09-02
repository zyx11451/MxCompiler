package ASM.operand;

public class Imm extends ASMOperand{
    public int value;
    public Imm(int value){
        this.value=value;
    }
    public String toString(){
        return String.valueOf(value);
    }
}
