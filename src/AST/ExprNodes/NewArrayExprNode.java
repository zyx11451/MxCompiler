package AST.ExprNodes;

import AST.ASTVisitor;
import Utils.Type;
import Utils.position;

import java.util.ArrayList;

public class NewArrayExprNode extends ExprNode{
    public ArrayList<ExprNode> bounds;
    public NewArrayExprNode(position pos){
        super(pos);
        this.bounds=new ArrayList<>();
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
