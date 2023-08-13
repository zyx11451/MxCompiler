package AST.ExprNodes;

import AST.ASTVisitor;
import Utils.position;

public class AssignExprNode extends ExprNode{
    public ExprNode lhs;
    public ExprNode rhs;
    public AssignExprNode(ExprNode lhs,ExprNode rhs,position pos){
        super(pos);
        this.lhs=lhs;
        this.rhs=rhs;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
