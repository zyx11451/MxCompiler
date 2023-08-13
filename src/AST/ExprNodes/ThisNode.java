package AST.ExprNodes;

import AST.ASTVisitor;
import Utils.position;

public class ThisNode extends ExprNode{
    public ThisNode(position pos){
        super(pos);
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
