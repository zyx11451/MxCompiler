package Utils;

import Utils.error.SemanticError;

import java.util.HashMap;

public class Scope {
    public HashMap<String,Type> variables;
    private final Scope parent;
    public boolean isClass;
    public boolean isFunc;//为了return
    public Type correctReturnType=null;//在isFunc=true的那一层记录正确的返回值。
    public Type returnType=null;//记录可能出现的return语句的返回类型,在某些涉及到Scope的节点处需要手动改;
    public boolean isLoop;//为了continue和break
    public Scope(Scope parentScope){
        variables=new HashMap<>();
        this.parent=parentScope;
        this.isClass=false;
        this.isFunc=false;
        this.isLoop=false;
    }
    public Scope parentScope(){return parent;}
    public void defineVariable(String name,Type t,position pos){
        if(variables.containsKey(name)) throw new SemanticError("Variable redefine",pos);
        variables.put(name,t);
    }
    public boolean containsVariable(String name,boolean lookUpon){
        if(variables.containsKey(name)) return true;
        else if(parent !=null && lookUpon){
            return parent.containsVariable(name,true);
        }
        else return false;
    }
    public Type getVarTypeFromName(String name,boolean lookUpon){
        if(variables.containsKey(name)) return variables.get(name);
        else if(parent != null && lookUpon){
            return parent.getVarTypeFromName(name,true);
        }
        else return null;
    }
    public boolean inClassScope(boolean lookUpon){
        if(isClass) return true;
        else if(parent != null && lookUpon){
            return parent.inClassScope(true);
        }
        return false;
    }
    public boolean inLoopScope(boolean lookUpon){
        if(isLoop) return true;
        else if(parent != null && lookUpon){
            return parent.inLoopScope(true);
        }
        return false;
    }
    public boolean inFuncScope(boolean lookUpon){
        if(isFunc) return true;
        else if(parent != null && lookUpon){
            return parent.inFuncScope(true);
        }
        return false;
    }
    public Type findReturnType(){
        if(isFunc) return correctReturnType;
        else if(parent != null ){
            return parent.findReturnType();
        }
        return null;
    }
    public String findClassName(){
        if(isClass) return ((ClassScope) this).className;
        else if(parent != null ){
            return parent.findClassName();
        }
        return null;
    }
    public Function findClassMethod(String name){
        if(isClass) return ((ClassScope) this).func.get(name);
        else if(parent != null ){
            return parent.findClassMethod(name);
        }
        return null;
    }
}
