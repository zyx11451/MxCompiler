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
   addi sp, sp, -224
   sw ra, 220(sp)
   sw s0, 216(sp)
   addi s0, sp, 224
   la %54, @_1
   lw %53, 0(%54)
   mv %1, %53
   li %55, 0
   mv %0, %55
   j _b.3
   
_b.3:
   mv %56, %0
   mv %57, %1
   slt %58, %56, %57
   beqz %58, _b.6
   j _b.4
   
_b.5:
   mv %59, %0
   li %61, 1
   add %60, %59, %61
   mv %0, %60
   j _b.3
   
_b.7:
   mv %62, %52
   mv %2, %62
   j _b.8
   
_b.8:
   j _b.5
   
_b.4:
   mv %63, %0
   li %65, 1
   add %64, %63, %65
   mv %3, %64
   mv %66, %3
   mv %4, %66
   mv %67, %4
   mv %5, %67
   mv %68, %5
   mv %6, %68
   mv %69, %6
   mv %7, %69
   mv %70, %7
   mv %8, %70
   mv %71, %8
   mv %9, %71
   mv %72, %9
   mv %10, %72
   mv %73, %10
   mv %11, %73
   mv %74, %11
   mv %12, %74
   mv %75, %12
   mv %13, %75
   mv %76, %13
   mv %14, %76
   mv %77, %14
   mv %15, %77
   mv %78, %15
   mv %16, %78
   mv %79, %16
   mv %17, %79
   mv %80, %17
   mv %18, %80
   mv %81, %18
   mv %19, %81
   mv %82, %19
   mv %20, %82
   mv %83, %20
   mv %21, %83
   mv %84, %21
   mv %22, %84
   mv %85, %22
   mv %23, %85
   mv %86, %23
   mv %24, %86
   mv %87, %24
   mv %25, %87
   mv %88, %25
   mv %26, %88
   mv %89, %26
   mv %27, %89
   mv %90, %27
   mv %28, %90
   mv %91, %28
   mv %29, %91
   mv %92, %29
   mv %30, %92
   mv %93, %30
   mv %31, %93
   mv %94, %31
   mv %32, %94
   mv %95, %32
   mv %33, %95
   mv %96, %33
   mv %34, %96
   mv %97, %34
   mv %35, %97
   mv %98, %35
   mv %36, %98
   mv %99, %36
   mv %37, %99
   mv %100, %37
   mv %38, %100
   mv %101, %38
   mv %39, %101
   mv %102, %39
   mv %40, %102
   mv %103, %40
   mv %41, %103
   mv %104, %41
   mv %42, %104
   mv %105, %42
   mv %43, %105
   mv %106, %43
   mv %44, %106
   mv %107, %44
   mv %45, %107
   mv %108, %45
   mv %46, %108
   mv %109, %46
   mv %47, %109
   mv %110, %47
   mv %48, %110
   mv %111, %48
   mv %49, %111
   mv %112, %49
   mv %50, %112
   mv %113, %50
   mv %51, %113
   mv %114, %51
   mv %52, %114
   mv %115, %3
   mv %4, %115
   mv %116, %4
   mv %5, %116
   mv %117, %5
   mv %6, %117
   mv %118, %6
   mv %7, %118
   mv %119, %7
   mv %8, %119
   mv %120, %8
   mv %9, %120
   mv %121, %9
   mv %10, %121
   mv %122, %10
   mv %11, %122
   mv %123, %11
   mv %12, %123
   mv %124, %12
   mv %13, %124
   mv %125, %13
   mv %14, %125
   mv %126, %14
   mv %15, %126
   mv %127, %15
   mv %16, %127
   mv %128, %16
   mv %17, %128
   mv %129, %17
   mv %18, %129
   mv %130, %18
   mv %19, %130
   mv %131, %19
   mv %20, %131
   mv %132, %20
   mv %21, %132
   mv %133, %21
   mv %22, %133
   mv %134, %22
   mv %23, %134
   mv %135, %23
   mv %24, %135
   mv %136, %24
   mv %25, %136
   mv %137, %25
   mv %26, %137
   mv %138, %26
   mv %27, %138
   mv %139, %27
   mv %28, %139
   mv %140, %28
   mv %29, %140
   mv %141, %29
   mv %30, %141
   mv %142, %30
   mv %31, %142
   mv %143, %31
   mv %32, %143
   mv %144, %32
   mv %33, %144
   mv %145, %33
   mv %34, %145
   mv %146, %34
   mv %35, %146
   mv %147, %35
   mv %36, %147
   mv %148, %36
   mv %37, %148
   mv %149, %37
   mv %38, %149
   mv %150, %38
   mv %39, %150
   mv %151, %39
   mv %40, %151
   mv %152, %40
   mv %41, %152
   mv %153, %41
   mv %42, %153
   mv %154, %42
   mv %43, %154
   mv %155, %43
   mv %44, %155
   mv %156, %44
   mv %45, %156
   mv %157, %45
   mv %46, %157
   mv %158, %46
   mv %47, %158
   mv %159, %47
   mv %48, %159
   mv %160, %48
   mv %49, %160
   mv %161, %49
   mv %50, %161
   mv %162, %50
   mv %51, %162
   mv %163, %51
   mv %52, %163
   mv %164, %3
   mv %4, %164
   mv %165, %4
   mv %5, %165
   mv %166, %5
   mv %6, %166
   mv %167, %6
   mv %7, %167
   mv %168, %7
   mv %8, %168
   mv %169, %8
   mv %9, %169
   mv %170, %9
   mv %10, %170
   mv %171, %10
   mv %11, %171
   mv %172, %11
   mv %12, %172
   mv %173, %12
   mv %13, %173
   mv %174, %13
   mv %14, %174
   mv %175, %14
   mv %15, %175
   mv %176, %15
   mv %16, %176
   mv %177, %16
   mv %17, %177
   mv %178, %17
   mv %18, %178
   mv %179, %18
   mv %19, %179
   mv %180, %19
   mv %20, %180
   mv %181, %20
   mv %21, %181
   mv %182, %21
   mv %22, %182
   mv %183, %22
   mv %23, %183
   mv %184, %23
   mv %24, %184
   mv %185, %24
   mv %25, %185
   mv %186, %25
   mv %26, %186
   mv %187, %26
   mv %27, %187
   mv %188, %27
   mv %28, %188
   mv %189, %28
   mv %29, %189
   mv %190, %29
   mv %30, %190
   mv %191, %30
   mv %31, %191
   mv %192, %31
   mv %32, %192
   mv %193, %32
   mv %33, %193
   mv %194, %33
   mv %34, %194
   mv %195, %34
   mv %35, %195
   mv %196, %35
   mv %36, %196
   mv %197, %36
   mv %37, %197
   mv %198, %37
   mv %38, %198
   mv %199, %38
   mv %39, %199
   mv %200, %39
   mv %40, %200
   mv %201, %40
   mv %41, %201
   mv %202, %41
   mv %42, %202
   mv %203, %42
   mv %43, %203
   mv %204, %43
   mv %44, %204
   mv %205, %44
   mv %45, %205
   mv %206, %45
   mv %46, %206
   mv %207, %46
   mv %47, %207
   mv %208, %47
   mv %48, %208
   mv %209, %48
   mv %49, %209
   mv %210, %49
   mv %50, %210
   mv %211, %50
   mv %51, %211
   mv %212, %51
   mv %52, %212
   mv %213, %3
   mv %4, %213
   mv %214, %4
   mv %5, %214
   mv %215, %5
   mv %6, %215
   mv %216, %6
   mv %7, %216
   mv %217, %7
   mv %8, %217
   mv %218, %8
   mv %9, %218
   mv %219, %9
   mv %10, %219
   mv %220, %10
   mv %11, %220
   mv %221, %11
   mv %12, %221
   mv %222, %12
   mv %13, %222
   mv %223, %13
   mv %14, %223
   mv %224, %14
   mv %15, %224
   mv %225, %15
   mv %16, %225
   mv %226, %16
   mv %17, %226
   mv %227, %17
   mv %18, %227
   mv %228, %18
   mv %19, %228
   mv %229, %19
   mv %20, %229
   mv %230, %20
   mv %21, %230
   mv %231, %21
   mv %22, %231
   mv %232, %22
   mv %23, %232
   mv %233, %23
   mv %24, %233
   mv %234, %24
   mv %25, %234
   mv %235, %25
   mv %26, %235
   mv %236, %26
   mv %27, %236
   mv %237, %27
   mv %28, %237
   mv %238, %28
   mv %29, %238
   mv %239, %29
   mv %30, %239
   mv %240, %30
   mv %31, %240
   mv %241, %31
   mv %32, %241
   mv %242, %32
   mv %33, %242
   mv %243, %33
   mv %34, %243
   mv %244, %34
   mv %35, %244
   mv %245, %35
   mv %36, %245
   mv %246, %36
   mv %37, %246
   mv %247, %37
   mv %38, %247
   mv %248, %38
   mv %39, %248
   mv %249, %39
   mv %40, %249
   mv %250, %40
   mv %41, %250
   mv %251, %41
   mv %42, %251
   mv %252, %42
   mv %43, %252
   mv %253, %43
   mv %44, %253
   mv %254, %44
   mv %45, %254
   mv %255, %45
   mv %46, %255
   mv %256, %46
   mv %47, %256
   mv %257, %47
   mv %48, %257
   mv %258, %48
   mv %49, %258
   mv %259, %49
   mv %50, %259
   mv %260, %50
   mv %51, %260
   mv %261, %51
   mv %52, %261
   mv %262, %3
   mv %4, %262
   mv %263, %4
   mv %5, %263
   mv %264, %5
   mv %6, %264
   mv %265, %6
   mv %7, %265
   mv %266, %7
   mv %8, %266
   mv %267, %8
   mv %9, %267
   mv %268, %9
   mv %10, %268
   mv %269, %10
   mv %11, %269
   mv %270, %11
   mv %12, %270
   mv %271, %12
   mv %13, %271
   mv %272, %13
   mv %14, %272
   mv %273, %14
   mv %15, %273
   mv %274, %15
   mv %16, %274
   mv %275, %16
   mv %17, %275
   mv %276, %17
   mv %18, %276
   mv %277, %18
   mv %19, %277
   mv %278, %19
   mv %20, %278
   mv %279, %20
   mv %21, %279
   mv %280, %21
   mv %22, %280
   mv %281, %22
   mv %23, %281
   mv %282, %23
   mv %24, %282
   mv %283, %24
   mv %25, %283
   mv %284, %25
   mv %26, %284
   mv %285, %26
   mv %27, %285
   mv %286, %27
   mv %28, %286
   mv %287, %28
   mv %29, %287
   mv %288, %29
   mv %30, %288
   mv %289, %30
   mv %31, %289
   mv %290, %31
   mv %32, %290
   mv %291, %32
   mv %33, %291
   mv %292, %33
   mv %34, %292
   mv %293, %34
   mv %35, %293
   mv %294, %35
   mv %36, %294
   mv %295, %36
   mv %37, %295
   mv %296, %37
   mv %38, %296
   mv %297, %38
   mv %39, %297
   mv %298, %39
   mv %40, %298
   mv %299, %40
   mv %41, %299
   mv %300, %41
   mv %42, %300
   mv %301, %42
   mv %43, %301
   mv %302, %43
   mv %44, %302
   mv %303, %44
   mv %45, %303
   mv %304, %45
   mv %46, %304
   mv %305, %46
   mv %47, %305
   mv %306, %47
   mv %48, %306
   mv %307, %48
   mv %49, %307
   mv %308, %49
   mv %50, %308
   mv %309, %50
   mv %51, %309
   mv %310, %51
   mv %52, %310
   mv %311, %3
   mv %4, %311
   mv %312, %4
   mv %5, %312
   mv %313, %5
   mv %6, %313
   mv %314, %6
   mv %7, %314
   mv %315, %7
   mv %8, %315
   mv %316, %8
   mv %9, %316
   mv %317, %9
   mv %10, %317
   mv %318, %10
   mv %11, %318
   mv %319, %11
   mv %12, %319
   mv %320, %12
   mv %13, %320
   mv %321, %13
   mv %14, %321
   mv %322, %14
   mv %15, %322
   mv %323, %15
   mv %16, %323
   mv %324, %16
   mv %17, %324
   mv %325, %17
   mv %18, %325
   mv %326, %18
   mv %19, %326
   mv %327, %19
   mv %20, %327
   mv %328, %20
   mv %21, %328
   mv %329, %21
   mv %22, %329
   mv %330, %22
   mv %23, %330
   mv %331, %23
   mv %24, %331
   mv %332, %24
   mv %25, %332
   mv %333, %25
   mv %26, %333
   mv %334, %26
   mv %27, %334
   mv %335, %27
   mv %28, %335
   mv %336, %28
   mv %29, %336
   mv %337, %29
   mv %30, %337
   mv %338, %30
   mv %31, %338
   mv %339, %31
   mv %32, %339
   mv %340, %32
   mv %33, %340
   mv %341, %33
   mv %34, %341
   mv %342, %34
   mv %35, %342
   mv %343, %35
   mv %36, %343
   mv %344, %36
   mv %37, %344
   mv %345, %37
   mv %38, %345
   mv %346, %38
   mv %39, %346
   mv %347, %39
   mv %40, %347
   mv %348, %40
   mv %41, %348
   mv %349, %41
   mv %42, %349
   mv %350, %42
   mv %43, %350
   mv %351, %43
   mv %44, %351
   mv %352, %44
   mv %45, %352
   mv %353, %45
   mv %46, %353
   mv %354, %46
   mv %47, %354
   mv %355, %47
   mv %48, %355
   mv %356, %48
   mv %49, %356
   mv %357, %49
   mv %50, %357
   mv %358, %50
   mv %51, %358
   mv %359, %51
   mv %52, %359
   mv %360, %3
   mv %4, %360
   mv %361, %4
   mv %5, %361
   mv %362, %5
   mv %6, %362
   mv %363, %6
   mv %7, %363
   mv %364, %7
   mv %8, %364
   mv %365, %8
   mv %9, %365
   mv %366, %9
   mv %10, %366
   mv %367, %10
   mv %11, %367
   mv %368, %11
   mv %12, %368
   mv %369, %12
   mv %13, %369
   mv %370, %13
   mv %14, %370
   mv %371, %14
   mv %15, %371
   mv %372, %15
   mv %16, %372
   mv %373, %16
   mv %17, %373
   mv %374, %17
   mv %18, %374
   mv %375, %18
   mv %19, %375
   mv %376, %19
   mv %20, %376
   mv %377, %20
   mv %21, %377
   mv %378, %21
   mv %22, %378
   mv %379, %22
   mv %23, %379
   mv %380, %23
   mv %24, %380
   mv %381, %24
   mv %25, %381
   mv %382, %25
   mv %26, %382
   mv %383, %26
   mv %27, %383
   mv %384, %27
   mv %28, %384
   mv %385, %28
   mv %29, %385
   mv %386, %29
   mv %30, %386
   mv %387, %30
   mv %31, %387
   mv %388, %31
   mv %32, %388
   mv %389, %32
   mv %33, %389
   mv %390, %33
   mv %34, %390
   mv %391, %34
   mv %35, %391
   mv %392, %35
   mv %36, %392
   mv %393, %36
   mv %37, %393
   mv %394, %37
   mv %38, %394
   mv %395, %38
   mv %39, %395
   mv %396, %39
   mv %40, %396
   mv %397, %40
   mv %41, %397
   mv %398, %41
   mv %42, %398
   mv %399, %42
   mv %43, %399
   mv %400, %43
   mv %44, %400
   mv %401, %44
   mv %45, %401
   mv %402, %45
   mv %46, %402
   mv %403, %46
   mv %47, %403
   mv %404, %47
   mv %48, %404
   mv %405, %48
   mv %49, %405
   mv %406, %49
   mv %50, %406
   mv %407, %50
   mv %51, %407
   mv %408, %51
   mv %52, %408
   mv %409, %3
   mv %4, %409
   mv %410, %4
   mv %5, %410
   mv %411, %5
   mv %6, %411
   mv %412, %6
   mv %7, %412
   mv %413, %7
   mv %8, %413
   mv %414, %8
   mv %9, %414
   mv %415, %9
   mv %10, %415
   mv %416, %10
   mv %11, %416
   mv %417, %11
   mv %12, %417
   mv %418, %12
   mv %13, %418
   mv %419, %13
   mv %14, %419
   mv %420, %14
   mv %15, %420
   mv %421, %15
   mv %16, %421
   mv %422, %16
   mv %17, %422
   mv %423, %17
   mv %18, %423
   mv %424, %18
   mv %19, %424
   mv %425, %19
   mv %20, %425
   mv %426, %20
   mv %21, %426
   mv %427, %21
   mv %22, %427
   mv %428, %22
   mv %23, %428
   mv %429, %23
   mv %24, %429
   mv %430, %24
   mv %25, %430
   mv %431, %25
   mv %26, %431
   mv %432, %26
   mv %27, %432
   mv %433, %27
   mv %28, %433
   mv %434, %28
   mv %29, %434
   mv %435, %29
   mv %30, %435
   mv %436, %30
   mv %31, %436
   mv %437, %31
   mv %32, %437
   mv %438, %32
   mv %33, %438
   mv %439, %33
   mv %34, %439
   mv %440, %34
   mv %35, %440
   mv %441, %35
   mv %36, %441
   mv %442, %36
   mv %37, %442
   mv %443, %37
   mv %38, %443
   mv %444, %38
   mv %39, %444
   mv %445, %39
   mv %40, %445
   mv %446, %40
   mv %41, %446
   mv %447, %41
   mv %42, %447
   mv %448, %42
   mv %43, %448
   mv %449, %43
   mv %44, %449
   mv %450, %44
   mv %45, %450
   mv %451, %45
   mv %46, %451
   mv %452, %46
   mv %47, %452
   mv %453, %47
   mv %48, %453
   mv %454, %48
   mv %49, %454
   mv %455, %49
   mv %50, %455
   mv %456, %50
   mv %51, %456
   mv %457, %51
   mv %52, %457
   mv %458, %52
   li %460, 0
   xor %461, %458, %460
   snez %459, %461
   beqz %459, _b.8
   j _b.7
   
_b.6:
   mv %462, %2
   mv a0, %462
   lw s0, 216(sp)
   lw ra, 220(sp)
   addi sp, sp, 224
   ret
   

 .globl main
main:
   addi sp, sp, -16
   sw ra, 12(sp)
   sw s0, 8(sp)
   addi s0, sp, 16
   call _initGlobal
   j _b.9
   
_b.9:
   la %464, @_1
   li %465, 100
   sw %465, 0(%464)
   call test
   mv %466, a0
   mv %463, %466
   la %467, @_1
   li %468, 200
   sw %468, 0(%467)
   mv %469, %463
   mv a0, %469
   call printInt
   mv %470, a0
   call test
   mv %471, a0
   mv %472, %463
   add %473, %472, %471
   li %475, 300
   sub %474, %473, %475
   mv a0, %474
   lw s0, 8(sp)
   lw ra, 12(sp)
   addi sp, sp, 16
   ret
   

 .section .data
@_1:
 .word 0

