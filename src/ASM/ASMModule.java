package ASM;

import java.util.ArrayList;

public class ASMModule {
    public ArrayList<ASMData> data;
    public ArrayList<ASMFunction> function;
    public ASMModule(){
        data=new ArrayList<>();
        function=new ArrayList<>();
    }
    public String toString(){
        StringBuilder ans=new StringBuilder();
        ans.append(".text\n");
        function.forEach(f->ans.append(f.toString()).append("\n"));
        data.forEach(d->ans.append(d.toString()).append("\n"));
        return ans.toString();
    }
    public void accept(ASMVisitor visitor){
        visitor.visit(this);
    }
}
