package AST.ExprNodes;

import AST.ASTVisitor;
import Utils.position;

public class VarExprNode extends ExprNode{
    public String name;
    public VarExprNode(String name, position pos){
        super(pos);
        this.name=name;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
    public boolean isAssignable() {return true;}
}
