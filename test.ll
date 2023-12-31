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


@_1 = global ptr null


define void @_initGlobal(){
_b.1:
%_4 = call ptr @_malloc(i32 20)
%_5 = getelementptr i32,ptr %_4,i32 0
store i32 4,ptr %_5
%_1 = getelementptr i32,ptr %_4,i32 1
%_6 = alloca i32
store i32 0,ptr %_6
br label %_b.2

_b.2:
%_7 = load i32,ptr %_6
%_8 = icmp slt i32 %_7,4
br i1 %_8,label %_b.4,label %_b.5

_b.3:
%_9 = load i32,ptr %_6
%_10 = add i32 %_9,1
store i32 %_10,ptr %_6
br label %_b.2

_b.4:
%_11 = load i32,ptr %_6
%_12 = getelementptr i32,ptr %_1,i32 %_11
br label %_b.3

_b.5:
store ptr %_1,ptr @_1

ret void
}

@.str1 = private unnamed_addr constant [1 x i8] c" "
define i32 @main(){
call void @_initGlobal()
br label %_b.6
_b.6:
%_15 = load ptr,ptr @_1
%_16 = getelementptr i32,ptr %_15,i32 -1
%_17 = load i32,ptr %_16
%_18 = add i32 %_17,1
%_19 = mul i32 %_18,4
%_20 = call ptr @_malloc(i32 %_19)
%_21 = getelementptr i32,ptr %_20,i32 0
store i32 %_17,ptr %_21
%_14 = getelementptr i32,ptr %_20,i32 1
%_22 = alloca i32
store i32 0,ptr %_22
br label %_b.7

_b.7:
%_23 = load i32,ptr %_22
%_24 = icmp slt i32 %_23,%_17
br i1 %_24,label %_b.9,label %_b.10

_b.8:
%_25 = load i32,ptr %_22
%_26 = add i32 %_25,1
store i32 %_26,ptr %_22
br label %_b.7

_b.9:
%_27 = load i32,ptr %_22
%_28 = getelementptr i32,ptr %_14,i32 %_27
br label %_b.8

_b.10:
%_29 = alloca i32
%_30 = load ptr,ptr @_1
%_31 = getelementptr i32,ptr %_30,i32 0
store i32 0,ptr %_31
%_33 = getelementptr i32,ptr %_14,i32 0
%_34 = call i32 @getInt()
store i32 %_34,ptr %_33
%_35 = load ptr,ptr @_1
%_36 = getelementptr i32,ptr %_35,i32 0
%_37 = load i32,ptr %_36
%_38 = call ptr @toString(i32 %_37)
%_39 = call ptr @print(ptr %_38)
%_40 = call ptr @println(ptr @.str1)
store ptr %_14,ptr @_1
%_43 = getelementptr i32,ptr %_14,i32 0
%_44 = load i32,ptr %_43
%_45 = call ptr @toString(i32 %_44)
%_46 = call ptr @print(ptr %_45)
ret i32 0

}
