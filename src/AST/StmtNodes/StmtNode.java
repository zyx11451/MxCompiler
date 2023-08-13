package AST.StmtNodes;

import AST.ASTNode;
import Utils.position;

abstract public class StmtNode extends ASTNode {
    public StmtNode(position pos){
        super(pos);
    }
}
