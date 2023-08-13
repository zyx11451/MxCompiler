package AST.ExprNodes;

import AST.ASTNode;
import AST.ASTVisitor;
import Utils.position;

public class ArrayElementExprNode extends ExprNode {
    public ExprNode arrayExpr;
    public ExprNode indexExpr;

    public ArrayElementExprNode(ExprNode arrayExpr,ExprNode indexExpr,position pos){
        super(pos);
        this.arrayExpr=arrayExpr;
        this.indexExpr=indexExpr;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    public boolean isAssignable(){
        return true;
    }
}
