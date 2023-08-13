package AST.ExprNodes;

import AST.ASTNode;
import AST.ASTVisitor;
import Utils.position;

import java.util.ArrayList;

public class FunctionCallExprNode extends ExprNode {
    public String funcName;
    public ArrayList<ExprNode> parameters;
    public FunctionCallExprNode(String name,position pos){
        super(pos);
        this.parameters=new ArrayList<>();
        this.funcName=name;
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
