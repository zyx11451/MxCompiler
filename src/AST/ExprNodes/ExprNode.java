package AST.ExprNodes;

import AST.ASTNode;
import IR.entity.IREntity;
import Utils.Type;
import Utils.position;

abstract public class ExprNode extends ASTNode {
    public Type type;
    public IREntity ent = null;//在IRBuilder中记录表达式得出的值是什么

    public ExprNode(position pos) {
        super(pos);
    }

    public boolean isAssignable() {
        return false;
    }
}
