.text
 .globl _initGlobal
_initGlobal:
   li %1, 20
   mv a0, %1
   call _malloc
   mv %2, a0
   li %4, 0
   li %6, 4
   mul %5, %4, %6
   add %3, %2, %5
   li %7, 4
   sw %7, 0(%3)
   li %9, 1
   li %11, 4
   mul %10, %9, %11
   add %8, %2, %10
   li %12, 0
   mv %0, %12
   j _b.2
   
_b.2:
   mv %13, %0
   li %15, 4
   slt %14, %13, %15
   beqz %14, _b.5
   j _b.4
   
_b.3:
   mv %16, %0
   li %18, 1
   add %17, %16, %18
   mv %0, %17
   j _b.2
   
_b.4:
   mv %19, %0
   li %22, 4
   mul %21, %19, %22
   add %20, %8, %21
   j _b.3
   
_b.5:
   la %23, @_1
   sw %8, 0(%23)
   ret
   

 .globl main
main:
   call _initGlobal
   j _b.6
   
_b.6:
   la %27, @_1
   lw %26, 0(%27)
   li %29, -1
   li %31, 4
   mul %30, %29, %31
   add %28, %26, %30
   lw %32, 0(%28)
   li %34, 1
   add %33, %32, %34
   li %36, 4
   mul %35, %33, %36
   mv a0, %35
   call _malloc
   mv %37, a0
   li %39, 0
   li %41, 4
   mul %40, %39, %41
   add %38, %37, %40
   sw %32, 0(%38)
   li %43, 1
   li %45, 4
   mul %44, %43, %45
   add %42, %37, %44
   li %46, 0
   mv %24, %46
   j _b.7
   
_b.7:
   mv %47, %24
   slt %48, %47, %32
   beqz %48, _b.10
   j _b.9
   
_b.8:
   mv %49, %24
   li %51, 1
   add %50, %49, %51
   mv %24, %50
   j _b.7
   
_b.9:
   mv %52, %24
   li %55, 4
   mul %54, %52, %55
   add %53, %42, %54
   j _b.8
   
_b.10:
   la %57, @_1
   lw %56, 0(%57)
   li %59, 0
   li %61, 4
   mul %60, %59, %61
   add %58, %56, %60
   li %62, 0
   sw %62, 0(%58)
   li %64, 0
   li %66, 4
   mul %65, %64, %66
   add %63, %42, %65
   call getInt
   mv %67, a0
   sw %67, 0(%63)
   la %69, @_1
   lw %68, 0(%69)
   li %71, 0
   li %73, 4
   mul %72, %71, %73
   add %70, %68, %72
   lw %74, 0(%70)
   mv a0, %74
   call toString
   mv %75, a0
   mv a0, %75
   call print
   mv %76, a0
   la %77, @.str1
   mv a0, %77
   call println
   mv %78, a0
   la %79, @_1
   sw %42, 0(%79)
   li %81, 0
   li %83, 4
   mul %82, %81, %83
   add %80, %42, %82
   lw %84, 0(%80)
   mv a0, %84
   call toString
   mv %85, a0
   mv a0, %85
   call print
   mv %86, a0
   li %87, 0
   mv a0, %87
   ret
   

 .section .data
@_1:
 .word 0

 .section .rodata
@.str1:
 .asciz " "

