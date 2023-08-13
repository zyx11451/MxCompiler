package AST.ExprNodes;

import AST.ASTNode;
import AST.ASTVisitor;
import Utils.position;

public class BoolLiteralNode extends ExprNode {
    public boolean value;
    public BoolLiteralNode(boolean value, position pos){
        super(pos);
        this.value=value;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
