package IR.IRDefs;

import IR.IRTypes.*;
import IR.IRVisitor;
import IR.entity.variable;
import Utils.Function;
import Utils.Type;

import java.util.ArrayList;
import java.util.HashMap;

public class FunctionDec extends def{
    public String functionName;//函数原名
    public IRType resultType;
    public ArrayList<variable> parameters;
    public HashMap<String,variable> newParameterName;//方便找对应
    int parameterNum;
    public FunctionDec(String name,IRType resultType){
        this.functionName=name;
        this.resultType=resultType;
        this.parameters=new ArrayList<>();
        this.newParameterName=new HashMap<>();
        this.parameterNum=0;
    }
    IRType TransTypeToIRType(Type type) {
        if(type.dimension==0){
            if (type.isInt()) return new IRSimpleType(32);
            else if (type.isBool()) return new IRSimpleType(1);
            else return new IRPointerType(new IRBasicClassType(type.type_name));
        }else{
            Type elementType=new Type(type.type_name,type.dimension-1);
            return new IRPointerType(new IRBasicArrayType(TransTypeToIRType(elementType)));
        }
    }
    public FunctionDec(Function f){
        this.parameterNum=0;
        resultType=TransTypeToIRType(f.returnType);
        this.parameters=new ArrayList<>();
        this.newParameterName=new HashMap<>();
        this.functionName=f.functionName;
        for(int i=0;i<f.parameters.size();++i){
            variable p=new variable(TransTypeToIRType(f.getParameterType(i)),"%_"+parameterNum);
            ++parameterNum;
            parameters.add(p);
            newParameterName.put(f.getParameterName(i),p );
        }
    }
    public String toString(){
        StringBuilder ans= new StringBuilder("declare "+resultType.toString()+" @"+functionName+"( ");
        for(variable parameter:parameters){
            ans.append(parameter.type.toString());
            ans.append(" ");
            ans.append(parameter.toString());
            ans.append(" ");
        }
        ans.append(")");
        return ans.toString();
    }
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
