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
define void @check(){
_b.1:
ret void

}
define i32 @main(){
call void @_initGlobal()
br label %_b.2
_b.2:
%_1 = alloca i32
%_2 = alloca ptr
%_3 = alloca i32
%_4 = call i32 @getInt()
store i32 %_4,ptr %_1
%_6 = add i32 1,1
%_7 = mul i32 %_6,4
%_8 = call ptr @_malloc(i32 %_7)
%_9 = getelementptr i32,ptr %_8,i32 0
store i32 1,ptr %_9
%_5 = getelementptr i32,ptr %_8,i32 1
%_10 = alloca i32
store i32 0,ptr %_10
br label %_b.3

_b.3:
%_11 = load i32,ptr %_10
%_12 = icmp slt i32 %_11,1
br i1 %_12,label %_b.5,label %_b.6

_b.4:
%_13 = load i32,ptr %_10
%_14 = add i32 %_13,1
store i32 %_14,ptr %_10
br label %_b.3

_b.7:
%_24 = load i32,ptr %_23
%_25 = icmp slt i32 %_24,%_18
br i1 %_25,label %_b.9,label %_b.10

_b.8:
%_26 = load i32,ptr %_23
%_27 = add i32 %_26,1
store i32 %_27,ptr %_23
br label %_b.7

_b.9:
%_28 = load i32,ptr %_23
%_29 = getelementptr i32,ptr %_17,i32 %_28
br label %_b.8

_b.10:
store ptr %_17,ptr %_16
br label %_b.4

_b.5:
%_15 = load i32,ptr %_10
%_16 = getelementptr ptr,ptr %_5,i32 %_15
%_18 = load i32,ptr %_1
%_19 = add i32 %_18,1
%_20 = mul i32 %_19,4
%_21 = call ptr @_malloc(i32 %_20)
%_22 = getelementptr i32,ptr %_21,i32 0
store i32 %_18,ptr %_22
%_17 = getelementptr i32,ptr %_21,i32 1
%_23 = alloca i32
store i32 0,ptr %_23
br label %_b.7

_b.6:
store ptr %_5,ptr %_2
%_30 = load ptr,ptr %_2
%_31 = getelementptr i32,ptr %_30,i32 0
%_32 = load ptr,ptr %_31
%_33 = getelementptr i32,ptr %_32,i32 24
%_34 = sub i32 0,1
store i32 %_34,ptr %_33
%_35 = call ptr @check()
ret i32 0

}
