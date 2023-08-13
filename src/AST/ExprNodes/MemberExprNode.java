package AST.ExprNodes;

import AST.ASTNode;
import AST.ASTVisitor;
import Utils.position;

public class MemberExprNode extends ExprNode {
    public ExprNode familyExpr;
    public String memberName;
    public MemberExprNode(ExprNode familyExpr,String name,position pos){
        super(pos);
        this.familyExpr=familyExpr;
        this.memberName=name;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    public boolean isAssignable() {return true;}
}
