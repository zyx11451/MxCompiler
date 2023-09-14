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
   li %11, 1
   li %12, 1
   xor %13, %11, %12
   seqz %10, %13
   j phi1
   
_b.7:
   mv %15, %14
   beqz %15, phi2
   j _b.8
   
_b.8:
   li %18, 5050
   li %19, 5050
   xor %20, %18, %19
   seqz %17, %20
   j phi3
   
_b.9:
   mv %22, %21
   beqz %22, _b.11
   j _b.10
   
_b.10:
   mv %23, %0
   li %25, 1
   add %24, %23, %25
   mv %0, %24
   mv %26, %1
   li %28, 1
   add %27, %26, %28
   mv %1, %27
   j _b.12
   
_b.11:
   li %29, 114514
   mv %1, %29
   j _b.12
   
_b.12:
   j _b.4
   
_b.3:
   li %30, 3
   li %31, 3
   xor %32, %30, %31
   seqz %16, %32
   beqz %16, phi4
   j _b.6
   
_b.5:
   mv %33, %1
   mv a0, %33
   lw s0, 24(sp)
   lw ra, 28(sp)
   addi sp, sp, 32
   ret
   
phi1:
   mv %14, %10
   j _b.7
   
phi2:
   mv %21, %15
   j _b.9
   
phi3:
   mv %21, %17
   j _b.9
   
phi4:
   mv %14, %16
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
   mv %34, a0
   li %36, 100
   sub %35, %34, %36
   mv a0, %35
   call printInt
   mv %37, a0
   call test
   mv %38, a0
   li %40, 100
   sub %39, %38, %40
   mv a0, %39
   lw s0, 8(sp)
   lw ra, 12(sp)
   addi sp, sp, 16
   ret
   

