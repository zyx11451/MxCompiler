package IR.IRDefs;

import IR.IRTypes.*;
import Utils.Type;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassDef extends def {
    public String name;
    public ArrayList<IRType> elements;
    public HashMap<String, Integer> index;//方便找到成员是第几个

    int elementNum;

    public ClassDef(String name) {
        this.name = name;
        elements = new ArrayList<>();
        index = new HashMap<>();
        elementNum=0;
    }

    IRType TransTypeToIRType(Type type) {
        if(type.dimension==0){
            if (type.isInt()) return new IRSimpleType(32);
            else if (type.isBool()) return new IRSimpleType(1);
            else return new IRPointerType(new IRBasicClassType(type.type_name));
        }else{
            Type elementType=new Type(type.type_name,type.dimension-1);
            return new IRPointerType(new IRBasicArrayType(TransTypeToIRType(elementType)));
        }
    }

    public ClassDef(String name, Type type) {
        elementNum=0;
        elements = new ArrayList<>();
        index = new HashMap<>();
        this.name = name;
        type.members.forEach((n, t) -> {
            elements.add(TransTypeToIRType(t));
            index.put(n,elementNum);
            ++elementNum;
        });
    }
    public String toString(){
        StringBuilder ans= new StringBuilder("%class."+name + " = type { ");
        for(IRType element:elements){
            ans.append(element.toString()).append(" ,");
        }
        ans.deleteCharAt(ans.length()-1);
        ans.append("}");
        return ans.toString();
    }
}
