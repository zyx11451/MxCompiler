package AST.ExprNodes;

import AST.ASTNode;
import AST.ASTVisitor;
import Utils.position;

public class PrefixExprNode extends ExprNode {
    public enum prefixOpType{
        addadd,subsub
    }
    public prefixOpType op;
    public ExprNode expression;
    public PrefixExprNode(ExprNode expression,prefixOpType op,position pos){
        super(pos);
        this.op=op;
        this.expression=expression;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    public boolean isAssignable(){
        return true;
    }
}
