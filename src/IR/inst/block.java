package IR.inst;

import java.util.ArrayList;

public class block extends inst {
    public String labelName=null;
    public ArrayList<inst> statements;
    public block(){
        super();
        statements=new ArrayList<>();
    }
    public String getLabelName() {
        return labelName;
    }

    public void addStatements(inst s){
        statements.add(s);
    }
}
