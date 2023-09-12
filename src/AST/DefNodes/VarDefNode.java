package AST.DefNodes;

import AST.ExprNodes.ExprNode;
import Utils.Type;
import Utils.position;

import java.util.ArrayList;

import AST.ASTVisitor;

class VarDefListElement {
    public String name;
    public Type type;
    public ExprNode init;

    public VarDefListElement(String name, Type type, ExprNode init) {
        this.name = name;
        this.type = type;
        this.init = init;
    }
}

public class VarDefNode extends DefNode {
    public ArrayList<VarDefListElement> defineList;

    public VarDefNode(position pos) {
        super(pos);
        this.defineList = new ArrayList<>();
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    public void add(String name, Type type, ExprNode init) {
        this.defineList.add(new VarDefListElement(name, type, init));
    }

    public String getDefineListName(int i) {
        return defineList.get(i).name;
    }

    public Type getDefineListType(int i) {
        return defineList.get(i).type;
    }

    public ExprNode getDefineListInit(int i) {
        return defineList.get(i).init;
    }

    public boolean haveInit(int i) {
        return defineList.get(i).init != null;
    }
}
