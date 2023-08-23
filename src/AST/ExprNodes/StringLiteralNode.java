package AST.ExprNodes;

import AST.ASTVisitor;
import Utils.Type;
import Utils.position;

public class StringLiteralNode extends ExprNode{
    public String value;
    public StringLiteralNode(String value, position pos){
        super(pos);
        this.value=value;
        this.type=new Type("string");
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
