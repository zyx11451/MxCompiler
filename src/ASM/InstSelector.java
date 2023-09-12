package ASM;

import ASM.inst.*;
import ASM.operand.Imm;
import ASM.operand.PhyReg;
import ASM.operand.Register;
import ASM.operand.VirReg;
import IR.IRDefs.*;
import IR.IRTypes.IRSimpleType;
import IR.IRVisitor;
import IR.entity.*;
import IR.inst.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class InstSelector implements IRVisitor {
    public ASMModule asmModule;
    public ASMFunction nowDefiningFunc = null;
    public ASMBlock nowBuildingBlock = null;
    public HashMap<IRBlock, ASMBlock> blockMap = new HashMap<>();
    public HashMap<IREntity, Register> entMap = new HashMap<>();
    public int virRegNum = 0;
    public int phiBlkNum = 0;

    public VirReg getNewVirReg() {
        return new VirReg(virRegNum++);
    }

    public ASMBlock getNewPhiBlock() {
        phiBlkNum++;
        return new ASMBlock("phi" + phiBlkNum);
    }

    public Register getReg(IREntity e) {
        if (e instanceof IRConstantValue) {
            VirReg r = getNewVirReg();
            nowBuildingBlock.push_back(new ASMLi(r, new Imm(((IRConstantValue) e).value)));
            return r;
        } else if (e instanceof IRConstantBool) {
            VirReg r = getNewVirReg();
            if (((IRConstantBool) e).value) {
                nowBuildingBlock.push_back(new ASMLi(r, new Imm(1)));
            } else {
                nowBuildingBlock.push_back(new ASMLi(r, new Imm(0)));
            }
            return r;
        } else if (e instanceof IRNull) {
            VirReg r = getNewVirReg();
            nowBuildingBlock.push_back(new ASMLi(r, new Imm(0)));
            return r;
        } else if (((IRVariable) e).isGlobal()) {
            //全局变量
            VirReg r = getNewVirReg();
            nowBuildingBlock.push_back(new ASMLa(r, ((IRVariable) e).name));
            return r;
        } else {
            if (entMap.containsKey(e)) {
                return entMap.get(e);
            } else {
                VirReg r = getNewVirReg();
                entMap.put(e, r);
                return r;
            }
        }
    }

    public InstSelector(ASMModule asmModule) {
        this.asmModule = asmModule;
    }

    public void visit(IRRoot it) {
        it.definitions.forEach(def -> def.accept(this));
    }

    public void visit(IRClassDef it) {
    }

    public void visit(IRFunctionDef it) {
        nowDefiningFunc = new ASMFunction(it.functionName);
        blockMap = new HashMap<>();
        if (Objects.equals(it.functionName, "main")) {
            ASMBlock mainInit = new ASMBlock("mainInit");
            //插入main函数专属的初始化语句
            nowDefiningFunc.blocks.add(mainInit);
            nowBuildingBlock = null;
        }
        for (IRInst functionBlock : it.functionBody) {
            if (!(functionBlock instanceof IRBlock)) {
                continue;
            }
            ASMBlock asmBlock = new ASMBlock(((IRBlock) functionBlock).labelName);
            blockMap.put((IRBlock) functionBlock, asmBlock);
            nowDefiningFunc.blocks.add(asmBlock);
        }
        if (nowDefiningFunc.blocks.isEmpty()) {
            nowDefiningFunc.blocks.add(new ASMBlock(nowDefiningFunc.name));
        }
        nowBuildingBlock = nowDefiningFunc.blocks.get(0);//无法处理什么都没有的情况
        if (Objects.equals(it.functionName, "main")) {
            //插入main函数专属的初始化语句
            it.functionBody.get(0).accept(this);
            it.functionBody.get(1).accept(this);
        }

        if (it.parameters.size() <= 8) {
            for (int i = 0; i < it.parameters.size(); ++i) {
                VirReg r = getNewVirReg();
                entMap.put(it.parameters.get(i), r);
                nowBuildingBlock.push_back(new ASMMv(r, new PhyReg("a" + i)));
            }
        }
        if (it.parameters.size() > 8) {
            for (int i = 0; i < 8; ++i) {
                VirReg r = getNewVirReg();
                entMap.put(it.parameters.get(i), r);
                nowBuildingBlock.push_back(new ASMMv(r, new PhyReg("a" + i)));
            }
            for (int i = 8; i < it.parameters.size(); ++i) {
                VirReg r = getNewVirReg();
                entMap.put(it.parameters.get(i), r);
                nowBuildingBlock.push_back(new ASMLoad(r, new PhyReg("s0"), (i - 8) * 4));
            }
        }
        //收集alloca
        for (IRInst functionBlock : it.functionBody) {
            if (!(functionBlock instanceof IRBlock)) {
                continue;
            }
            for (IRInst statement : ((IRBlock) functionBlock).statements) {
                if (statement instanceof IRAlloca) {
                    statement.accept(this);
                }
            }
        }
        for (IRInst functionBlock : it.functionBody) {
            if (!(functionBlock instanceof IRBlock)) {
                continue;
            }
            functionBlock.accept(this);
        }
        //插入phi
        ArrayList<ASMBlock> phiBlocks = new ArrayList<>();
        for (ASMBlock asmBlock : nowDefiningFunc.blocks) {
            if (asmBlock.phi.isEmpty()) continue;
            asmBlock.phi.forEach((labelName, in) -> {
                //找到对应跳转语句,让它在跳转途中再经历一个块。
                ASMBlock phiTar = getNewPhiBlock();
                phiBlocks.add(phiTar);
                phiTar.push_back(in);
                phiTar.push_back(new ASMJump(labelName));
                for (ASMInst i = asmBlock.head; i != null; i = i.next) {
                    if (i instanceof ASMBranch) {
                        if (Objects.equals(((ASMBranch) i).label, labelName)) {
                            ((ASMBranch) i).label = phiTar.labelName;
                        }
                    }
                    if (i instanceof ASMJump) {
                        if (Objects.equals(((ASMJump) i).labelName, labelName)) {
                            ((ASMJump) i).labelName = phiTar.labelName;
                        }
                    }
                }
            });
        }
        nowDefiningFunc.blocks.addAll(phiBlocks);
        if (Objects.equals(it.functionName, "_initGlobal") || nowDefiningFunc.blocks.get(0).head == null) {
            nowDefiningFunc.blocks.get(nowDefiningFunc.blocks.size() - 1).push_back(new ASMRet());
        }
        nowDefiningFunc.blocks.get(0).labelName = nowDefiningFunc.name;
        asmModule.function.add(nowDefiningFunc);
    }

    public void visit(IRGlobalDef it) {
        asmModule.data.add(new ASMData(it.name, 0));//所有东西都被初始化为0
    }

    public void visit(IRStringConstantDef it) {
        asmModule.data.add(new ASMData(it.name, it.value));
    }

    public void visit(IRAlloca it) {
        //如果alloca在load、store后面就会出事，在函数开始前收集一下
        VirReg rd = getNewVirReg();
        entMap.put(it.result, rd);
        nowDefiningFunc.allocate(rd);
    }

    public void visit(IRBinary it) {
        Register rd = getReg(it.lhs);
        Register rs1 = getReg(it.operand1);
        Register rs2 = getReg(it.operand2);
        if (it.op == IRBinary.opType.add) {
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.add, rd, rs1, rs2));
        } else if (it.op == IRBinary.opType.sub) {
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.sub, rd, rs1, rs2));
        } else if (it.op == IRBinary.opType.mul) {
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.mul, rd, rs1, rs2));
        } else if (it.op == IRBinary.opType.sdiv) {
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.div, rd, rs1, rs2));
        } else if (it.op == IRBinary.opType.srem) {
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.rem, rd, rs1, rs2));
        } else if (it.op == IRBinary.opType.and) {
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.and, rd, rs1, rs2));
        } else if (it.op == IRBinary.opType.or) {
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.or, rd, rs1, rs2));
        } else if (it.op == IRBinary.opType.xor) {
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.xor, rd, rs1, rs2));
        } else if (it.op == IRBinary.opType.shl) {
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.sll, rd, rs1, rs2));
        } else if (it.op == IRBinary.opType.ashr) {
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.sra, rd, rs1, rs2));
        }
    }

    public void visit(IRBlock it) {
        nowBuildingBlock = blockMap.get(it);
        it.statements.forEach(s -> {
            if (!(s instanceof IRAlloca)) s.accept(this);
        });
    }

    public void visit(IRCall it) {
        if (it.parameters.size() <= 8) {
            for (int i = 0; i < it.parameters.size(); ++i) {
                nowBuildingBlock.push_back(new ASMMv(new PhyReg("a" + i), getReg(it.parameters.get(i))));
            }
        } else {
            for (int i = 0; i < 8; ++i) {
                nowBuildingBlock.push_back(new ASMMv(new PhyReg("a" + i), getReg(it.parameters.get(i))));
            }
            int parameterOffset = 0;
            for (int i = 8; i < it.parameters.size(); ++i) {
                nowBuildingBlock.push_back(new ASMStore(getReg(it.parameters.get(i)), new PhyReg("sp"), parameterOffset));
                parameterOffset += 4;
            }
            nowDefiningFunc.paraOffset = Integer.max(parameterOffset, nowDefiningFunc.paraOffset);

        }
        nowBuildingBlock.push_back(new ASMCall(it.functionName));
        if (it.result != null) {
            VirReg r = getNewVirReg();
            entMap.put(it.result, r);
            nowBuildingBlock.push_back(new ASMMv(r, new PhyReg("a0")));
        }

    }

    public void visit(IRCompare it) {
        Register rd = getReg(it.lhs);
        Register rs1 = getReg(it.operand1);
        Register rs2 = getReg(it.operand2);
        if (it.op == IRCompare.condType.eq) {
            VirReg tmp = getNewVirReg();
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.xor, tmp, rs1, rs2));
            nowBuildingBlock.push_back(new ASMSet(ASMSet.opType.seqz, rd, tmp));
        } else if (it.op == IRCompare.condType.ne) {
            VirReg tmp = getNewVirReg();
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.xor, tmp, rs1, rs2));
            nowBuildingBlock.push_back(new ASMSet(ASMSet.opType.snez, rd, tmp));
        } else if (it.op == IRCompare.condType.slt) {
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.slt, rd, rs1, rs2));
        } else if (it.op == IRCompare.condType.sgt) {
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.slt, rd, rs2, rs1));
        } else if (it.op == IRCompare.condType.sge) {
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.slt, rd, rs1, rs2));
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.xori, rd, rd, new Imm(1)));
        } else if (it.op == IRCompare.condType.sle) {
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.slt, rd, rs2, rs1));
            nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.xori, rd, rd, new Imm(1)));
        }
    }

    public void visit(IRConditionalBr it) {
        nowBuildingBlock.push_back(new ASMBranch(ASMBranch.opType.beqz, getReg(it.cond), blockMap.get(it.ifFalse).labelName));
        nowBuildingBlock.push_back(new ASMJump(blockMap.get(it.ifTrue).labelName));
    }

    public void visit(IRGetElementPtr it) {
        //取数组元素时后面跟着一个，取类成员时后面跟着两个且第一个必为0
        Register rd = getReg(it.result);
        Register i;
        if (it.idx.size() == 1) {
            i = getReg(it.idx.get(0));
        } else {
            i = getReg(it.idx.get(1));
        }
        VirReg tmp = getNewVirReg();
        nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.mul, tmp, i, getReg(new IRConstantValue(4, new IRSimpleType(32)))));
        nowBuildingBlock.push_back(new ASMBinary(ASMBinary.opType.add, rd, getReg(it.ptrVal), tmp));
    }

    public void visit(IRLoad it) {
        Register rd = getReg(it.result);
        Register rs = getReg(it.pointer);
        if (it.pointer.isGlobal()) {
            nowBuildingBlock.push_back(new ASMLoad(rd, rs, 0));
            return;
        }
        if (nowDefiningFunc.stackOffset.containsKey(rs)) {
            nowBuildingBlock.push_back(new ASMMv(rd, rs));
            return;
        }
        nowBuildingBlock.push_back(new ASMLoad(rd, rs, 0));
    }

    public void visit(IRPhi it) {
        VirReg tmp = getNewVirReg();
        nowBuildingBlock.push_back(new ASMMv(getReg(it.result), tmp));
        it.valLabel.forEach((blk, ent) -> {
            if (ent instanceof IRVariable) {
                blockMap.get(blk).phi.put(nowBuildingBlock.labelName, new ASMMv(tmp, getReg(ent)));
            } else if (ent instanceof IRConstantValue) {
                blockMap.get(blk).phi.put(nowBuildingBlock.labelName, new ASMLi(tmp, new Imm(((IRConstantValue) ent).value)));
            } else if (ent instanceof IRConstantBool) {
                if (((IRConstantBool) ent).value) {
                    blockMap.get(blk).phi.put(nowBuildingBlock.labelName, new ASMLi(tmp, new Imm(1)));
                } else {
                    blockMap.get(blk).phi.put(nowBuildingBlock.labelName, new ASMLi(tmp, new Imm(0)));
                }
            } else if (ent instanceof IRNull) {
                blockMap.get(blk).phi.put(nowBuildingBlock.labelName, new ASMLi(tmp, new Imm(0)));
            }
        });
    }

    public void visit(IRRet it) {
        if (!(it.returnValue instanceof IRVoidEntity)) {
            nowBuildingBlock.push_back(new ASMMv(new PhyReg("a0"), getReg(it.returnValue)));
        }
        nowBuildingBlock.push_back(new ASMRet());
    }

    public void visit(IRStore it) {
        Register rd = getReg(it.target);
        Register rs = getReg(it.value);
        if (it.target.isGlobal()) {
            nowBuildingBlock.push_back(new ASMStore(rs, rd, 0));
            return;
        }
        if (nowDefiningFunc.stackOffset.containsKey(rd)) {
            nowBuildingBlock.push_back(new ASMMv(rd, rs));
            return;
        }
        nowBuildingBlock.push_back(new ASMStore(rs, rd, 0));
    }

    public void visit(IRUnconditionalBr it) {
        nowBuildingBlock.push_back(new ASMJump(blockMap.get(it.dest).labelName));
    }
}
