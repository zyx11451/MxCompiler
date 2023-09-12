package IR.inst;

import IR.IRVisitor;

public abstract class IRInst {
    public IRInst(){}
    abstract public String toString();
    abstract public void accept(IRVisitor visitor);
}
