package IR.IRTypes;

import java.util.ArrayList;

public class IRBasicClassType extends IRType {
    //注意：保留原来的名字。如果判断类型（需要吗）出错可能是这里的问题
    //实际上的自定义类型量是指向该类的指针（也就是说变量有两层指针）（为了方便函数引用传参）
    public String className;
    public ArrayList<IRType> classElements;

    public IRBasicClassType(String className) {
        this.className = className;
        classElements = new ArrayList<>();
    }

    public void addElements(IRType e) {
        classElements.add(e);
    }

    public int size() {
        return 0;
    }

    public String toString() {
        if(className.equals("struct._array")) return "%struct._array";
        return "%class." + className;
    }
}
