package IR;

import IR.IRDefs.IRFunctionDef;
import IR.entity.IRVariable;
import IR.inst.IRBlock;

import java.util.HashMap;

public class IRScope {
    public int variableNum=0;
    public IRScope parent;
    public HashMap<String, IRVariable> var=new HashMap<>();//用来存储作用域中的变量
    public IRBlock nowBlock=null;
    public IRFunctionDef nowFunc=null;
    public boolean isFunctionScope=false;
    public boolean isClassScope=false;
    public IRScope(IRScope parent){
        this.parent=parent;
        if(parent!=null) this.variableNum+= parent.variableNum;
    }
    public IRVariable findVar(String name, boolean lookUpon){
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
