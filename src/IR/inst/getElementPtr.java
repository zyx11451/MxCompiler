package IR.inst;

import IR.IRTypes.IRType;
import IR.entity.entity;
import IR.entity.variable;

import java.util.ArrayList;

public class getElementPtr extends inst{
    public IRType type;//ptrVal指向的类型
    public variable ptrVal;//必须为指针类型
    public variable result;//也是指针
    public ArrayList<entity> idx;
    public getElementPtr(IRType type,variable ptrVal,variable result){
        this.type=type;
        this.ptrVal=ptrVal;
        this.result=result;
        idx=new ArrayList<>();
    }
    public void addIdx(entity e){
        idx.add(e);
    }
    public String toString(){
        return null;
    }
}