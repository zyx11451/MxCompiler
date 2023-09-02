package IR.inst;

import AST.ASTVisitor;
import IR.IRTypes.IRType;
import IR.IRVisitor;
import IR.entity.entity;
import IR.entity.variable;

import java.util.ArrayList;

public class getElementPtr extends inst{
    public IRType type;//ptrVal指向的类型
    public variable ptrVal;//必须为指针类型
    public variable result;//也是指针
    public ArrayList<entity> idx;
    public getElementPtr(IRType type,variable ptrVal,variable result){
        this.type=type;
        this.ptrVal=ptrVal;
        this.result=result;
        idx=new ArrayList<>();
    }
    public void addIdx(entity e){
        idx.add(e);
    }
    public String toString(){
        StringBuilder ans=new StringBuilder();
        ans.append(result.toString());
        ans.append(" = getelementptr ");
        ans.append(type.toString());
        ans.append(",ptr ");
        ans.append(ptrVal.toString());
        for (entity i:idx){
            ans.append(",");
            ans.append(i.getType().toString());
            ans.append(" ");
            ans.append(i.toString());
        }
        return ans.toString();
    }
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
