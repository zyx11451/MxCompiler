package AST.StmtNodes;

import AST.ASTVisitor;
import Utils.position;

public class ContinueStmtNode extends StmtNode{
    public ContinueStmtNode(position pos){
        super(pos);
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}