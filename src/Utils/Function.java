package Utils;

import Utils.error.SemanticError;

import java.util.ArrayList;
import java.util.HashMap;

class FunctionParameter {
    public Type type;
    public String name;

    public FunctionParameter(String name, Type type) {
        this.name = name;
        this.type = type;
    }
}

public class Function {
    public boolean isConstructor = false;
    public Type returnType;
    public ArrayList<FunctionParameter> parameters;//核对参数类型是否正确

    public HashMap<String, Type> parameterType;//方便在函数内查找函数参数类型
    public String functionName;

    public Function(String name, Type returnType) {
        this.functionName = name;
        this.returnType = returnType;
        this.parameters = new ArrayList<>();
        this.parameterType =new HashMap<>();
    }

    public int parameterNum() {
        return parameters.size();
    }

    public void addParameter(String name, Type type) {
        if (parameterType.containsKey(name))
            throw new SemanticError("Repeated parameter name in function " + functionName, null);
        parameters.add(new FunctionParameter(name, type));
        parameterType.put(name, type);
    }

    public boolean parameterTypeCheck(ArrayList<Type> in) {
        if (in.size() != parameterNum()) return false;
        for (int i = 0; i < parameterNum(); ++i) {
            if (!parameters.get(i).type.equals(in.get(i)) ) return false;
        }
        return true;
    }
}
