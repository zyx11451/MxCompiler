package OPT;

import ASM.ASMBlock;
import ASM.ASMFunction;
import ASM.ASMModule;
import ASM.ASMVisitor;
import ASM.inst.*;
import ASM.operand.ASMOperand;
import ASM.operand.VirReg;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class LiveAnalysis implements ASMVisitor {
    boolean updated = true;
    ASMBlock nowBlock = null;

    void tryAddUse(ASMInst it, ASMOperand tar) {
        if (tar instanceof VirReg) {
            it.use.add((VirReg) tar);
        }
    }

    void tryAddDef(ASMInst it, ASMOperand tar) {
        if (tar instanceof VirReg) {
            it.def.add((VirReg) tar);
        }
    }

    void buildCFG(ASMFunction f) {
        f.blocks.forEach(b -> f.blockNameMap.put(b.labelName, b));
        f.blocks.forEach(b -> {
            b.nextBlockName.forEach(nextBlkName -> {
                ASMBlock nextBlk = f.blockNameMap.get(nextBlkName);
                b.next.add(nextBlk);
                nextBlk.prev.add(b);
            });
        });
    }

    public void visit(ASMModule it) {
        it.function.forEach(f -> f.accept(this));
    }

    public void visit(ASMFunction it) {
        buildCFG(it);
        updated=true;//让其进行第一次
        //不能dfs，因为遇到循环之类的就会一直绕圈无限循环。需迭代找不动点。
        while (updated) {
            updated = false;
            //todo 适当排序加快到不动点的速度
            it.blocks.forEach(b -> b.accept(this));
        }
        //更新块内指令的in和out
        it.blocks.forEach(b->{
            for(ASMInst i=b.tail;i!=null;i=i.prev){
                ASMInst nowInst = i;
                if(nowInst.next==null){
                    nowInst.out.addAll(b.out);
                }else{
                    nowInst.out.addAll(nowInst.next.in);
                }
                nowInst.in.addAll(nowInst.use);
                nowInst.out.forEach(v->{
                    if(!nowInst.def.contains(v)){
                        nowInst.in.add(v);
                    }
                });
            }
        });
    }

    public void visit(ASMBlock it) {
        if (!it.allInstVisited) {
            nowBlock = it;
            for (ASMInst i = it.head; i != null; i = i.next) {
                i.accept(this);
                for(VirReg v:i.use){
                    if(!it.def.contains(v)) it.use.add(v);
                }
                it.def.addAll(i.def);
            }
            updated=true;
            it.allInstVisited=true;
            //初始in
            it.in.addAll(it.use);
        }
        //更新out
        it.next.forEach(b->{
            b.in.forEach(v->{
                if(!it.out.contains(v)){
                    updated=true;
                    it.out.add(v);
                }
            });
        });
        //更新in
        it.out.forEach(v->{
            if((!it.def.contains(v))&&(!it.in.contains(v))){
                updated=true;
                it.in.add(v);
            }
        });
    }

    public void visit(ASMBinary it) {
        tryAddUse(it, it.rs1);
        tryAddUse(it, it.rs2);
        tryAddDef(it, it.rd);
    }

    public void visit(ASMBranch it) {
        tryAddUse(it, it.cond);
    }

    public void visit(ASMCall it) {
    }

    public void visit(ASMJump it) {
    }

    public void visit(ASMLa it) {
        tryAddDef(it, it.rd);
    }

    public void visit(ASMLi it) {
        tryAddDef(it, it.rd);
    }

    public void visit(ASMLoad it) {
        tryAddDef(it, it.rd);
        tryAddUse(it, it.rs);
    }

    public void visit(ASMMv it) {
        tryAddUse(it, it.rs);
        tryAddDef(it, it.rd);
    }

    public void visit(ASMRet it) {
    }

    public void visit(ASMSet it) {
        tryAddUse(it, it.rs);
        tryAddDef(it, it.rd);
    }

    public void visit(ASMStore it) {
        tryAddUse(it, it.rd);//todo
        tryAddUse(it, it.rs);
    }
}
