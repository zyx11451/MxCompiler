package IR.IRDefs;

import IR.IRTypes.IRSimpleType;
import IR.IRTypes.IRType;
import IR.entity.entity;
import IR.entity.variable;

public class GlobalDef extends def{
    //全局变量定义语句
    public String name;
    public IRType type;
    public GlobalDef(String name,IRType type){
        this.name=name;
        this.type=type;
    }
    public String toString(){
        if(type instanceof IRSimpleType){
            return name+" = global "+type.toString()+" 0 ";
        }else{
            return name+" = global ptr null";
        }

    }
}
