package AST.StmtNodes;

import AST.ASTVisitor;
import AST.ExprNodes.ExprNode;
import AST.StmtNodes.StmtNode;
import Utils.position;

public class IfStmtNode extends StmtNode {
    public ExprNode condition;
    public StmtNode thenStmt;
    public StmtNode elseStmt;
    public IfStmtNode(ExprNode condition, StmtNode thenStmt, StmtNode elseStmt, position pos){
        super(pos);
        this.condition=condition;
        this.thenStmt=thenStmt;
        this.elseStmt=elseStmt;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
