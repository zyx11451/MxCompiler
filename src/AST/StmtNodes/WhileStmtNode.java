package AST.StmtNodes;

import AST.ASTVisitor;
import AST.ExprNodes.ExprNode;
import Utils.position;

public class WhileStmtNode extends StmtNode{
    public ExprNode condition;
    public StmtNode thenStmt;
    public WhileStmtNode(ExprNode condition,StmtNode thenStmt,position pos){
        super(pos);
        this.condition=condition;
        this.thenStmt=thenStmt;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
