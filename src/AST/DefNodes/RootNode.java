package AST.DefNodes;

import AST.ASTNode;
import AST.ASTVisitor;
import Utils.Type;
import Utils.position;

import java.util.ArrayList;

public class RootNode extends ASTNode {
    public ArrayList<DefNode> defs;
    Type intType;
    Type boolType;
    Type stringType;
    public RootNode(position pos){
        super(pos);
        defs=new ArrayList<>();
        intType=new Type("int");
        boolType=new Type("bool");
        stringType=new Type("string");
    }
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
