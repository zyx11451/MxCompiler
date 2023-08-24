package IR.inst;

import IR.IRTypes.IRType;
import IR.entity.entity;
import IR.entity.variable;

import java.util.HashMap;

public class phi extends inst {
    public variable result;
    public IRType type;
    public HashMap<block, entity> valLabel;//顺序不重要

    public phi(variable result, IRType type) {
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
}
