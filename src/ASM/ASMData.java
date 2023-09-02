package ASM;

public class ASMData {
    public String name;
    public boolean isString;
    public int val;
    public String stringVal=null;
    public ASMData(String name,String stringVal){
        this.name=name;
        this.stringVal=stringVal;
        this.isString=true;
    }
    public ASMData(String name,int val){
        this.name=name;
        this.val=val;
        this.isString=false;
    }
    public String toString(){
        StringBuilder ans=new StringBuilder();
        ans.append(" .section ");
        if(isString){
            ans.append(".rodata");
        }else{
            ans.append(".data");
        }
        ans.append("\n");
        ans.append(name).append(":\n ");
        if(isString){
            ans.append(".asciz");
        }else{
            ans.append(".word");
        }
        ans.append(" ");
        if(isString){
            ans.append("\"").append(stringVal).append("\"");
        } else if (stringVal!=null) {
            ans.append(stringVal);
        }else{
            long v=val;//ravel可能不支持负值，家里网比较难git clone
            if(val<0){
                v=val & 0xFFFFFFFFL;
            }
            ans.append(v);
        }
        ans.append("\n");
        return ans.toString();
    }
}
