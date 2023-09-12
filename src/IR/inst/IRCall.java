package IR.inst;

import IR.IRTypes.IRType;
import IR.IRTypes.IRVoidType;
import IR.IRVisitor;
import IR.entity.IREntity;
import IR.entity.IRVariable;

import java.util.ArrayList;

public class IRCall extends IRInst {
    public String functionName;
    public IRType returnType;
    public IRVariable result=null;
    public ArrayList<IREntity> parameters;
    public IRCall(String name, IRType returnType){
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
        for(IREntity parameter:parameters){
            ans.append(parameter.getType().toString());
            ans.append(" ");
            ans.append(parameter.toString());
            ans.append(",");
        }
        if(!parameters.isEmpty()) ans.deleteCharAt(ans.length()-1);
        ans.append(")");
        return ans.toString();
    }
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
