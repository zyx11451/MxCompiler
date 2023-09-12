package IR.inst;

import IR.IRTypes.IRType;
import IR.IRVisitor;
import IR.entity.IREntity;
import IR.entity.IRVariable;

import java.util.ArrayList;

public class IRGetElementPtr extends IRInst {
    public IRType type;//ptrVal指向的类型
    public IRVariable ptrVal;//必须为指针类型
    public IRVariable result;//也是指针
    public ArrayList<IREntity> idx;
    public IRGetElementPtr(IRType type, IRVariable ptrVal, IRVariable result){
        this.type=type;
        this.ptrVal=ptrVal;
        this.result=result;
        idx=new ArrayList<>();
    }
    public void addIdx(IREntity e){
        idx.add(e);
    }
    public String toString(){
        StringBuilder ans=new StringBuilder();
        ans.append(result.toString());
        ans.append(" = getelementptr ");
        ans.append(type.toString());
        ans.append(",ptr ");
        ans.append(ptrVal.toString());
        for (IREntity i:idx){
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
