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
%_2 = mul i32 1,4
%_1 = alloca %struct._array
%_3 = getelementptr %struct._array,ptr %_1,i32 0,i32 1
%_4 = call ptr @_malloc(i32 %_2)
store ptr %_4,ptr %_3
%_5 = getelementptr %struct._array,ptr %_1,i32 0,i32 0
store i32 134,ptr %_5
%_6 = alloca i32
store i32 0,ptr %_6
br label %_b.2

_b.2:
%_7 = load i32,ptr %_6
%_8 = icmp slt i32 %_7,114
br i1 %_8,label %_b.4,label %_b.5

_b.3:
%_9 = load i32,ptr %_6
%_10 = add i32 %_9,1
store i32 %_10,ptr %_6
br label %_b.2

_b.4:
%_11 = load i32,ptr %_6
%_13 = mul i32 %_11,4
%_12 = call ptr @_arrayElement(ptr %_1,i32 %_13)
br label %_b.3

_b.5:
store ptr %_1,ptr @_2

ret void
}



define i32 @main(){
call void @_initGlobal()
br label %_b.6
_b.6:
%_14 = load ptr,ptr @_2
%_16 = mul i32 0,4
%_17 = getelementptr %struct._array,ptr %_14,i32 0,i32 1
%_18 = load ptr,ptr %_17
%_15 = getelementptr i8,ptr %_18,i32 %_16
%_19 = add i32 1,0
store i32 %_19,ptr %_15
%_20 = load ptr,ptr @_2
%_21 = call i32 @_arraySize(ptr %_20)
%_22 = call ptr @printlnInt(i32 %_21)
%_23 = load ptr,ptr @_2
%_25 = mul i32 0,4
%_26 = getelementptr %struct._array,ptr %_14,i32 0,i32 1
%_27 = load ptr,ptr %_17
%_24 = getelementptr i32,ptr %_27,i32 0
%_28 = load i32,ptr %_24
%_29 = call ptr @printInt(i32 %_28)
ret i32 0

ret i32 0
}
