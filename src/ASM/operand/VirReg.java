package ASM.operand;

import java.util.HashSet;

public class VirReg extends Register{
    public int id;
    public VirReg(int id){
        this.id=id;
    }
    public String toString(){
        return "%"+id;
    }
    public String reg=null;//表示被分配到哪个寄存器，为null代表没被染色

    public HashSet<VirReg> conflict=new HashSet<>();
    public HashSet<VirReg> initialConflict=null;//记录简化前的冲突关系以便重建冲突图
}
