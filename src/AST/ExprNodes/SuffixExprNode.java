package AST.ExprNodes;

import AST.ASTVisitor;
import Utils.position;

public class SuffixExprNode extends ExprNode{
    public enum suffixOpType{
        addadd,subsub
    }
    public suffixOpType op;
    public ExprNode expression;
    public SuffixExprNode(ExprNode expression, suffixOpType op, position pos){
        super(pos);
        this.expression=expression;
        this.op=op;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
