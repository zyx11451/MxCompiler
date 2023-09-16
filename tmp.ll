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


define void @_initGlobal(){
_b.1:

ret void
}

define i32 @test(){
_b.2:
%_1 = alloca i32
%_2 = alloca i32
%_3 = load i32,ptr @_1
store i32 %_3,ptr %_2
%_4 = alloca i32
store i32 0,ptr %_1
br label %_b.3

_b.3:
%_5 = load i32,ptr %_1
%_6 = load i32,ptr %_2
%_7 = icmp slt i32 %_5,%_6
br i1 %_7,label %_b.4,label %_b.6

_b.5:
%_8 = load i32,ptr %_1
%_9 = add i32 %_8,1
store i32 %_9,ptr %_1
br label %_b.3

_b.7:
%_456 = load i32,ptr %_59
store i32 %_456,ptr %_4
br label %_b.8

_b.8:
br label %_b.5

_b.4:
%_59 = alloca i32
%_60 = load i32,ptr %_1
%_61 = add i32 %_60,1
store i32 %_61,ptr %_59
%_455 = icmp ne i32 %_61,0
br i1 %_455,label %_b.7,label %_b.8

_b.6:
%_457 = load i32,ptr %_4
ret i32 %_457

}
define i32 @main(){
call void @_initGlobal()
br label %_b.9
_b.9:
%_459 = call i32 @test()
store i32 200,ptr @_1
%_461 = call ptr @printInt(i32 %_459)
%_462 = call i32 @test()
%_464 = add i32 %_459,%_462
%_465 = sub i32 %_464,300
ret i32 %_465

}
