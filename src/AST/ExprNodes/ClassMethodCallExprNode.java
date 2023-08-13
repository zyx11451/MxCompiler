package AST.ExprNodes;

import AST.ASTVisitor;
import Utils.position;

import java.util.ArrayList;

public class ClassMethodCallExprNode extends ExprNode{
    public ExprNode classExpr;
    public String methodName;
    public ArrayList<ExprNode> parameters;
    public ClassMethodCallExprNode(ExprNode classExpr,String name, position pos){
        super(pos);
        this.classExpr=classExpr;
        this.parameters=new ArrayList<>();
        this.methodName=name;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
