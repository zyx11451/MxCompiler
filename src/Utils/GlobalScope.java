package Utils;

import Utils.error.SemanticError;

import java.util.HashMap;

public class GlobalScope extends Scope {
    //全局作用域
    public final HashMap<String, Type> types;//用来存类
    public final HashMap<String, Function> func;

    public GlobalScope(Scope parentScope) {
        super(parentScope);
        types = new HashMap<>();
        func = new HashMap<>();
    }

    public void addType(String name, Type t) {
        if (types.containsKey(name)) throw new SemanticError("Class " + name + " redefine", null);
        types.put(name, t);
    }

    public void addFunc(String name, Function t) {
        if (func.containsKey(name)) throw new SemanticError("Function " + name + " redeclare", null);
        func.put(name, t);
    }
    public Type getTypeFromName(String name) {
        if (types.containsKey(name)) return types.get(name);
        return null;
    }

    public Type getReturnTypeFromName(String name) {
        if (func.containsKey(name)) return func.get(name).returnType;
        return null;
    }

}
