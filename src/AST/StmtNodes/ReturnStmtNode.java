package AST.StmtNodes;

import AST.ASTVisitor;
import AST.ExprNodes.ExprNode;
import Utils.position;

public class ReturnStmtNode extends StmtNode{
    public ExprNode return_expression;
    public ReturnStmtNode(ExprNode return_expression,  position pos){
        super(pos);
        this.return_expression=return_expression;

    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
