package IR.entity;

import IR.IRTypes.IRPointerType;
import IR.IRTypes.IRType;

public class variable extends entity {
    public IRType type;//变量只能是ptr类型  吗？
    public String name;
    public variable(IRType type,String name){
        this.type=type;
        this.name=name;
    }
    public IRType getType(){
        return type;
    }
    public String toString(){
        return name;
    };
}
