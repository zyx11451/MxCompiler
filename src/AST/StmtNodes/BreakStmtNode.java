package AST.StmtNodes;

import AST.ASTVisitor;
import Utils.position;

public class BreakStmtNode extends StmtNode{
    public BreakStmtNode(position pos){
        super(pos);
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
