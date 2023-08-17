package IR.inst;

import IR.IRTypes.IRType;
import IR.entity.variable;

public class alloca extends inst {
    public variable result;
    public IRType type;
    public alloca(variable result,IRType type){
        this.result=result;
        this.type=type;
    }
}
