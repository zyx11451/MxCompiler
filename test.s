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
   

 .globl test
test:
   addi sp, sp, -32
   sw ra, 28(sp)
   sw s0, 24(sp)
   addi s0, sp, 32
   li %2, 0
   mv %1, %2
   li %3, 0
   mv %0, %3
   j _b.2
   
_b.2:
   mv %4, %0
   li %6, 200
   slt %5, %4, %6
   beqz %5, _b.5
   j _b.3
   
_b.4:
   mv %7, %0
   li %9, 1
   add %8, %7, %9
   mv %0, %8
   j _b.2
   
_b.6:
   j phi1
   
_b.7:
   mv %11, %10
   beqz %11, phi2
   j _b.8
   
_b.8:
   j phi3
   
_b.9:
   mv %15, %14
   beqz %15, _b.11
   j _b.10
   
_b.10:
   mv %17, %0
   li %19, 1
   add %18, %17, %19
   mv %0, %18
   mv %20, %1
   li %22, 1
   add %21, %20, %22
   mv %1, %21
   j _b.12
   
_b.11:
   li %23, 114514
   mv %1, %23
   j _b.12
   
_b.12:
   j _b.4
   
_b.3:
   j _b.6
   
_b.5:
   mv %24, %1
   mv a0, %24
   lw s0, 24(sp)
   lw ra, 28(sp)
   addi sp, sp, 32
   ret
   
phi1:
   mv %10, %12
   j _b.7
   
phi2:
   mv %14, %11
   j _b.9
   
phi3:
   mv %14, %16
   j _b.9
   
phi4:
   mv %10, %13
   j _b.7
   

 .globl main
main:
   addi sp, sp, -16
   sw ra, 12(sp)
   sw s0, 8(sp)
   addi s0, sp, 16
   call _initGlobal
   j _b.13
   
_b.13:
   call test
   mv %25, a0
   li %27, 100
   sub %26, %25, %27
   mv a0, %26
   lw s0, 8(sp)
   lw ra, 12(sp)
   addi sp, sp, 16
   ret
   

