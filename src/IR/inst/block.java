package IR.inst;

import IR.IRVisitor;

import java.util.ArrayList;

public class block extends inst {
    public String labelName=null;
    public ArrayList<inst> statements;
    public block(String labelName){
        super();
        this.labelName=labelName;
        statements=new ArrayList<>();
    }
    public String getLabelName() {
        return labelName;
    }

    public void addStatements(inst s){
        statements.add(s);
    }
    public String toString(){
        StringBuilder ans= new StringBuilder(labelName + ":\n");
        for (inst statement : statements) {
            if (statement.toString() != null) {
                ans.append(statement).append("\n");
            }
        }
        return ans.toString();
    }
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
