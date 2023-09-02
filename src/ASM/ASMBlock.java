package ASM;

import ASM.inst.ASMInst;

import java.util.HashMap;

public class ASMBlock {
    public ASMInst head;
    public ASMInst tail;
    public String labelName;
    public HashMap<String,ASMInst> phi;
    public ASMBlock(String labelName){
        this.labelName=labelName;
        this.head=null;
        this.tail=null;
        this.phi=new HashMap<>();
    }
    public void push_back(ASMInst tar){
        if(head==null){
            head=tar;
            tail=tar;
            return;
        }
        tail.next=tar;
        tar.prev=tail;
        tail=tar;
    }
    public void push_front(ASMInst tar){
        if(head==null){
            head=tar;
            tail=tar;
            return;
        }
        head.prev=tar;
        tar.next=head;
        head=tar;
    }
    public void insert_before(ASMInst loc,ASMInst tar){
        //在前方插入
        if(loc.prev==null){
            push_front(tar);
            return;
        }
        loc.prev.next=tar;
        tar.prev=loc.prev;
        tar.next=loc;
        loc.prev=tar;
    }
    public void insert_after(ASMInst loc,ASMInst tar){
        if(loc.next==null){
            push_back(tar);
            return;
        }
        loc.next.prev=tar;
        tar.next=loc.next;
        tar.prev=loc;
        loc.next=tar;
    }
    public String toString(){
        StringBuilder ans=new StringBuilder();
        if(labelName!=null) ans.append(labelName);
        ans.append(":\n   ");
        for(ASMInst i=head;i!=null;i=i.next){
            ans.append(i);
            ans.append("\n   ");
        }
        return ans.toString();
    }
    public void accept(ASMVisitor visitor){
        visitor.visit(this);
    }
}
