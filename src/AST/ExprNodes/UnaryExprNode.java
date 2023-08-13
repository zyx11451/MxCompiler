package AST.ExprNodes;

import AST.ASTNode;
import AST.ASTVisitor;
import Utils.position;

public class UnaryExprNode extends ExprNode{
    public enum unaryOpType{
        not,tilde,plus,minus
    }
    public unaryOpType op;
    public ExprNode expression;

    public UnaryExprNode(ExprNode expression,unaryOpType op,position pos){
        super(pos);
        this.op=op;
        this.expression=expression;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
