package AST.StmtNodes;

import AST.ASTVisitor;
import Utils.position;

public class EmptyStmtNode extends StmtNode{
    public EmptyStmtNode(position pos){
        super(pos);
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
