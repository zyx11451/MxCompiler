package IR.entity;

import IR.IRTypes.IRType;

public class IRVariable extends IREntity {
    public IRType type;
    public String name;
    public int val=0;
    public boolean isConstant=false;
    public IRVariable(IRType type, String name){
        this.type=type;
        this.name=name;
    }
    public IRType getType(){
        return type;
    }
    public String toString(){
        return name;
    };
    public boolean isGlobal(){
        return name.charAt(0)=='@';
    }
    public boolean isConstant(){
        return isConstant;
    }
    public int getConstant(){
        return val;
    }
}
