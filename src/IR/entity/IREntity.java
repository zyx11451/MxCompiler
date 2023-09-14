package IR.entity;

import IR.IRTypes.IRType;

abstract public class IREntity {
    abstract public IRType getType();
    abstract public String toString();
    abstract public boolean isConstant();
    abstract public int getConstant();
}
