package AST.DefNodes;

import AST.ASTVisitor;
import Utils.position;

import java.util.ArrayList;

public class ClassDefNode extends DefNode {
    public String classname;
    public ArrayList<DefNode> elements;

    public ClassDefNode(String name, position pos) {
        super(pos);
        this.elements = new ArrayList<>();
        this.classname = name;
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
