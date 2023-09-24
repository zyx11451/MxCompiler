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
   mv s10, a0
   li s8, 0
   li s9, 4
   mul s11, s8, s9
   add s9, s10, s11
   li s11, 4
   sw s11, 0(s9)
   li s11, 1
   li s8, 4
   mul s9, s11, s8
   add s11, s10, s9
   li s10, 0
   mv s7, s10
   j _b.2

_b.2:
   mv s10, s7
   li s8, 4
   slt s9, s10, s8
   beqz s9, _b.5
   j _b.4

_b.3:
   mv s9, s7
   li s8, 1
   add s10, s9, s8
   mv s7, s10
   j _b.2

_b.4:
   mv s8, s7
   li s9, 4
   mul s10, s8, s9
   add s9, s11, s10
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
   addi sp, sp, -80
   sw ra, 76(sp)
   sw s11, 28(sp)
   sw s10, 32(sp)
   sw s9, 36(sp)
   sw s8, 40(sp)
   sw s7, 44(sp)
   sw s6, 48(sp)
   sw s5, 52(sp)
   sw s4, 56(sp)
   sw s3, 60(sp)
   sw s2, 64(sp)
   sw s1, 68(sp)
   sw s0, 72(sp)
   addi s0, sp, 80
   call _initGlobal
   j _b.6

_b.6:
   la s11, @_1
   lw s10, 0(s11)
   li s8, -1
   li s9, 4
   mul s11, s8, s9
   add s9, s10, s11
   lw s10, 0(s9)
   li s11, 1
   add s9, s10, s11
   li s10, 4
   mul s11, s9, s10
   mv a0, s11
   call _malloc
   mv s8, a0
   li s11, 0
   li s9, 4
   mul s10, s11, s9
   add s11, s8, s10
   sw s10, 0(s11)
   li s9, 1
   li s7, 4
   mul s11, s9, s7
   add s6, s8, s11
   li s9, 0
   mv s11, s9
   j _b.7

_b.7:
   mv s9, s11
   slt s8, s9, s10
   beqz s8, _b.10
   j _b.9

_b.8:
   mv s9, s11
   li s11, 1
   add s8, s9, s11
   mv s11, s8
   j _b.7

_b.9:
   mv s9, s11
   li s8, 4
   mul s7, s9, s8
   add s9, s6, s7
   j _b.8

_b.10:
   mv s11, s6
   li s9, 0
   mv s10, s9
   j _b.11

_b.11:
   la s9, @_1
   lw s8, 0(s9)
   li s6, -1
   li s7, 4
   mul s9, s6, s7
   add s7, s8, s9
   lw s9, 0(s7)
   mv s7, s10
   slt s8, s7, s9
   beqz s8, _b.14
   j _b.12

_b.13:
   mv s9, s10
   li s10, 1
   add s8, s9, s10
   mv s10, s8
   j _b.11

_b.12:
   la s9, @_1
   lw s7, 0(s9)
   mv s9, s10
   li s8, 4
   mul s6, s9, s8
   add s8, s7, s6
   li s9, 0
   sw s9, 0(s8)
   mv s8, s11
   mv s9, s10
   li s7, 4
   mul s6, s9, s7
   add s9, s8, s6
   call getInt
   mv s8, a0
   sw s8, 0(s9)
   j _b.13

_b.14:
   li s9, 0
   mv s10, s9
   j _b.15

_b.15:
   la s8, @_1
   lw s9, 0(s8)
   li s7, -1
   li s8, 4
   mul s6, s7, s8
   add s8, s9, s6
   lw s7, 0(s8)
   mv s9, s10
   slt s8, s9, s7
   beqz s8, _b.18
   j _b.16

_b.17:
   mv s8, s10
   li s10, 1
   add s9, s8, s10
   mv s10, s9
   j _b.15

_b.16:
   la s9, @_1
   lw s7, 0(s9)
   mv s9, s10
   li s8, 4
   mul s6, s9, s8
   add s9, s7, s6
   lw s8, 0(s9)
   mv a0, s8
   call toString
   mv s9, a0
   mv a0, s9
   call print
   mv s9, a0
   j _b.17

_b.18:
   la s10, @.str1
   mv a0, s10
   call println
   mv s10, a0
   mv s10, s11
   la s11, @_1
   sw s10, 0(s11)
   li s11, 0
   mv s10, s11
   j _b.19

_b.19:
   la s11, @_1
   lw s9, 0(s11)
   li s8, -1
   li s11, 4
   mul s7, s8, s11
   add s11, s9, s7
   lw s9, 0(s11)
   mv s8, s10
   slt s11, s8, s9
   beqz s11, _b.22
   j _b.20

_b.21:
   mv s9, s10
   li s11, 1
   add s8, s9, s11
   mv s10, s8
   j _b.19

_b.20:
   la s11, @_1
   lw s8, 0(s11)
   mv s11, s10
   li s9, 4
   mul s7, s11, s9
   add s11, s8, s7
   lw s9, 0(s11)
   mv a0, s9
   call toString
   mv s11, a0
   mv a0, s11
   call print
   mv s11, a0
   j _b.21

_b.22:
   li s11, 0
   mv a0, s11
   lw s0, 72(sp)
   lw s1, 68(sp)
   lw s2, 64(sp)
   lw s3, 60(sp)
   lw s4, 56(sp)
   lw s5, 52(sp)
   lw s6, 48(sp)
   lw s7, 44(sp)
   lw s8, 40(sp)
   lw s9, 36(sp)
   lw s10, 32(sp)
   lw s11, 28(sp)
   lw ra, 76(sp)
   addi sp, sp, 80
   ret


 .section .data
@_1:
 .word 0

 .section .rodata
@.str1:
 .asciz " "

