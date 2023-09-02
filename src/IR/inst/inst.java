package IR.inst;

import IR.IRVisitor;

public abstract class inst {
    public inst(){}
    abstract public String toString();
    abstract public void accept(IRVisitor visitor);
}
