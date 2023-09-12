.text
 .globl _initGlobal
_initGlobal:
   addi sp, sp, -16
   sw ra, 12(sp)
   sw s0, 8(sp)
   addi s0, sp, 16
   lw s0, 8(sp)
   lw ra, 12(sp)
   addi sp, sp, 16
   ret
   

 .globl check
check:
   addi sp, sp, -16
   sw ra, 12(sp)
   sw s0, 8(sp)
   addi s0, sp, 16
   lw s0, 8(sp)
   lw ra, 12(sp)
   addi sp, sp, 16
   ret
   

 .globl main
main:
   addi sp, sp, -32
   sw ra, 28(sp)
   sw s0, 24(sp)
   addi s0, sp, 32
   call _initGlobal
   j _b.2
   
_b.2:
   call getInt
   mv %5, a0
   mv %0, %5
   li %7, 1
   li %8, 1
   add %6, %7, %8
   li %10, 4
   mul %9, %6, %10
   mv a0, %9
   call _malloc
   mv %11, a0
   li %13, 0
   li %15, 4
   mul %14, %13, %15
   add %12, %11, %14
   li %16, 1
   sw %16, 0(%12)
   li %18, 1
   li %20, 4
   mul %19, %18, %20
   add %17, %11, %19
   li %21, 0
   mv %3, %21
   j _b.3
   
_b.3:
   mv %22, %3
   li %24, 1
   slt %23, %22, %24
   beqz %23, _b.6
   j _b.5
   
_b.4:
   mv %25, %3
   li %27, 1
   add %26, %25, %27
   mv %3, %26
   j _b.3
   
_b.7:
   mv %28, %4
   slt %29, %28, %30
   beqz %29, _b.10
   j _b.9
   
_b.8:
   mv %31, %4
   li %33, 1
   add %32, %31, %33
   mv %4, %32
   j _b.7
   
_b.9:
   mv %34, %4
   li %37, 4
   mul %36, %34, %37
   add %35, %38, %36
   j _b.8
   
_b.10:
   sw %38, 0(%39)
   j _b.4
   
_b.5:
   mv %40, %3
   li %42, 4
   mul %41, %40, %42
   add %39, %17, %41
   mv %30, %0
   li %44, 1
   add %43, %30, %44
   li %46, 4
   mul %45, %43, %46
   mv a0, %45
   call _malloc
   mv %47, a0
   li %49, 0
   li %51, 4
   mul %50, %49, %51
   add %48, %47, %50
   sw %30, 0(%48)
   li %52, 1
   li %54, 4
   mul %53, %52, %54
   add %38, %47, %53
   li %55, 0
   mv %4, %55
   j _b.7
   
_b.6:
   mv %1, %17
   mv %56, %1
   li %58, 0
   li %60, 4
   mul %59, %58, %60
   add %57, %56, %59
   lw %61, 0(%57)
   li %63, 24
   li %65, 4
   mul %64, %63, %65
   add %62, %61, %64
   li %67, 0
   li %68, 1
   sub %66, %67, %68
   sw %66, 0(%62)
   call check
   mv %69, a0
   li %70, 0
   mv a0, %70
   lw s0, 24(sp)
   lw ra, 28(sp)
   addi sp, sp, 32
   ret
   

