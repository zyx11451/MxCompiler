package AST.ExprNodes;

import AST.ASTVisitor;
import Utils.position;

public class BinaryExprNode extends ExprNode{
    public ExprNode lhs;
    public ExprNode rhs;
    public enum binaryOpType{
        multiply,divide,mod,plus,minus,leftShift,rightShift,greater,less,greaterEqual,lessEqual,equal,notEqual,and,or,caret,andand,oror
    }
    public binaryOpType op;
    public BinaryExprNode(ExprNode lhs,ExprNode rhs,binaryOpType op,position pos){
        super(pos);
        this.lhs=lhs;
        this.rhs=rhs;
        this.op=op;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

}
