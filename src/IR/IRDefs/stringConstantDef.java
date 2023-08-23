package IR.IRDefs;

import IR.entity.variable;
import IR.inst.inst;

public class stringConstantDef extends def {
    public String value;
    public int size;
    public String name;
    public stringConstantDef(variable v,int size,String value){
        this.value=value+"\00";
        this.size=size;
        this.name=v.name;
    }
    public String toString(){
        return name+" = private unnamed_addr constant ["+size+" x i8] c\""+value+"\"";
    }
}
