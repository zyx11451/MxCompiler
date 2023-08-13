package AST.StmtNodes;

import AST.ASTVisitor;
import AST.ExprNodes.ExprNode;
import Utils.position;

public class ForStmtNode extends StmtNode{
    public StmtNode init;
    public ExprNode condition;
    public ExprNode step;
    public StmtNode thenStmt;
    public ForStmtNode(StmtNode init,ExprNode condition,ExprNode step,StmtNode thenStmt,position pos){
        super(pos);
        this.init=init;
        this.condition=condition;
        this.step=step;
        this.thenStmt=thenStmt;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
