package AST.ExprNodes;

import AST.ASTNode;
import Utils.Type;
import Utils.position;

abstract public class ExprNode extends ASTNode {
    public Type type;
    public ExprNode(position pos){
        super(pos);
    }
    public boolean isAssignable(){
        return false;
    }
}
