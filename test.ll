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
store i32 19260817,ptr @_1

ret void
}

define i32 @unsigned_shl( i32 %_1,i32 %_3){
_b.2:
%_7 = shl i32 %_1,%_3
ret i32 %_7

}
define i32 @unsigned_shr( i32 %_8,i32 %_10){
_b.3:
%_9 = alloca i32
store i32 %_8,ptr %_9
%_11 = alloca i32
store i32 %_10,ptr %_11
%_12 = alloca i32
store i32 -2147483648,ptr %_12
%_15 = icmp sge i32 %_8,0
br i1 %_15,label %_b.4,label %_b.5

_b.4:
%_16 = load i32,ptr %_9
%_17 = load i32,ptr %_11
%_18 = ashr i32 %_16,%_17
ret i32 %_18
br label %_b.6

_b.5:
%_19 = load i32,ptr %_11
%_20 = sub i32 31,%_19
%_21 = shl i32 1,%_20
%_22 = load i32,ptr %_9
%_23 = load i32,ptr %_12
%_24 = xor i32 %_22,%_23
%_25 = load i32,ptr %_11
%_26 = ashr i32 %_24,%_25
%_27 = or i32 %_21,%_26
ret i32 %_27
br label %_b.6

_b.6:
ret i32 0

}
define i32 @rng(){
_b.7:
%_30 = load i32,ptr @_1
%_32 = call i32 @unsigned_shl(i32 %_30,i32 13)
%_34 = xor i32 %_30,%_32
%_36 = call i32 @unsigned_shr(i32 %_34,i32 17)
%_38 = xor i32 %_34,%_36
%_40 = call i32 @unsigned_shl(i32 %_38,i32 5)
%_42 = xor i32 %_38,%_40
store i32 %_42,ptr @_1
%_46 = and i32 %_42,1073741823
ret i32 %_46

}
define i32 @main(){
call void @_initGlobal()
br label %_b.8
_b.8:
br label %_b.9

_b.9:
%_47 = load i32,ptr @_1
%_48 = srem i32 %_47,100
%_49 = icmp ne i32 %_48,0
br i1 %_49,label %_b.10,label %_b.11

_b.10:
%_50 = load i32,ptr @_1
%_51 = add i32 %_50,-1
store i32 %_51,ptr @_1
br label %_b.9

_b.11:
%_52 = alloca i32
%_53 = call i32 @rng()
store i32 %_53,ptr %_52
%_54 = alloca i32
%_55 = alloca i32
%_56 = call i32 @rng()
%_57 = and i32 %_56,127
%_58 = add i32 %_57,100000
store i32 %_58,ptr %_55
store i32 0,ptr %_54
br label %_b.12

_b.12:
%_59 = load i32,ptr %_54
%_60 = load i32,ptr %_55
%_61 = icmp slt i32 %_59,%_60
br i1 %_61,label %_b.13,label %_b.15

_b.14:
%_62 = load i32,ptr %_54
%_63 = add i32 %_62,1
store i32 %_63,ptr %_54
br label %_b.12

_b.13:
%_64 = load i32,ptr %_52
%_65 = mul i32 %_64,2
%_67 = add i32 %_65,1
%_69 = sdiv i32 %_67,2
%_71 = sdiv i32 %_69,3
%_72 = load i32,ptr %_54
%_73 = add i32 %_72,1
%_75 = sdiv i32 %_71,%_73
%_77 = load i32,ptr %_54
%_78 = add i32 %_75,%_77
%_80 = sdiv i32 %_78,100
%_82 = load i32,ptr %_54
%_83 = add i32 %_80,%_82
%_86 = mul i32 %_83,%_83
%_88 = sdiv i32 %_86,10000
%_90 = add i32 %_88,1
store i32 %_90,ptr %_52
br label %_b.14

_b.15:
ret i32 0

}
