%struct._array = type { i32, ptr }
declare dso_local i32 @_arraySize(ptr nocapture noundef readonly %0)
declare dso_local ptr @_arrayElement(ptr nocapture noundef readonly %0, i32 noundef %1)
declare dso_local void @print(ptr noundef %0)
declare dso_local void @println(ptr noundef %0)
declare dso_local void @printInt(i32 noundef %0)
declare dso_local void @printlnInt(i32 noundef %0)
declare dso_local noalias ptr @_malloc(i32 noundef %0)
declare dso_local ptr @getString()
declare dso_local i32 @getInt()
declare dso_local noalias ptr @toString(i32 noundef %0)
declare dso_local ptr @_string.plus(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)
declare dso_local zeroext i1 @_string.eq(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)
declare dso_local zeroext i1 @_string.ne(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)
declare dso_local zeroext i1 @_string.l(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)
declare dso_local zeroext i1 @_string.g(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)
declare dso_local zeroext i1 @_string.le(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)
declare dso_local zeroext i1 @_string.ge(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)
declare dso_local i32 @_string.length(ptr nocapture noundef readonly %0)
declare dso_local ptr @_string.substring(ptr noundef %0, i32 noundef %1, i32 noundef %2)
declare dso_local i32 @_string.parseInt(ptr nocapture noundef readonly %0)
declare dso_local i32 @_string.ord(ptr nocapture noundef readonly %0, i32 noundef %1)



@_1 = global i32 0
@_2 = global ptr null
@_3 = global i32 0

define void @_initGlobal(){
_b.1:
%_2 = mul i32 20,4
%_1 = alloca %struct._array
%_3 = getelementptr %struct._array,ptr %_1,i32 0,i32 1
%_4 = call ptr @_malloc(i32 %_2)
store ptr %_4,ptr %_3
%_5 = getelementptr %struct._array,ptr %_1,i32 0,i32 0
%_6 = load i32,ptr %_5
%_7 = alloca i32
store i32 0,ptr %_7
br label %_b.2

_b.2:
%_8 = load i32,ptr %_7
%_9 = icmp slt i32 %_8,%_6
br i1 %_9,label %_b.4,label %_b.5

_b.3:
%_10 = load i32,ptr %_7
%_11 = add i32 %_10,1
store i32 %_11,ptr %_7
br label %_b.2

_b.4:
%_12 = load i32,ptr %_7
%_14 = mul i32 %_12,4
%_13 = call ptr @_arrayElement(ptr %_1,i32 %_14)
br label %_b.2

_b.5:
store ptr %_1,ptr @_2

ret void
}


define i32 @jud( i32 %_15){
_b.6:
%_16 = alloca i32
store i32 %_15,ptr %_16
%_17 = alloca i32
%_18 = alloca i32
store i32 0,ptr %_17
br label %_b.7

_b.7:
%_19 = load i32,ptr @_1
%_20 = load i32,ptr %_16
%_21 = sdiv i32 %_19,%_20
%_22 = load i32,ptr %_17
%_23 = icmp slt i32 %_22,%_21
br i1 %_23,label %_b.8,label %_b.10

_b.9:
%_24 = load i32,ptr %_17
%_25 = add i32 %_24,1
store i32 %_25,ptr %_17
br label %_b.7

_b.11:
%_27 = load i32,ptr %_16
%_28 = sub i32 %_27,1
%_29 = load i32,ptr %_18
%_30 = icmp slt i32 %_29,%_28
br i1 %_30,label %_b.12,label %_b.14

_b.13:
%_31 = load i32,ptr %_18
%_32 = add i32 %_31,1
store i32 %_32,ptr %_18
br label %_b.11

_b.15:
store i1 true,ptr %_26
br label %_b.16

_b.16:
br label %_b.13

_b.12:
%_33 = load i32,ptr %_17
%_34 = load i32,ptr %_16
%_35 = mul i32 %_33,%_34
%_36 = load i32,ptr %_18
%_37 = add i32 %_35,%_36
%_38 = load ptr,ptr @_2
%_40 = mul i32 %_37,4
%_39 = call ptr @_arrayElement(ptr %_38,i32 %_40)
%_41 = load i32,ptr %_17
%_42 = load i32,ptr %_16
%_43 = mul i32 %_41,%_42
%_44 = load i32,ptr %_18
%_45 = add i32 %_43,%_44
%_46 = add i32 %_45,1
%_47 = load ptr,ptr @_2
%_49 = mul i32 %_46,4
%_48 = call ptr @_arrayElement(ptr %_47,i32 %_49)
%_50 = load i32,ptr %_39
%_51 = load i32,ptr %_48
%_52 = icmp sgt i32 %_50,%_51
br i1 %_52,label %_b.15,label %_b.16

_b.14:
%_53 = load i1,ptr %_26
%_54 = xor i1 %_53,true
br i1 %_54,label %_b.17,label %_b.18

_b.17:
ret i32 1
br label %_b.18

_b.18:
br label %_b.9

_b.8:
%_26 = alloca i1
store i1 false,ptr %_26
store i32 0,ptr %_18
br label %_b.11

_b.10:
ret i32 0

}

define i32 @main(){
call void @_initGlobal()
br label %_b.19
_b.19:
%_55 = call i32 @getInt()
store i32 %_55,ptr @_1
store i32 0,ptr @_3
br label %_b.20

_b.20:
%_56 = load i32,ptr @_3
%_57 = load i32,ptr @_1
%_58 = icmp slt i32 %_56,%_57
br i1 %_58,label %_b.21,label %_b.23

_b.22:
%_59 = load i32,ptr @_3
%_60 = add i32 %_59,1
store i32 %_60,ptr @_3
br label %_b.20

_b.21:
%_61 = load ptr,ptr @_2
%_62 = load i32,ptr @_3
%_64 = mul i32 %_62,4
%_63 = call ptr @_arrayElement(ptr %_61,i32 %_64)
%_65 = call i32 @getInt()
store i32 %_65,ptr %_63
br label %_b.22

_b.23:
%_66 = load i32,ptr @_1
store i32 %_66,ptr @_3
br label %_b.24

_b.24:
%_67 = load i32,ptr @_3
%_68 = icmp sgt i32 %_67,0
br i1 %_68,label %_b.25,label %_b.27

_b.26:
%_69 = load i32,ptr @_3
%_70 = sdiv i32 %_69,2
store i32 %_70,ptr @_3
br label %_b.24

_b.28:
%_74 = load i32,ptr @_3
%_75 = call ptr @toString(i32 %_74)
%_76 = call ptr @print(ptr %_75)
ret i32 0
br label %_b.29

_b.29:
br label %_b.26

_b.25:
%_71 = load i32,ptr @_3
%_72 = call i32 @jud(i32 %_71)
%_73 = icmp sgt i32 %_72,0
br i1 %_73,label %_b.28,label %_b.29

_b.27:
ret i32 0

}
