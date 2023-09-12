package AST;

import Utils.position;

abstract public class ASTNode {
    public position pos;

    public ASTNode(position p) {
        this.pos = p;
    }

    abstract public void accept(ASTVisitor visitor);
}
