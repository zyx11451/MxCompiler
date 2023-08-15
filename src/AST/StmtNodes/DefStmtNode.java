package AST.StmtNodes;

import AST.ASTVisitor;
import AST.DefNodes.DefNode;
import Utils.position;

public class DefStmtNode extends StmtNode {
    public DefNode def;

    public DefStmtNode(DefNode def, position pos) {
        super(pos);
        this.def = def;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
