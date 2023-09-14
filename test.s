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
   

 .globl dceconst
dceconst:
   addi sp, sp, -32
   sw ra, 28(sp)
   sw s0, 24(sp)
   addi s0, sp, 32
   li %5, 114514
   mv %3, %5
   mv %6, %3
   mv a0, %6
   lw s0, 24(sp)
   lw ra, 28(sp)
   addi sp, sp, 32
   ret
   

 .globl main
main:
   addi sp, sp, -16
   sw ra, 12(sp)
   sw s0, 8(sp)
   addi s0, sp, 16
   call _initGlobal
   j _b.2
   
_b.2:
   call dceconst
   mv %7, a0
   mv a0, %7
   call printInt
   mv %8, a0
   li %9, 0
   mv a0, %9
   lw s0, 8(sp)
   lw ra, 12(sp)
   addi sp, sp, 16
   ret
   

