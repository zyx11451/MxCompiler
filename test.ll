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



define void @_initGlobal(){
ret void
}
define i32 @main(){
call void @_initGlobal()
br label %_b.1
_b.1:
%_1 = alloca i32
%_2 = call i32 @getInt()
store i32 %_2,ptr %_1
%_3 = alloca i32
%_4 = call i32 @getInt()
store i32 %_4,ptr %_3
%_5 = alloca i32
%_6 = call i32 @getInt()
store i32 %_6,ptr %_5
%_9 = add i32 %_2,1
store i32 %_9,ptr %_1
%_11 = icmp eq i32 %_2,%_4
br i1 %_11,label %_b.2,label %_b.3

_b.5:
%_16 = load i32,ptr %_5
%_17 = add i32 %_16,1
store i32 %_17,ptr %_5
br label %_b.7

_b.6:
%_19 = load i32,ptr %_1
%_20 = add i32 %_19,1
store i32 %_20,ptr %_1
br label %_b.7

_b.7:
%_22 = phi i32[ %_20,%_b.6],[ %_17,%_b.5]
br label %_b.4

_b.2:
%_12 = load i32,ptr %_1
%_13 = add i32 %_12,1
store i32 %_13,ptr %_1
%_14 = load i32,ptr %_3
%_15 = icmp eq i32 %_12,%_14
br i1 %_15,label %_b.5,label %_b.6

_b.11:
%_33 = load i32,ptr %_3
%_34 = add i32 %_33,1
store i32 %_34,ptr %_3
br label %_b.13

_b.12:
%_36 = load i32,ptr %_5
%_37 = add i32 %_36,1
store i32 %_37,ptr %_5
br label %_b.13

_b.13:
%_39 = phi i32[ %_34,%_b.11],[ %_37,%_b.12]
br label %_b.10

_b.8:
%_27 = load i32,ptr %_3
%_28 = add i32 %_27,1
store i32 %_28,ptr %_3
%_29 = load i32,ptr %_5
%_30 = add i32 %_29,1
store i32 %_30,ptr %_5
%_32 = icmp eq i32 %_28,%_29
br i1 %_32,label %_b.11,label %_b.12

_b.9:
%_40 = load i32,ptr %_1
%_41 = add i32 %_40,1
store i32 %_41,ptr %_1
br label %_b.10

_b.10:
%_43 = phi i32[ %_39,%_b.13],[ %_41,%_b.9]
br label %_b.4

_b.3:
%_23 = load i32,ptr %_1
%_24 = add i32 %_23,1
store i32 %_24,ptr %_1
%_25 = load i32,ptr %_3
%_26 = icmp eq i32 %_23,%_25
br i1 %_26,label %_b.8,label %_b.9

_b.4:
%_44 = phi i32[ %_43,%_b.10],[ %_22,%_b.7]
%_45 = load i32,ptr %_1
%_46 = call ptr @printlnInt(i32 %_45)
%_47 = load i32,ptr %_3
%_48 = call ptr @printlnInt(i32 %_47)
%_49 = load i32,ptr %_5
%_50 = call ptr @printlnInt(i32 %_49)
%_52 = call ptr @printlnInt(i32 %_44)
ret i32 0

}
