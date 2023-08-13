package AST.StmtNodes;

import AST.ASTVisitor;
import AST.StmtNodes.StmtNode;
import Utils.position;

import java.util.ArrayList;

public class BlockStmtNode extends StmtNode {
    public ArrayList<StmtNode> stmts;

    public BlockStmtNode(position pos) {
        super(pos);
        this.stmts = new ArrayList<>();
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
