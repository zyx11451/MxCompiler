package AST.ExprNodes;

import AST.ASTVisitor;
import Utils.position;

public class ErrorArrayExprNode extends ExprNode{
    //这种节点出现就说明输入有问题
    public ErrorArrayExprNode(position pos){
        super(pos);
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
