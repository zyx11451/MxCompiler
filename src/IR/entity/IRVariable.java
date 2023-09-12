package IR.entity;

import IR.IRTypes.IRType;

public class IRVariable extends IREntity {
    public IRType type;//变量只能是ptr类型  吗？
    public String name;
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
}
