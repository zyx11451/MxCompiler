package AST.ExprNodes;

import AST.ASTVisitor;
import Utils.position;

public class IntLiteralNode extends ExprNode{
    public int value;
    public IntLiteralNode(int value, position pos){
        super(pos);
        this.value=value;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
