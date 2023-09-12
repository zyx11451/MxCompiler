package IR.IRDefs;

import IR.IRVisitor;
import IR.entity.IRVariable;

public class IRStringConstantDef extends IRDef {
    public String value;
    public int size;
    public String name;

    public IRStringConstantDef(IRVariable v, int size, String value){
        this.value=value+"\00";
        this.size=size;
        this.name=v.name;
    }
    public String toString(){
        StringBuilder ans=new StringBuilder();
        int IROutSize=0;
        for(int i=0;i<value.length();++i){
            if(value.charAt(i)=='\\'){
                ++i;
                if(value.charAt(i)=='n'){
                    ans.append("\\0A");
                    ++IROutSize;
                }else if(value.charAt(i)=='\\'){
                    ans.append("\\\\");
                    ++IROutSize;
                }else if(value.charAt(i)=='\"'){
                    ans.append("\\22");
                    ++IROutSize;
                }
            }
            else {
                ans.append(value.charAt(i));
                ++IROutSize;
            }
        }
        return name+" = private unnamed_addr constant ["+IROutSize+" x i8] c\""+ans.toString()+"\"";
    }
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
