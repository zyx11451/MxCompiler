package AST.ExprNodes;

import AST.ASTVisitor;
import Utils.position;

public class NullNode extends ExprNode{
    public NullNode(position pos){
        super(pos);
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
