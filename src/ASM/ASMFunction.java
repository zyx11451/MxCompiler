package ASM;

import ASM.inst.*;
import ASM.operand.Imm;
import ASM.operand.PhyReg;
import ASM.operand.Register;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ASMFunction {
    public String name;
    public ArrayList<ASMBlock> blocks;
    public HashMap<String,ASMBlock> blockNameMap=new HashMap<>();
    public HashMap<Register,Integer> stackOffset;
    //todo 写寄存器分配时要把它改成52
    public int offset=52;//s0,ra,s1,s2,s3,s4,s5,s6,s7,s8,s9,s10,s11
    public int paraOffset=0;
    public ASMFunction(String name){
        this.name=name;
        blocks=new ArrayList<>();
        stackOffset=new HashMap<>();
    }
    public void allocate(Register r){
        stackOffset.put(r,offset+=4);
    }
    public void addBeforeRet(ASMBlock retBlock, ASMInst retInst){
        //在每个ret的前方插入对sp的调整等
        int totalOffset=(((offset+paraOffset)>>4)+1)<<4;
        if(totalOffset>2048){
            retBlock.insert_before(retInst,new ASMLi(new PhyReg("t0"),new Imm(totalOffset)));
            retBlock.insert_before(retInst,new ASMBinary(ASMBinary.opType.add,new PhyReg("t1"),new PhyReg("sp"),new PhyReg("t0")));
            retBlock.insert_before(retInst,new ASMLoad(new PhyReg("s0"),new PhyReg("t1"),-8));

            for(int i=1;i<=11;++i){
                retBlock.insert_before(retInst,new ASMLoad(new PhyReg("s"+i),new PhyReg("t1"),-8-4*i));
            }

            retBlock.insert_before(retInst,new ASMLoad(new PhyReg("ra"),new PhyReg("t1"),-4));
            retBlock.insert_before(retInst,new ASMBinary(ASMBinary.opType.add,new PhyReg("sp"),new PhyReg("sp"),new PhyReg("t0")));
        }else{
            retBlock.insert_before(retInst,new ASMLoad(new PhyReg("s0"),new PhyReg("sp"),totalOffset-8));

            for(int i=1;i<=11;++i){
                retBlock.insert_before(retInst,new ASMLoad(new PhyReg("s"+i),new PhyReg("sp"),totalOffset-8-4*i));
            }

            retBlock.insert_before(retInst,new ASMLoad(new PhyReg("ra"),new PhyReg("sp"),totalOffset-4));
            retBlock.insert_before(retInst,new ASMBinary(ASMBinary.opType.addi,new PhyReg("sp"),new PhyReg("sp"),new Imm(totalOffset)));
        }

    }
    public void addAtBeginning(){
        int totalOffset=(((offset+paraOffset)>>4)+1)<<4;
        ASMBlock firstBlock=blocks.get(0);
        if(totalOffset>2048){
            firstBlock.push_front(new ASMBinary(ASMBinary.opType.add,new PhyReg("s0"),new PhyReg("sp"),new PhyReg("t0")));
            firstBlock.push_front(new ASMStore(new PhyReg("s0"),new PhyReg("t1"),-8));
            //opt
            for(int i=1;i<=11;++i){
                firstBlock.push_front(new ASMStore(new PhyReg("s"+i),new PhyReg("t1"),-8-4*i));
            }

            firstBlock.push_front(new ASMStore(new PhyReg("ra"),new PhyReg("t1"),-4));
            firstBlock.push_front(new ASMBinary(ASMBinary.opType.add,new PhyReg("t1"),new PhyReg("sp"),new PhyReg("t0")));
            firstBlock.push_front(new ASMBinary(ASMBinary.opType.sub,new PhyReg("sp"),new PhyReg("sp"),new PhyReg("t0")));
            firstBlock.push_front(new ASMLi(new PhyReg("t0"),new Imm(totalOffset)));
        }else{
            firstBlock.push_front(new ASMBinary(ASMBinary.opType.addi,new PhyReg("s0"),new PhyReg("sp"),new Imm(totalOffset)));
            firstBlock.push_front(new ASMStore(new PhyReg("s0"),new PhyReg("sp"),totalOffset-8));
            //opt
            for(int i=1;i<=11;++i){
                firstBlock.push_front(new ASMStore(new PhyReg("s"+i),new PhyReg("sp"),totalOffset-8-4*i));
            }

            firstBlock.push_front(new ASMStore(new PhyReg("ra"),new PhyReg("sp"),totalOffset-4));
            firstBlock.push_front(new ASMBinary(ASMBinary.opType.addi,new PhyReg("sp"),new PhyReg("sp"),new Imm(-totalOffset)));
        }
    }
    public String toString(){
        StringBuilder ans=new StringBuilder();
        ans.append(" .globl ").append(name).append("\n");
        blocks.forEach(b->ans.append(b.toString()).append("\n"));
        return ans.toString();
    }
    public void accept(ASMVisitor visitor){
        visitor.visit(this);
    }
    public HashSet<ASMBlock> endBlocks=new HashSet<>();
}
