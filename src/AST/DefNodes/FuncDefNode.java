package AST.DefNodes;

import AST.ASTVisitor;
import AST.ExprNodes.ExprNode;
import AST.StmtNodes.StmtNode;
import Utils.Type;
import Utils.position;

import java.util.ArrayList;

class ParameterListElement {
    public Type type;
    public String name;
    public ExprNode init;

    ParameterListElement(Type type, String name, ExprNode init) {
        this.type = type;
        this.name = name;
        this.init = init;
    }
}

public class FuncDefNode extends DefNode {
    public boolean isConstructor = false;
    public Type return_type;//构造函数的returnType为null,不能有返回值
    public String func_name;
    public ArrayList<ParameterListElement> parameter_list;

    public ArrayList<StmtNode> statements;

    public FuncDefNode(Type return_type, String name, position pos) {
        super(pos);
        this.return_type = return_type;
        this.func_name = name;
        this.parameter_list = new ArrayList<>();
        this.statements = new ArrayList<>();
    }

    public void addParameter(Type type, String name, ExprNode init) {
        this.parameter_list.add(new ParameterListElement(type, name, init));
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    public String getParameterName(int i) {
        return parameter_list.get(i).name;
    }

    public Type getParameterType(int i) {
        return parameter_list.get(i).type;
    }
}
