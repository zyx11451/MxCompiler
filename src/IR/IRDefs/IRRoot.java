package IR.IRDefs;

import IR.IRVisitor;

import java.util.ArrayList;

public class IRRoot {
    //根节点
    public ArrayList<IRDef> definitions;
    public IRRoot(){
        definitions=new ArrayList<>();
    }

    public String toString() {
        StringBuilder ans=new StringBuilder();
        ans.append("%struct._array = type { i32, ptr }\n" +
                "declare dso_local i32 @_arraySize(ptr nocapture noundef readonly %0)\n" +
                "declare dso_local ptr @_arrayElement(ptr nocapture noundef readonly %0, i32 noundef %1)\n" +
                "declare dso_local void @print(ptr noundef %0)\n" +
                "declare dso_local void @println(ptr noundef %0)\n" +
                "declare dso_local void @printInt(i32 noundef %0)\n" +
                "declare dso_local void @printlnInt(i32 noundef %0)\n" +
                "declare dso_local noalias ptr @_malloc(i32 noundef %0)\n" +
                "declare dso_local ptr @getString()\n" +
                "declare dso_local i32 @getInt()\n" +
                "declare dso_local noalias ptr @toString(i32 noundef %0)\n" +
                "declare dso_local ptr @_string.plus(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)\n" +
                "declare dso_local zeroext i1 @_string.eq(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)\n" +
                "declare dso_local zeroext i1 @_string.ne(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)\n" +
                "declare dso_local zeroext i1 @_string.l(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)\n" +
                "declare dso_local zeroext i1 @_string.g(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)\n" +
                "declare dso_local zeroext i1 @_string.le(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)\n" +
                "declare dso_local zeroext i1 @_string.ge(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)\n" +
                "declare dso_local i32 @_string.length(ptr nocapture noundef readonly %0)\n" +
                "declare dso_local ptr @_string.substring(ptr noundef %0, i32 noundef %1, i32 noundef %2)\n" +
                "declare dso_local i32 @_string.parseInt(ptr nocapture noundef readonly %0)\n" +
                "declare dso_local i32 @_string.ord(ptr nocapture noundef readonly %0, i32 noundef %1)\n" +
                "\n");
        for(IRDef definition:definitions){
            if(definition instanceof IRGlobalDef) ans.append(definition.toString());
            ans.append("\n");
        }
        for(IRDef definition:definitions){
            if(!(definition instanceof IRGlobalDef)) ans.append(definition.toString());
            ans.append("\n");
        }
        return ans.toString();
    }
    public void accept(IRVisitor visitor){
        visitor.visit(this);
    }
}
