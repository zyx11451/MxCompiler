package IR.inst;

import IR.IRTypes.IRType;
import IR.IRTypes.IRVoidType;
import IR.entity.entity;
import IR.entity.variable;

import java.util.ArrayList;

public class call extends inst{
    public String functionName;
    public IRType returnType;
    public variable result=null;
    public ArrayList<entity> parameters;
    public call(String name,IRType returnType){
        this.functionName=name;
        this.returnType=returnType;
        parameters=new ArrayList<>();
    }
    public String toString(){
        StringBuilder ans=new StringBuilder();
        if(returnType instanceof IRVoidType){
            ans.append("call void @").append(functionName).append("(");
        }else{
            ans.append(result.toString()).append(" = call ").append(returnType.toString()).append(" @").append(functionName).append("(");
        }
        for(entity parameter:parameters){
            ans.append(parameter.getType().toString());
            ans.append(" ");
            ans.append(parameter.toString());
            ans.append(",");
        }
        ans.deleteCharAt(ans.length()-1);
        ans.append(")");
        return ans.toString();
    }
}
