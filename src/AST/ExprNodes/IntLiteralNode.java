package AST.ExprNodes;

import AST.ASTVisitor;
import Utils.position;

public class IntLiteralNode extends ExprNode{
    public long value;//-2147483648
    public IntLiteralNode(long value, position pos){
        super(pos);
        this.value=value;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
