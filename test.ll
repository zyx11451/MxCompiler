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
define i32 @test(){
_b.1:
%_1 = alloca i32
%_2 = alloca i32
store i32 0,ptr %_2
store i32 0,ptr %_1
br label %_b.2

_b.2:
%_3 = load i32,ptr %_1
%_4 = icmp slt i32 %_3,200
br i1 %_4,label %_b.3,label %_b.5

_b.4:
%_5 = load i32,ptr %_1
%_6 = add i32 %_5,1
store i32 %_6,ptr %_1
br label %_b.2

_b.6:
%_13 = icmp eq i32 1,1
br label %_b.7

_b.7:
%_8 = phi i1[ %_13,%_b.6],[ %_10,%_b.3]
br i1 %_8,label %_b.8,label %_b.9

_b.8:
%_116 = icmp eq i32 5050,5050
br label %_b.9

_b.9:
%_7 = phi i1[ %_116,%_b.8],[ %_8,%_b.7]
br i1 %_7,label %_b.10,label %_b.11

_b.10:
%_117 = load i32,ptr %_1
%_118 = add i32 %_117,1
store i32 %_118,ptr %_1
%_119 = load i32,ptr %_2
%_120 = add i32 %_119,1
store i32 %_120,ptr %_2
br label %_b.12

_b.11:
store i32 114514,ptr %_2
br label %_b.12

_b.12:
br label %_b.4

_b.3:
%_10 = icmp eq i32 3,3
br i1 %_10,label %_b.6,label %_b.7

_b.5:
%_124 = load i32,ptr %_2
ret i32 %_124

}
define i32 @main(){
call void @_initGlobal()
br label %_b.13
_b.13:
%_125 = call i32 @test()
%_126 = sub i32 %_125,100
%_127 = call ptr @printInt(i32 %_126)
%_128 = call i32 @test()
%_129 = sub i32 %_128,100
ret i32 %_129

}
