package AST.StmtNodes;

import AST.ASTVisitor;
import AST.ExprNodes.ExprNode;
import Utils.position;

public class PureExprStmtNode extends StmtNode{
    public ExprNode expression;
    public PureExprStmtNode(ExprNode expression,position pos){
        super(pos);
        this.expression=expression;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
