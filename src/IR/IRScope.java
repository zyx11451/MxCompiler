package IR;

import IR.IRDefs.FunctionDef;
import IR.entity.variable;
import IR.inst.block;

import java.util.HashMap;

public class IRScope {
    public int variableNum=0;
    public IRScope parent;
    public HashMap<String, variable> var=new HashMap<>();//用来存储作用域中的变量
    public block nowBlock=null;
    public FunctionDef nowFunc=null;
    public boolean isFunctionScope=false;
    public boolean isClassScope=false;
    public IRScope(IRScope parent){
        this.parent=parent;
        if(parent!=null) this.variableNum+= parent.variableNum;
    }
    public variable findVar(String name,boolean lookUpon){
        if(var.containsKey(name)){
            return var.get(name);
        }else if(parent!=null&&lookUpon){
            return parent.findVar(name,true);
        }else{
            return null;
        }
    }
    public boolean isClassMember(String name){
        if(var.containsKey(name)){
            return this.isClassScope;
        }else if(parent!=null){
            return parent.isClassMember(name);
        }else{
            return false;
        }
    }

}
