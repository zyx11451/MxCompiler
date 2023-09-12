package IR.inst;

import IR.IRTypes.IRType;
import IR.IRVisitor;
import IR.entity.IREntity;
import IR.entity.IRVariable;

import java.util.HashMap;

public class IRPhi extends IRInst {
    public IRVariable result;
    public IRType type;
    public HashMap<IRBlock, IREntity> valLabel;//顺序不重要

    public IRPhi(IRVariable result, IRType type) {
        this.result = result;
        this.type = type;
        this.valLabel=new HashMap<>();
    }

    public String toString() {
        StringBuilder ans = new StringBuilder();
        ans.append(result.toString());
        ans.append(" = phi ");
        ans.append(type.toString());
        valLabel.forEach((s, e) -> ans.append("[ ").append(e.toString()).append(",%").append(s.labelName).append("],"));
        ans.deleteCharAt(ans.length()-1);
        return ans.toString();
    }
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
