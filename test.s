.text
 .globl _initGlobal
_initGlobal:
   addi sp, sp, -64
   sw ra, 60(sp)
   sw s11, 12(sp)
   sw s10, 16(sp)
   sw s9, 20(sp)
   sw s8, 24(sp)
   sw s7, 28(sp)
   sw s6, 32(sp)
   sw s5, 36(sp)
   sw s4, 40(sp)
   sw s3, 44(sp)
   sw s2, 48(sp)
   sw s1, 52(sp)
   sw s0, 56(sp)
   addi s0, sp, 64
   li s11, 20
   mv a0, s11
   call _malloc
   mv s9, a0
   li s11, 0
   li s8, 4
   mul s10, s11, s8
   add s8, s9, s10
   li s11, 4
   sw s11, 0(s8)
   li s11, 1
   li s8, 4
   mul s10, s11, s8
   add s11, s9, s10
   li s10, 0
   mv s7, s10
   j _b.2
   
_b.2:
   mv s9, s7
   li s8, 4
   slt s10, s9, s8
   beqz s10, _b.5
   j _b.4
   
_b.3:
   mv s8, s7
   li s9, 1
   add s10, s8, s9
   mv s7, s10
   j _b.2
   
_b.4:
   mv s8, s7
   li s10, 4
   mul s9, s8, s10
   add s10, s11, s9
   j _b.3
   
_b.5:
   la s10, @_1
   sw s11, 0(s10)
   lw s0, 56(sp)
   lw s1, 52(sp)
   lw s2, 48(sp)
   lw s3, 44(sp)
   lw s4, 40(sp)
   lw s5, 36(sp)
   lw s6, 32(sp)
   lw s7, 28(sp)
   lw s8, 24(sp)
   lw s9, 20(sp)
   lw s10, 16(sp)
   lw s11, 12(sp)
   lw ra, 60(sp)
   addi sp, sp, 64
   ret
   

 .globl main
main:
   addi sp, sp, -64
   sw ra, 60(sp)
   sw s11, 12(sp)
   sw s10, 16(sp)
   sw s9, 20(sp)
   sw s8, 24(sp)
   sw s7, 28(sp)
   sw s6, 32(sp)
   sw s5, 36(sp)
   sw s4, 40(sp)
   sw s3, 44(sp)
   sw s2, 48(sp)
   sw s1, 52(sp)
   sw s0, 56(sp)
   addi s0, sp, 64
   call _initGlobal
   j _b.6
   
_b.6:
   la s11, @_1
   lw s9, 0(s11)
   li s10, -1
   li s11, 4
   mul s8, s10, s11
   add s11, s9, s8
   lw s10, 0(s11)
   li s11, 1
   add s9, s10, s11
   li s11, 4
   mul s8, s9, s11
   mv a0, s8
   call _malloc
   mv s7, a0
   li s8, 0
   li s9, 4
   mul s11, s8, s9
   add s9, s7, s11
   sw s10, 0(s9)
   li s11, 1
   li s8, 4
   mul s9, s11, s8
   add s11, s7, s9
   li s9, 0
   mv s6, s9
   j _b.7
   
_b.7:
   mv s9, s6
   slt s8, s9, s10
   beqz s8, _b.10
   j _b.9
   
_b.8:
   mv s8, s6
   li s9, 1
   add s7, s8, s9
   mv s6, s7
   j _b.7
   
_b.9:
   mv s9, s6
   li s7, 4
   mul s8, s9, s7
   add s9, s11, s8
   j _b.8
   
_b.10:
   la s10, @_1
   lw s8, 0(s10)
   li s7, 0
   li s9, 4
   mul s10, s7, s9
   add s9, s8, s10
   li s10, 0
   sw s10, 0(s9)
   li s9, 0
   li s8, 4
   mul s10, s9, s8
   add s9, s11, s10
   call getInt
   mv s10, a0
   sw s10, 0(s9)
   la s9, @_1
   lw s10, 0(s9)
   li s7, 0
   li s8, 4
   mul s9, s7, s8
   add s8, s10, s9
   lw s10, 0(s8)
   mv a0, s10
   call toString
   mv s10, a0
   mv a0, s10
   call print
   mv s10, a0
   la s10, @.str1
   mv a0, s10
   call println
   mv s10, a0
   la s10, @_1
   sw s11, 0(s10)
   li s9, 0
   li s10, 4
   mul s8, s9, s10
   add s10, s11, s8
   lw s11, 0(s10)
   mv a0, s11
   call toString
   mv s11, a0
   mv a0, s11
   call print
   mv s11, a0
   li s11, 0
   mv a0, s11
   lw s0, 56(sp)
   lw s1, 52(sp)
   lw s2, 48(sp)
   lw s3, 44(sp)
   lw s4, 40(sp)
   lw s5, 36(sp)
   lw s6, 32(sp)
   lw s7, 28(sp)
   lw s8, 24(sp)
   lw s9, 20(sp)
   lw s10, 16(sp)
   lw s11, 12(sp)
   lw ra, 60(sp)
   addi sp, sp, 64
   ret
   

 .section .data
@_1:
 .word 0

 .section .rodata
@.str1:
 .asciz " "

