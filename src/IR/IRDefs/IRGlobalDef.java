package IR.IRDefs;

import IR.IRTypes.IRSimpleType;
import IR.IRTypes.IRType;
import IR.IRVisitor;

public class IRGlobalDef extends IRDef {
    //全局变量定义语句
    public String name;
    public IRType type;
    public IRGlobalDef(String name, IRType type){
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
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
