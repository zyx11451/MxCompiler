package Utils;

import Utils.error.SemanticError;

import java.util.HashMap;

public class ClassScope extends Scope{
    //类作用域
    public final HashMap<String, Function> func;
    public String className;

    public ClassScope(Scope parentScope,String name) {
        super(parentScope);
        func = new HashMap<>();
        this.isClass=true;
        className=name;
    }

    public void addType(String name, Type t) {
       throw new SemanticError("You cannot define a class in a ClassScope in Mx*", null);
    }

    public void addFunc(String name, Function t) {
        if (func.containsKey(name)) throw new SemanticError("Function " + name + " redeclare", null);
        func.put(name, t);
    }

    public void addVar(String name, Type t) {
        //Semantic Check 阶段先这样写，后面需要值的时候应该会改。
        if (variables.containsKey(name)) throw new SemanticError("Variable " + name + " redefine", null);
        variables.put(name, t);
    }

    public Type getReturnTypeFromName(String name) {
        if (func.containsKey(name)) return func.get(name).returnType;
        return null;
    }

}
