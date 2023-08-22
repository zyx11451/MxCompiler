package IR.IRDefs;

import java.util.ArrayList;

public class IRRoot {
    //根节点
    public ArrayList<def> definitions;
    public IRRoot(){
        definitions=new ArrayList<>();
    }

    public String toString() {
        StringBuilder ans=new StringBuilder();
        for(def definition:definitions){
            ans.append(definition.toString());
            ans.append("\n");
        }
        return ans.toString();
    }
}
