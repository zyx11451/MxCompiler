package IR.inst;

import IR.IRVisitor;

import java.util.ArrayList;

public class IRBlock extends IRInst {
    public String labelName=null;
    public ArrayList<IRInst> statements;
    public ArrayList<IRBlock> nextBlock=new ArrayList<>();
    public boolean haveTerminal=false;
    public IRBlock(String labelName){
        super();
        this.labelName=labelName;
        statements=new ArrayList<>();
    }
    public String getLabelName() {
        return labelName;
    }

    public void addStatements(IRInst s){
        statements.add(s);
    }
    public String toString(){
        StringBuilder ans= new StringBuilder(labelName + ":\n");
        for (IRInst statement : statements) {
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
