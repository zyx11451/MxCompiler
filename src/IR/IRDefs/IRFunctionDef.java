package IR.IRDefs;


import IR.IRTypes.*;
import IR.IRVisitor;
import IR.entity.IRVariable;
import IR.inst.IRBlock;
import IR.inst.IRInst;
import Utils.Type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class IRFunctionDef extends IRDef {
    public String functionName;
    public IRType resultType;
    public HashMap<IRVariable, HashSet<IRBlock>> activeAlloca = new HashMap<>();//记录alloca的值在哪些块里被用到

    public ArrayList<IRVariable> parameters;
    public ArrayList<IRInst> functionBody;

    public IRType TransTypeToIRType(Type type) {
        if (type.dimension == 0) {
            if (type.isInt()) return new IRSimpleType(32);
            else if (type.isBool()) return new IRSimpleType(1);
            else if (type.isVoid()) return new IRVoidType();
            else return new IRPointerType(new IRBasicClassType(type.type_name));
        } else {
            Type elementType = new Type(type.type_name, type.dimension - 1);
            return new IRPointerType(new IRBasicArrayType(TransTypeToIRType(elementType)));
        }
    }

    public IRFunctionDef(String name, Type type) {
        this.functionName = name;
        this.resultType = TransTypeToIRType(type);
        this.parameters = new ArrayList<>();
        this.functionBody = new ArrayList<>();
    }

    public String toString() {
        StringBuilder ans = new StringBuilder("define " + resultType.toString() + " @" + functionName + "( ");
        for (IRVariable parameter : parameters) {
            ans.append(parameter.type.toString());
            ans.append(" ");
            ans.append(parameter.toString());
            ans.append(",");
        }
        ans.deleteCharAt(ans.length() - 1);
        ans.append("){\n");
        for (IRInst b : functionBody) {
            ans.append(b.toString());
            ans.append("\n");
        }
        ans.append("}");
        return ans.toString();
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
