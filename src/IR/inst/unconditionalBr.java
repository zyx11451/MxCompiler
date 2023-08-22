package IR.inst;

public class unconditionalBr extends inst {
    block dest;
    public unconditionalBr(block dest){
        super();
        this.dest=dest;
    }
    public String toString(){
        return "br label %"+dest.labelName;
    }
}
