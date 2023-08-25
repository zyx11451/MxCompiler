package IR.IRDefs;

import IR.entity.variable;
import IR.inst.inst;

public class stringConstantDef extends def {
    public String value;
    public int size;
    public String name;
    public stringConstantDef(variable v,int size,String value){
        this.value=value+"\00";
        this.size=size;
        this.name=v.name;
    }
    String TransEscapeValue(){
        StringBuilder ans=new StringBuilder();
        for(int i=0;i<value.length();++i){
            if(value.charAt(i)=='\\'){
                ++i;
                if(value.charAt(i)=='n'){
                    ans.append("\0A");
                }else if(value.charAt(i)=='\\'){
                    ans.append("\\\\");
                }else if(value.charAt(i)=='\"'){
                    ans.append("\22");
                }
            }
            else ans.append(value.charAt(i));
        }
        return ans.toString();
    }
    public String toString(){
        return name+" = private unnamed_addr constant ["+size+" x i8] c\""+TransEscapeValue()+"\"";
    }
}
