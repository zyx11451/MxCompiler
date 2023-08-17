package IR.IRTypes;

import java.util.ArrayList;

public class IRClassType extends IRType{
    public String className;
    public ArrayList<IRType> classElements;
    public IRClassType(String className){
        this.className=className;
        classElements=new ArrayList<>();
    }
    public void addElements(IRType e){
        classElements.add(e);
    }
    public String toString() {
        return "class."+className;
    }
}
