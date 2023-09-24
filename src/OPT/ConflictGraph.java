package OPT;

import ASM.ASMFunction;
import ASM.inst.ASMInst;
import ASM.operand.VirReg;
import Utils.error.SemanticError;

import java.util.HashSet;
import java.util.Stack;

public class ConflictGraph {
    //不进行move合并
    public ASMFunction func;
    public HashSet<VirReg> nodes=new HashSet<>();

    int k=11;//可供分配的寄存器个数
    String[] reg={"s1","s2","s3","s4","s5","s6","s7","s8","s9","s10","s11"};
    public Stack<VirReg> selectedStack=new Stack<>();
    public Stack<VirReg> spilledStack=new Stack<>();

    public void conflictWith(VirReg a,VirReg b){
        if(a.conflict==null){
            a.conflict=new HashSet<>();
        }
        if(b.conflict==null){
            b.conflict=new HashSet<>();
        }
        a.conflict.add(b);
        b.conflict.add(a);
    }
    public void removeNode(VirReg a){
        a.conflict.forEach(v->{
            if(v==a) throw new SemanticError("conflict with itself "+a.id,null);
            v.conflict.remove(a);
        });
        selectedStack.push(a);
        nodes.remove(a);
    }
    public void spillNode(VirReg a){
        a.conflict.forEach(v->{
            if(v==a) throw new SemanticError("conflict with itself",null);
            v.conflict.remove(a);
        });
        spilledStack.push(a);
        nodes.remove(a);
    }
    public ConflictGraph(ASMFunction f){
        func=f;
        build();
        while (!nodes.isEmpty()){
            simplify();
            spill();
        }
        select();
    }
    public void build(){
        for(VirReg n:nodes){
            if(n.conflict==null)
                n.conflict=new HashSet<>();
        }
        func.blocks.forEach(b->{
            for(ASMInst i=b.head;i!=null;i=i.next){
                ASMInst nowInst = i;
                nowInst.def.forEach(v1-> nowInst.in.forEach(v2->conflictWith(v1,v2)));
                nodes.addAll(nowInst.def);
            }
        });
        for(VirReg n:nodes){
            n.initialConflict=new HashSet<>(n.conflict);
        }
    }
    public void simplify(){
        HashSet<VirReg> toRemove=new HashSet<>();
        nodes.forEach(n->{
            if(n.conflict.size()<k){
                toRemove.add(n);
            }
        });
        toRemove.forEach(this::removeNode);
    }
    public void spill(){
        VirReg toSpill=null;
        if(!nodes.isEmpty()){
            for(VirReg n:nodes){
                if(toSpill==null) toSpill=n;
                else{
                    if(n.conflict.size()>toSpill.conflict.size()) toSpill=n;
                }
            }

        }
        if(toSpill!=null) spillNode(toSpill);
    }
    public void select(){
        while (!selectedStack.isEmpty()){
            VirReg toColor=selectedStack.pop();
            HashSet<String> usedColor=new HashSet<>();
            toColor.initialConflict.forEach(v->{
                if(v.reg!=null){
                    usedColor.add(v.reg);
                }
            });
            for(int i=0;i<k;++i){
                if(!usedColor.contains(reg[i])) toColor.reg=reg[i];
            }
        }
        while (!spilledStack.isEmpty()){
            VirReg toColor=spilledStack.pop();
            HashSet<String> usedColor=new HashSet<>();
            toColor.initialConflict.forEach(v->{
                if(v.reg!=null){
                    usedColor.add(v.reg);
                }
            });
            if(usedColor.size()!=k){
                for(int i=0;i<k;++i){
                    if(!usedColor.contains(reg[i])) toColor.reg=reg[i];
                }
            }
        }
    }
}
