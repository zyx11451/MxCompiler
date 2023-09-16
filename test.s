.text
 .globl _initGlobal
_initGlobal:
   addi sp, sp, -16
   sw ra, 12(sp)
   sw s0, 8(sp)
   addi s0, sp, 16
   la %0, @_1
   li %1, 19260817
   sw %1, 0(%0)
   lw s0, 8(sp)
   lw ra, 12(sp)
   addi sp, sp, 16
   ret
   

 .globl unsigned_shl
unsigned_shl:
   addi sp, sp, -16
   sw ra, 12(sp)
   sw s0, 8(sp)
   addi s0, sp, 16
   mv %2, a0
   mv %3, a1
   sll %4, %2, %3
   mv a0, %4
   lw s0, 8(sp)
   lw ra, 12(sp)
   addi sp, sp, 16
   ret
   

 .globl unsigned_shr
unsigned_shr:
   addi sp, sp, -32
   sw ra, 28(sp)
   sw s0, 24(sp)
   addi s0, sp, 32
   mv %5, a0
   mv %6, a1
   mv %7, %5
   mv %8, %6
   li %10, -2147483648
   mv %9, %10
   li %12, 0
   slt %11, %5, %12
   xori %11, %11, 1
   beqz %11, _b.5
   j _b.4
   
_b.4:
   mv %13, %7
   mv %14, %8
   sra %15, %13, %14
   mv a0, %15
   lw s0, 24(sp)
   lw ra, 28(sp)
   addi sp, sp, 32
   ret
   j _b.6
   
_b.5:
   mv %16, %8
   li %18, 31
   sub %17, %18, %16
   li %20, 1
   sll %19, %20, %17
   mv %21, %7
   mv %22, %9
   xor %23, %21, %22
   mv %24, %8
   sra %25, %23, %24
   or %26, %19, %25
   mv a0, %26
   lw s0, 24(sp)
   lw ra, 28(sp)
   addi sp, sp, 32
   ret
   j _b.6
   
_b.6:
   li %27, 0
   mv a0, %27
   lw s0, 24(sp)
   lw ra, 28(sp)
   addi sp, sp, 32
   ret
   

 .globl rng
rng:
   addi sp, sp, -16
   sw ra, 12(sp)
   sw s0, 8(sp)
   addi s0, sp, 16
   la %29, @_1
   lw %28, 0(%29)
   mv a0, %28
   li %30, 13
   mv a1, %30
   call unsigned_shl
   mv %31, a0
   xor %32, %28, %31
   mv a0, %32
   li %33, 17
   mv a1, %33
   call unsigned_shr
   mv %34, a0
   xor %35, %32, %34
   mv a0, %35
   li %36, 5
   mv a1, %36
   call unsigned_shl
   mv %37, a0
   xor %38, %35, %37
   la %39, @_1
   sw %38, 0(%39)
   li %41, 1073741823
   and %40, %38, %41
   mv a0, %40
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
   j _b.8
   
_b.8:
   j _b.9
   
_b.9:
   la %46, @_1
   lw %45, 0(%46)
   li %48, 100
   rem %47, %45, %48
   li %50, 0
   xor %51, %47, %50
   snez %49, %51
   beqz %49, _b.11
   j _b.10
   
_b.10:
   la %53, @_1
   lw %52, 0(%53)
   li %55, -1
   add %54, %52, %55
   la %56, @_1
   sw %54, 0(%56)
   j _b.9
   
_b.11:
   call rng
   mv %57, a0
   mv %42, %57
   call rng
   mv %58, a0
   li %60, 127
   and %59, %58, %60
   li %62, 100000
   add %61, %59, %62
   mv %44, %61
   li %63, 0
   mv %43, %63
   j _b.12
   
_b.12:
   mv %64, %43
   mv %65, %44
   slt %66, %64, %65
   beqz %66, _b.15
   j _b.13
   
_b.14:
   mv %67, %43
   li %69, 1
   add %68, %67, %69
   mv %43, %68
   j _b.12
   
_b.13:
   mv %70, %42
   li %72, 2
   mul %71, %70, %72
   li %74, 1
   add %73, %71, %74
   li %76, 2
   div %75, %73, %76
   li %78, 3
   div %77, %75, %78
   mv %79, %43
   li %81, 1
   add %80, %79, %81
   div %82, %77, %80
   mv %83, %43
   add %84, %82, %83
   li %86, 100
   div %85, %84, %86
   mv %87, %43
   add %88, %85, %87
   mul %89, %88, %88
   li %91, 10000
   div %90, %89, %91
   li %93, 1
   add %92, %90, %93
   mv %42, %92
   j _b.14
   
_b.15:
   li %94, 0
   mv a0, %94
   lw s0, 24(sp)
   lw ra, 28(sp)
   addi sp, sp, 32
   ret
   

 .section .data
@_1:
 .word 0

