package AST.ExprNodes;

import AST.ASTVisitor;
import Utils.position;

public class NewExprNode extends ExprNode{
    public NewExprNode(position pos){
        super(pos);
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
