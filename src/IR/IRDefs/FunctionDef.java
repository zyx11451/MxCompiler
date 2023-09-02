package IR.IRDefs;


import IR.IRTypes.*;
import IR.IRVisitor;
import IR.entity.variable;
import IR.inst.inst;
import Utils.Type;

import java.util.ArrayList;

public class FunctionDef extends def{
    public String functionName;
    public IRType resultType;
    public ArrayList<variable> parameters;
    public ArrayList<inst> functionBody;
    public IRType TransTypeToIRType(Type type) {
        if(type.dimension==0){
            if (type.isInt()) return new IRSimpleType(32);
            else if (type.isBool()) return new IRSimpleType(1);
            else if (type.isVoid()) return new IRVoidType();
            else return new IRPointerType(new IRBasicClassType(type.type_name));
        }else{
            Type elementType=new Type(type.type_name,type.dimension-1);
            return new IRPointerType(new IRBasicArrayType(TransTypeToIRType(elementType)));
        }
    }

    public FunctionDef(String name,Type type){
        this.functionName=name;
        this.resultType=TransTypeToIRType(type);
        this.parameters=new ArrayList<>();
        this.functionBody=new ArrayList<>();
    }
    public String toString(){
        StringBuilder ans= new StringBuilder("define "+resultType.toString()+" @"+functionName+"( ");
        for(variable parameter:parameters){
            ans.append(parameter.type.toString());
            ans.append(" ");
            ans.append(parameter.toString());
            ans.append(",");
        }
        ans.deleteCharAt(ans.length()-1);
        ans.append("){\n");
        for(inst b:functionBody){
            ans.append(b.toString());
            ans.append("\n");
        }
        ans.append("}");
        return ans.toString();
    }
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
