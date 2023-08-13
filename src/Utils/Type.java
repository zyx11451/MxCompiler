package Utils;

import Parser.MxParser;
import Utils.error.SemanticError;

import java.util.HashMap;
import java.util.Objects;

public class Type {
    public String type_name;
    public int dimension;
    public boolean can_be_right;
    public boolean isClassDef;
    //有关类的内置函数、成员之类的一律去globalScope查找，定义变量时此值为false，只保留名字、维数等基本信息。（SemanticCheck时的想法，之后可能会改）
    public HashMap<String, Type> members = null;
    public HashMap<String, Function> functions = null;

    public Type(String s) {
        this.type_name = s;
        this.dimension = 0;
        this.can_be_right = true;
    }

    public Type(String s, int d) {
        this.type_name = s;
        this.dimension = d;
        this.can_be_right = true;
    }

    public void becomeClassDef() {
        isClassDef = true;
        members = new HashMap<>();
        functions = new HashMap<>();
    }

    public void addMember(String memberName, Type memberType) {
        if (members.containsKey(memberName)) throw new SemanticError("Multiple definition in class " + type_name, null);
        members.put(memberName, memberType);
    }

    public void addFunction(String functionName, Function function) {
        if (functions.containsKey(functionName))
            throw new SemanticError("Multiple function declaration in class " + type_name, null);
        functions.put(functionName, function);
    }

    public Type(MxParser.TypeContext ty) {
        String typeName = null;

        if (ty.Void() != null) {
            typeName = ty.Void().toString();
        } else if (ty.Bool() != null) {
            typeName = ty.Bool().toString();
        } else if (ty.Int() != null) {
            typeName = ty.Int().toString();
        } else if (ty.String() != null) {
            typeName = ty.String().toString();
        } else if (ty.Identifier() != null) {
            typeName = ty.Identifier().toString();
        }
        if (ty.LeftBracket()== null) {
            this.type_name = typeName;
            this.dimension = 0;
            this.can_be_right = true;
        } else {
            this.type_name = typeName;
            this.dimension = ty.LeftBracket().size();
            this.can_be_right = true;
        }
    }

    public boolean isInt() {
        return Objects.equals(this.type_name, "int")&&this.dimension==0;
    }

    public boolean isString() {
        return Objects.equals(this.type_name, "string")&&this.dimension==0;
    }

    public boolean isVoid() {
        return Objects.equals(this.type_name, "void")&&this.dimension==0;
    }

    public boolean isBool() {
        return Objects.equals(this.type_name, "bool")&&this.dimension==0;
    }

    public boolean equals(Type other) {
        return (Objects.equals(type_name, other.type_name)) && (dimension == other.dimension);
    }
}
