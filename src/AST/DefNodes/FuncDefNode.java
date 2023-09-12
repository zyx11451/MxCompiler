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
    public Type returnType;//构造函数的returnType为null,不能有返回值
    public String funcName;
    public ArrayList<ParameterListElement> parameterList;

    public ArrayList<StmtNode> statements;

    public FuncDefNode(Type returnType, String name, position pos) {
        super(pos);
        this.returnType = returnType;
        this.funcName = name;
        this.parameterList = new ArrayList<>();
        this.statements = new ArrayList<>();
    }

    public void addParameter(Type type, String name, ExprNode init) {
        this.parameterList.add(new ParameterListElement(type, name, init));
    }

    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }

    public String getParameterName(int i) {
        return parameterList.get(i).name;
    }

    public Type getParameterType(int i) {
        return parameterList.get(i).type;
    }
}
