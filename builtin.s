	.text
	.attribute	4, 16
	.attribute	5, "rv32i2p1"
	.file	"builtin.c"
	.option	push
	.option	arch, +a, +c, +m
	.globl	_arraySize                      # -- Begin function _arraySize
	.p2align	1
	.type	_arraySize,@function
_arraySize:                             # @_arraySize
# %bb.0:
	lw	a0, 0(a0)
	ret
.Lfunc_end0:
	.size	_arraySize, .Lfunc_end0-_arraySize
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	_arrayElement                   # -- Begin function _arrayElement
	.p2align	1
	.type	_arrayElement,@function
_arrayElement:                          # @_arrayElement
# %bb.0:
	lw	a0, 4(a0)
	add	a0, a0, a1
	ret
.Lfunc_end1:
	.size	_arrayElement, .Lfunc_end1-_arrayElement
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	print                           # -- Begin function print
	.p2align	1
	.type	print,@function
print:                                  # @print
# %bb.0:
	lui	a1, %hi(.L.str)
	addi	a1, a1, %lo(.L.str)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end2:
	.size	print, .Lfunc_end2-print
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	println                         # -- Begin function println
	.p2align	1
	.type	println,@function
println:                                # @println
# %bb.0:
	lui	a1, %hi(.L.str.1)
	addi	a1, a1, %lo(.L.str.1)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end3:
	.size	println, .Lfunc_end3-println
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	printInt                        # -- Begin function printInt
	.p2align	1
	.type	printInt,@function
printInt:                               # @printInt
# %bb.0:
	lui	a1, %hi(.L.str.2)
	addi	a1, a1, %lo(.L.str.2)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end4:
	.size	printInt, .Lfunc_end4-printInt
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	printlnInt                      # -- Begin function printlnInt
	.p2align	1
	.type	printlnInt,@function
printlnInt:                             # @printlnInt
# %bb.0:
	lui	a1, %hi(.L.str.3)
	addi	a1, a1, %lo(.L.str.3)
	mv	a2, a0
	mv	a0, a1
	mv	a1, a2
	tail	printf
.Lfunc_end5:
	.size	printlnInt, .Lfunc_end5-printlnInt
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	_malloc                         # -- Begin function _malloc
	.p2align	1
	.type	_malloc,@function
_malloc:                                # @_malloc
# %bb.0:
	tail	malloc
.Lfunc_end6:
	.size	_malloc, .Lfunc_end6-_malloc
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	getString                       # -- Begin function getString
	.p2align	1
	.type	getString,@function
getString:                              # @getString
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	li	a0, 1024
	call	malloc
	mv	s0, a0
	lui	a0, %hi(.L.str)
	addi	a0, a0, %lo(.L.str)
	mv	a1, s0
	call	scanf
	mv	a0, s0
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end7:
	.size	getString, .Lfunc_end7-getString
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	getInt                          # -- Begin function getInt
	.p2align	1
	.type	getInt,@function
getInt:                                 # @getInt
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	lui	a0, %hi(.L.str.2)
	addi	a0, a0, %lo(.L.str.2)
	addi	a1, sp, 8
	call	scanf
	lw	a0, 8(sp)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end8:
	.size	getInt, .Lfunc_end8-getInt
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	toString                        # -- Begin function toString
	.p2align	1
	.type	toString,@function
