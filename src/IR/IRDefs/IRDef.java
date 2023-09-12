package IR.IRDefs;

import IR.IRVisitor;

abstract public class IRDef {
    abstract public String toString();
    abstract public void accept(IRVisitor visitor);
}
