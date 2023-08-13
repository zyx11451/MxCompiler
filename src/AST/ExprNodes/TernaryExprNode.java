package AST.ExprNodes;

import AST.ASTVisitor;
import Utils.position;

public class TernaryExprNode extends ExprNode{
    public ExprNode judgeExpr;
    public ExprNode trueExpr;
    public ExprNode falseExpr;
    public TernaryExprNode(ExprNode judgeExpr,ExprNode trueExpr,ExprNode falseExpr,position pos){
        super(pos);
        this.judgeExpr=judgeExpr;
        this.trueExpr=trueExpr;
        this.falseExpr=falseExpr;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
