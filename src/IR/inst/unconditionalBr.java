package IR.inst;

public class unconditionalBr extends inst {
    block dest;
    public unconditionalBr(block dest){
        super();
        this.dest=dest;
    }
}
