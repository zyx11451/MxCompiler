package ASM;

import ASM.ASMBlock;
import ASM.ASMFunction;
import ASM.ASMModule;
import ASM.ASMVisitor;
import ASM.inst.*;

public class InsertSpOffsetInst implements ASMVisitor {
    ASMFunction nowFunc=null;
    ASMBlock nowBlock=null;
    public void visit(ASMModule it){
        it.function.forEach(f->f.accept(this));
    }
    public void visit(ASMFunction it){
        nowFunc=it;
        it.addAtBeginning();
        it.blocks.forEach(b->b.accept(this));
    }
    public void visit(ASMBlock it){
        nowBlock=it;
        for(ASMInst i=it.head;i!=null;i=i.next){
            i.accept(this);
        }
    }
    public void visit(ASMBinary it){}
    public void visit(ASMBranch it){}
    public void visit(ASMCall it){}
    public void visit(ASMJump it){}
    public void visit(ASMLa it){}
    public void visit(ASMLi it){}
    public void visit(ASMLoad it){}
    public void visit(ASMMv it){}
    public void visit(ASMRet it){
        nowFunc.addBeforeRet(nowBlock,it);
    }
    public void visit(ASMSet it){}
    public void visit(ASMStore it){}
}
