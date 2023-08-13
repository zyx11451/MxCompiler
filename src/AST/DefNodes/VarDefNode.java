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
    public ArrayList<VarDefListElement> define_list;

    public VarDefNode(position pos) {
        super(pos);
        this.define_list = new ArrayList<>();
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    public void add(String name, Type type, ExprNode init) {
        this.define_list.add(new VarDefListElement(name, type, init));
    }

    public String getDefineListName(int i) {
        return define_list.get(i).name;
    }

    public Type getDefineListType(int i) {
        return define_list.get(i).type;
    }

    public ExprNode getDefineListInit(int i) {
        return define_list.get(i).init;
    }

    public boolean haveInit(int i) {
        return define_list.get(i).init != null;
    }
}