toString:                               # @toString
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	sw	s1, 4(sp)                       # 4-byte Folded Spill
	mv	s0, a0
	li	a0, 16
	call	malloc
	mv	s1, a0
	lui	a0, %hi(.L.str.2)
	addi	a1, a0, %lo(.L.str.2)
	mv	a0, s1
	mv	a2, s0
	call	sprintf
	mv	a0, s1
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	lw	s1, 4(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end9:
	.size	toString, .Lfunc_end9-toString
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	_string.plus                    # -- Begin function _string.plus
	.p2align	1
	.type	_string.plus,@function
_string.plus:                           # @_string.plus
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	sw	s0, 8(sp)                       # 4-byte Folded Spill
	sw	s1, 4(sp)                       # 4-byte Folded Spill
	sw	s2, 0(sp)                       # 4-byte Folded Spill
	mv	s2, a1
	mv	s1, a0
	li	a0, 1024
	call	malloc
	mv	s0, a0
	mv	a1, s1
	call	strcpy
	mv	a0, s0
	mv	a1, s2
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	lw	s0, 8(sp)                       # 4-byte Folded Reload
	lw	s1, 4(sp)                       # 4-byte Folded Reload
	lw	s2, 0(sp)                       # 4-byte Folded Reload
	addi	sp, sp, 16
	tail	strcat
.Lfunc_end10:
	.size	_string.plus, .Lfunc_end10-_string.plus
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	_string.eq                      # -- Begin function _string.eq
	.p2align	1
	.type	_string.eq,@function
_string.eq:                             # @_string.eq
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	seqz	a0, a0
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end11:
	.size	_string.eq, .Lfunc_end11-_string.eq
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	_string.ne                      # -- Begin function _string.ne
	.p2align	1
	.type	_string.ne,@function
_string.ne:                             # @_string.ne
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	snez	a0, a0
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end12:
	.size	_string.ne, .Lfunc_end12-_string.ne
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	_string.l                       # -- Begin function _string.l
	.p2align	1
	.type	_string.l,@function
_string.l:                              # @_string.l
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	srli	a0, a0, 31
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end13:
	.size	_string.l, .Lfunc_end13-_string.l
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	_string.g                       # -- Begin function _string.g
	.p2align	1
	.type	_string.g,@function
_string.g:                              # @_string.g
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	sgtz	a0, a0
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end14:
	.size	_string.g, .Lfunc_end14-_string.g
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	_string.le                      # -- Begin function _string.le
	.p2align	1
	.type	_string.le,@function
_string.le:                             # @_string.le
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	slti	a0, a0, 1
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end15:
	.size	_string.le, .Lfunc_end15-_string.le
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	_string.ge                      # -- Begin function _string.ge
	.p2align	1
	.type	_string.ge,@function
_string.ge:                             # @_string.ge
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	call	strcmp
	not	a0, a0
	srli	a0, a0, 31
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end16:
	.size	_string.ge, .Lfunc_end16-_string.ge
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	_string.length                  # -- Begin function _string.length
	.p2align	1
	.type	_string.length,@function
_string.length:                         # @_string.length
# %bb.0:
	tail	strlen
.Lfunc_end17:
	.size	_string.length, .Lfunc_end17-_string.length
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	_string.substring               # -- Begin function _string.substring
	.p2align	1
	.type	_string.substring,@function
_string.substring:                      # @_string.substring
# %bb.0:
	addi	sp, sp, -32
	sw	ra, 28(sp)                      # 4-byte Folded Spill
	sw	s0, 24(sp)                      # 4-byte Folded Spill
	sw	s1, 20(sp)                      # 4-byte Folded Spill
	sw	s2, 16(sp)                      # 4-byte Folded Spill
	sw	s3, 12(sp)                      # 4-byte Folded Spill
	mv	s3, a1
	mv	s2, a0
	sub	s1, a2, a1
	addi	a0, s1, 1
	call	malloc
	mv	s0, a0
	add	a1, s2, s3
	mv	a2, s1
	call	memcpy
	add	s1, s1, s0
	sb	zero, 0(s1)
	mv	a0, s0
	lw	ra, 28(sp)                      # 4-byte Folded Reload
	lw	s0, 24(sp)                      # 4-byte Folded Reload
	lw	s1, 20(sp)                      # 4-byte Folded Reload
	lw	s2, 16(sp)                      # 4-byte Folded Reload
	lw	s3, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 32
	ret
.Lfunc_end18:
	.size	_string.substring, .Lfunc_end18-_string.substring
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	_string.parseInt                # -- Begin function _string.parseInt
	.p2align	1
	.type	_string.parseInt,@function
_string.parseInt:                       # @_string.parseInt
# %bb.0:
	addi	sp, sp, -16
	sw	ra, 12(sp)                      # 4-byte Folded Spill
	lui	a1, %hi(.L.str.2)
	addi	a1, a1, %lo(.L.str.2)
	addi	a2, sp, 8
	call	sscanf
	lw	a0, 8(sp)
	lw	ra, 12(sp)                      # 4-byte Folded Reload
	addi	sp, sp, 16
	ret
.Lfunc_end19:
	.size	_string.parseInt, .Lfunc_end19-_string.parseInt
                                        # -- End function
	.option	pop
	.option	push
	.option	arch, +a, +c, +m
	.globl	_string.ord                     # -- Begin function _string.ord
	.p2align	1
	.type	_string.ord,@function
_string.ord:                            # @_string.ord
# %bb.0:
	add	a0, a0, a1
	lbu	a0, 0(a0)
	ret
.Lfunc_end20:
	.size	_string.ord, .Lfunc_end20-_string.ord
                                        # -- End function
	.option	pop
	.type	.L.str,@object                  # @.str
	.section	.rodata.str1.1,"aMS",@progbits,1
.L.str:
	.asciz	"%s"
	.size	.L.str, 3

	.type	.L.str.1,@object                # @.str.1
.L.str.1:
	.asciz	"%s\n"
	.size	.L.str.1, 4

	.type	.L.str.2,@object                # @.str.2
.L.str.2:
	.asciz	"%d"
	.size	.L.str.2, 3

	.type	.L.str.3,@object                # @.str.3
.L.str.3:
	.asciz	"%d\n"
	.size	.L.str.3, 4

	.ident	"Ubuntu clang version 18.0.0 (++20230816052613+e87d68ce8f4f-1~exp1~20230816172632.732)"
	.section	".note.GNU-stack","",@progbits
