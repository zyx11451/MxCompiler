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
   

 .globl main
main:
   addi sp, sp, -32
   sw ra, 28(sp)
   sw s0, 24(sp)
   addi s0, sp, 32
   call _initGlobal
   j _b.1
   
_b.1:
   call getInt
   mv %3, a0
   mv %0, %3
   call getInt
   mv %4, a0
   mv %1, %4
   call getInt
   mv %5, a0
   mv %2, %5
   li %7, 1
   add %6, %3, %7
   mv %0, %6
   xor %9, %3, %4
   seqz %8, %9
   beqz %8, _b.3
   j _b.2
   
_b.5:
   mv %10, %2
   li %12, 1
   add %11, %10, %12
   mv %2, %11
   j phi1
   
_b.6:
   mv %13, %0
   li %15, 1
   add %14, %13, %15
   mv %0, %14
   j phi2
   
_b.7:
   mv %17, %16
   j phi3
   
_b.2:
   mv %18, %0
   li %20, 1
   add %19, %18, %20
   mv %0, %19
   mv %21, %1
   xor %23, %18, %21
   seqz %22, %23
   beqz %22, _b.6
   j _b.5
   
_b.11:
   mv %24, %1
   li %26, 1
   add %25, %24, %26
   mv %1, %25
   j phi4
   
_b.12:
   mv %27, %2
   li %29, 1
   add %28, %27, %29
   mv %2, %28
   j phi5
   
_b.13:
   mv %31, %30
   j phi6
   
_b.8:
   mv %32, %1
   li %34, 1
   add %33, %32, %34
   mv %1, %33
   mv %35, %2
   li %37, 1
   add %36, %35, %37
   mv %2, %36
   xor %39, %33, %35
   seqz %38, %39
   beqz %38, _b.12
   j _b.11
   
_b.9:
   mv %40, %0
   li %42, 1
   add %41, %40, %42
   mv %0, %41
   j phi7
   
_b.10:
   mv %44, %43
   j phi8
   
_b.3:
   mv %45, %0
   li %47, 1
   add %46, %45, %47
   mv %0, %46
   mv %48, %1
   xor %50, %45, %48
   seqz %49, %50
   beqz %49, _b.9
   j _b.8
   
_b.4:
   mv %52, %51
   mv %53, %0
   mv a0, %53
   call printlnInt
   mv %54, a0
   mv %55, %1
   mv a0, %55
   call printlnInt
   mv %56, a0
   mv %57, %2
   mv a0, %57
   call printlnInt
   mv %58, a0
   mv a0, %52
   call printlnInt
   mv %59, a0
   li %60, 0
   mv a0, %60
   lw s0, 24(sp)
   lw ra, 28(sp)
   addi sp, sp, 32
   ret
   
phi1:
   mv %16, %11
   j _b.7
   
phi2:
   mv %16, %14
   j _b.7
   
phi3:
   mv %51, %17
   j _b.4
   
phi4:
   mv %30, %25
   j _b.13
   
phi5:
   mv %30, %28
   j _b.13
   
phi6:
   mv %43, %31
   j _b.10
   
phi7:
   mv %43, %41
   j _b.10
   
phi8:
   mv %51, %44
   j _b.4
   

