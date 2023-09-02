package IR.IRDefs;

import IR.IRVisitor;

abstract public class def {
    abstract public String toString();
    abstract public void accept(IRVisitor visitor);
}
