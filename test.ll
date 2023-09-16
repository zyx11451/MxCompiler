%struct._array = type { i32, ptr }
declare dso_local i32 @_arraySize(ptr nocapture noundef readonly %0)
declare dso_local ptr @_arrayElement(ptr nocapture noundef readonly %0, i32 noundef %1)
declare dso_local void @print(ptr noundef %0)
declare dso_local void @println(ptr noundef %0)
declare dso_local void @printInt(i32 noundef %0)
declare dso_local void @printlnInt(i32 noundef %0)
declare dso_local noalias ptr @_malloc(i32 noundef %0)
declare dso_local ptr @getString()
declare dso_local i32 @getInt()
declare dso_local noalias ptr @toString(i32 noundef %0)
declare dso_local ptr @_string.plus(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)
declare dso_local zeroext i1 @_string.eq(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)
declare dso_local zeroext i1 @_string.ne(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)
declare dso_local zeroext i1 @_string.l(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)
declare dso_local zeroext i1 @_string.g(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)
declare dso_local zeroext i1 @_string.le(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)
declare dso_local zeroext i1 @_string.ge(ptr nocapture noundef readonly %0, ptr nocapture noundef readonly %1)
declare dso_local i32 @_string.length(ptr nocapture noundef readonly %0)
declare dso_local ptr @_string.substring(ptr noundef %0, i32 noundef %1, i32 noundef %2)
declare dso_local i32 @_string.parseInt(ptr nocapture noundef readonly %0)
declare dso_local i32 @_string.ord(ptr nocapture noundef readonly %0, i32 noundef %1)


@_1 = global i32 0 


define void @_initGlobal(){
_b.1:

ret void
}

define i32 @test(){
_b.2:
%_1 = alloca i32
%_2 = alloca i32
%_3 = load i32,ptr @_1
store i32 %_3,ptr %_2
%_4 = alloca i32
store i32 0,ptr %_1
br label %_b.3

_b.3:
%_5 = load i32,ptr %_1
%_6 = load i32,ptr %_2
%_7 = icmp slt i32 %_5,%_6
br i1 %_7,label %_b.4,label %_b.6

_b.5:
%_8 = load i32,ptr %_1
%_9 = add i32 %_8,1
store i32 %_9,ptr %_1
br label %_b.3

_b.7:
%_456 = load i32,ptr %_59
store i32 %_456,ptr %_4
br label %_b.8

_b.8:
br label %_b.5

_b.4:
%_10 = alloca i32
%_11 = alloca i32
%_12 = alloca i32
%_13 = alloca i32
%_14 = alloca i32
%_15 = alloca i32
%_16 = alloca i32
%_17 = alloca i32
%_18 = alloca i32
%_19 = alloca i32
%_20 = alloca i32
%_21 = alloca i32
%_22 = alloca i32
%_23 = alloca i32
%_24 = alloca i32
%_25 = alloca i32
%_26 = alloca i32
%_27 = alloca i32
%_28 = alloca i32
%_29 = alloca i32
%_30 = alloca i32
%_31 = alloca i32
%_32 = alloca i32
%_33 = alloca i32
%_34 = alloca i32
%_35 = alloca i32
%_36 = alloca i32
%_37 = alloca i32
%_38 = alloca i32
%_39 = alloca i32
%_40 = alloca i32
%_41 = alloca i32
%_42 = alloca i32
%_43 = alloca i32
%_44 = alloca i32
%_45 = alloca i32
%_46 = alloca i32
%_47 = alloca i32
%_48 = alloca i32
%_49 = alloca i32
%_50 = alloca i32
%_51 = alloca i32
%_52 = alloca i32
%_53 = alloca i32
%_54 = alloca i32
%_55 = alloca i32
%_56 = alloca i32
%_57 = alloca i32
%_58 = alloca i32
%_59 = alloca i32
%_60 = load i32,ptr %_1
%_61 = add i32 %_60,1
store i32 %_61,ptr %_10
%_62 = load i32,ptr %_10
store i32 %_62,ptr %_11
%_63 = load i32,ptr %_11
store i32 %_63,ptr %_12
%_64 = load i32,ptr %_12
store i32 %_64,ptr %_13
%_65 = load i32,ptr %_13
store i32 %_65,ptr %_14
%_66 = load i32,ptr %_14
store i32 %_66,ptr %_15
%_67 = load i32,ptr %_15
store i32 %_67,ptr %_16
%_68 = load i32,ptr %_16
store i32 %_68,ptr %_17
%_69 = load i32,ptr %_17
store i32 %_69,ptr %_18
%_70 = load i32,ptr %_18
store i32 %_70,ptr %_19
%_71 = load i32,ptr %_19
store i32 %_71,ptr %_20
%_72 = load i32,ptr %_20
store i32 %_72,ptr %_21
%_73 = load i32,ptr %_21
store i32 %_73,ptr %_22
%_74 = load i32,ptr %_22
store i32 %_74,ptr %_23
%_75 = load i32,ptr %_23
store i32 %_75,ptr %_24
%_76 = load i32,ptr %_24
store i32 %_76,ptr %_25
%_77 = load i32,ptr %_25
store i32 %_77,ptr %_26
%_78 = load i32,ptr %_26
store i32 %_78,ptr %_27
%_79 = load i32,ptr %_27
store i32 %_79,ptr %_28
%_80 = load i32,ptr %_28
store i32 %_80,ptr %_29
%_81 = load i32,ptr %_29
store i32 %_81,ptr %_30
%_82 = load i32,ptr %_30
store i32 %_82,ptr %_31
%_83 = load i32,ptr %_31
store i32 %_83,ptr %_32
%_84 = load i32,ptr %_32
store i32 %_84,ptr %_33
%_85 = load i32,ptr %_33
store i32 %_85,ptr %_34
%_86 = load i32,ptr %_34
store i32 %_86,ptr %_35
%_87 = load i32,ptr %_35
store i32 %_87,ptr %_36
%_88 = load i32,ptr %_36
store i32 %_88,ptr %_37
%_89 = load i32,ptr %_37
store i32 %_89,ptr %_38
%_90 = load i32,ptr %_38
store i32 %_90,ptr %_39
%_91 = load i32,ptr %_39
store i32 %_91,ptr %_40
%_92 = load i32,ptr %_40
store i32 %_92,ptr %_41
%_93 = load i32,ptr %_41
store i32 %_93,ptr %_42
%_94 = load i32,ptr %_42
store i32 %_94,ptr %_43
%_95 = load i32,ptr %_43
store i32 %_95,ptr %_44
%_96 = load i32,ptr %_44
store i32 %_96,ptr %_45
%_97 = load i32,ptr %_45
store i32 %_97,ptr %_46
%_98 = load i32,ptr %_46
store i32 %_98,ptr %_47
%_99 = load i32,ptr %_47
store i32 %_99,ptr %_48
%_100 = load i32,ptr %_48
store i32 %_100,ptr %_49
%_101 = load i32,ptr %_49
store i32 %_101,ptr %_50
%_102 = load i32,ptr %_50
store i32 %_102,ptr %_51
%_103 = load i32,ptr %_51
store i32 %_103,ptr %_52
%_104 = load i32,ptr %_52
store i32 %_104,ptr %_53
%_105 = load i32,ptr %_53
store i32 %_105,ptr %_54
%_106 = load i32,ptr %_54
store i32 %_106,ptr %_55
%_107 = load i32,ptr %_55
store i32 %_107,ptr %_56
%_108 = load i32,ptr %_56
store i32 %_108,ptr %_57
%_109 = load i32,ptr %_57
store i32 %_109,ptr %_58
%_110 = load i32,ptr %_58
store i32 %_110,ptr %_59
%_111 = load i32,ptr %_10
store i32 %_111,ptr %_11
%_112 = load i32,ptr %_11
store i32 %_112,ptr %_12
%_113 = load i32,ptr %_12
store i32 %_113,ptr %_13
%_114 = load i32,ptr %_13
store i32 %_114,ptr %_14
%_115 = load i32,ptr %_14
store i32 %_115,ptr %_15
%_116 = load i32,ptr %_15
store i32 %_116,ptr %_16
%_117 = load i32,ptr %_16
store i32 %_117,ptr %_17
%_118 = load i32,ptr %_17
store i32 %_118,ptr %_18
%_119 = load i32,ptr %_18
store i32 %_119,ptr %_19
%_120 = load i32,ptr %_19
store i32 %_120,ptr %_20
%_121 = load i32,ptr %_20
store i32 %_121,ptr %_21
%_122 = load i32,ptr %_21
store i32 %_122,ptr %_22
%_123 = load i32,ptr %_22
store i32 %_123,ptr %_23
%_124 = load i32,ptr %_23
store i32 %_124,ptr %_24
%_125 = load i32,ptr %_24
store i32 %_125,ptr %_25
%_126 = load i32,ptr %_25
store i32 %_126,ptr %_26
%_127 = load i32,ptr %_26
store i32 %_127,ptr %_27
%_128 = load i32,ptr %_27
store i32 %_128,ptr %_28
%_129 = load i32,ptr %_28
store i32 %_129,ptr %_29
%_130 = load i32,ptr %_29
store i32 %_130,ptr %_30
%_131 = load i32,ptr %_30
store i32 %_131,ptr %_31
%_132 = load i32,ptr %_31
store i32 %_132,ptr %_32
%_133 = load i32,ptr %_32
store i32 %_133,ptr %_33
%_134 = load i32,ptr %_33
store i32 %_134,ptr %_34
%_135 = load i32,ptr %_34
store i32 %_135,ptr %_35
%_136 = load i32,ptr %_35
store i32 %_136,ptr %_36
%_137 = load i32,ptr %_36
store i32 %_137,ptr %_37
%_138 = load i32,ptr %_37
store i32 %_138,ptr %_38
%_139 = load i32,ptr %_38
store i32 %_139,ptr %_39
%_140 = load i32,ptr %_39
store i32 %_140,ptr %_40
%_141 = load i32,ptr %_40
store i32 %_141,ptr %_41
%_142 = load i32,ptr %_41
store i32 %_142,ptr %_42
%_143 = load i32,ptr %_42
store i32 %_143,ptr %_43
%_144 = load i32,ptr %_43
store i32 %_144,ptr %_44
%_145 = load i32,ptr %_44
store i32 %_145,ptr %_45
%_146 = load i32,ptr %_45
store i32 %_146,ptr %_46
%_147 = load i32,ptr %_46
store i32 %_147,ptr %_47
%_148 = load i32,ptr %_47
store i32 %_148,ptr %_48
%_149 = load i32,ptr %_48
store i32 %_149,ptr %_49
%_150 = load i32,ptr %_49
store i32 %_150,ptr %_50
%_151 = load i32,ptr %_50
store i32 %_151,ptr %_51
%_152 = load i32,ptr %_51
store i32 %_152,ptr %_52
%_153 = load i32,ptr %_52
store i32 %_153,ptr %_53
%_154 = load i32,ptr %_53
store i32 %_154,ptr %_54
%_155 = load i32,ptr %_54
store i32 %_155,ptr %_55
%_156 = load i32,ptr %_55
store i32 %_156,ptr %_56
%_157 = load i32,ptr %_56
store i32 %_157,ptr %_57
%_158 = load i32,ptr %_57
store i32 %_158,ptr %_58
%_159 = load i32,ptr %_58
store i32 %_159,ptr %_59
%_160 = load i32,ptr %_10
store i32 %_160,ptr %_11
%_161 = load i32,ptr %_11
store i32 %_161,ptr %_12
%_162 = load i32,ptr %_12
store i32 %_162,ptr %_13
%_163 = load i32,ptr %_13
store i32 %_163,ptr %_14
%_164 = load i32,ptr %_14
store i32 %_164,ptr %_15
%_165 = load i32,ptr %_15
store i32 %_165,ptr %_16
%_166 = load i32,ptr %_16
store i32 %_166,ptr %_17
%_167 = load i32,ptr %_17
store i32 %_167,ptr %_18
%_168 = load i32,ptr %_18
store i32 %_168,ptr %_19
%_169 = load i32,ptr %_19
store i32 %_169,ptr %_20
%_170 = load i32,ptr %_20
store i32 %_170,ptr %_21
%_171 = load i32,ptr %_21
store i32 %_171,ptr %_22
%_172 = load i32,ptr %_22
store i32 %_172,ptr %_23
%_173 = load i32,ptr %_23
store i32 %_173,ptr %_24
%_174 = load i32,ptr %_24
store i32 %_174,ptr %_25
%_175 = load i32,ptr %_25
store i32 %_175,ptr %_26
%_176 = load i32,ptr %_26
store i32 %_176,ptr %_27
%_177 = load i32,ptr %_27
store i32 %_177,ptr %_28
%_178 = load i32,ptr %_28
store i32 %_178,ptr %_29
%_179 = load i32,ptr %_29
store i32 %_179,ptr %_30
%_180 = load i32,ptr %_30
store i32 %_180,ptr %_31
%_181 = load i32,ptr %_31
store i32 %_181,ptr %_32
%_182 = load i32,ptr %_32
store i32 %_182,ptr %_33
%_183 = load i32,ptr %_33
store i32 %_183,ptr %_34
%_184 = load i32,ptr %_34
store i32 %_184,ptr %_35
%_185 = load i32,ptr %_35
store i32 %_185,ptr %_36
%_186 = load i32,ptr %_36
store i32 %_186,ptr %_37
%_187 = load i32,ptr %_37
store i32 %_187,ptr %_38
%_188 = load i32,ptr %_38
store i32 %_188,ptr %_39
%_189 = load i32,ptr %_39
store i32 %_189,ptr %_40
%_190 = load i32,ptr %_40
store i32 %_190,ptr %_41
%_191 = load i32,ptr %_41
store i32 %_191,ptr %_42
%_192 = load i32,ptr %_42
store i32 %_192,ptr %_43
%_193 = load i32,ptr %_43
store i32 %_193,ptr %_44
%_194 = load i32,ptr %_44
store i32 %_194,ptr %_45
%_195 = load i32,ptr %_45
store i32 %_195,ptr %_46
%_196 = load i32,ptr %_46
store i32 %_196,ptr %_47
%_197 = load i32,ptr %_47
store i32 %_197,ptr %_48
%_198 = load i32,ptr %_48
store i32 %_198,ptr %_49
%_199 = load i32,ptr %_49
store i32 %_199,ptr %_50
%_200 = load i32,ptr %_50
store i32 %_200,ptr %_51
%_201 = load i32,ptr %_51
store i32 %_201,ptr %_52
%_202 = load i32,ptr %_52
store i32 %_202,ptr %_53
%_203 = load i32,ptr %_53
store i32 %_203,ptr %_54
%_204 = load i32,ptr %_54
store i32 %_204,ptr %_55
%_205 = load i32,ptr %_55
store i32 %_205,ptr %_56
%_206 = load i32,ptr %_56
store i32 %_206,ptr %_57
%_207 = load i32,ptr %_57
store i32 %_207,ptr %_58
%_208 = load i32,ptr %_58
store i32 %_208,ptr %_59
%_209 = load i32,ptr %_10
store i32 %_209,ptr %_11
%_210 = load i32,ptr %_11
store i32 %_210,ptr %_12
%_211 = load i32,ptr %_12
store i32 %_211,ptr %_13
%_212 = load i32,ptr %_13
store i32 %_212,ptr %_14
%_213 = load i32,ptr %_14
store i32 %_213,ptr %_15
%_214 = load i32,ptr %_15
store i32 %_214,ptr %_16
%_215 = load i32,ptr %_16
store i32 %_215,ptr %_17
%_216 = load i32,ptr %_17
store i32 %_216,ptr %_18
%_217 = load i32,ptr %_18
store i32 %_217,ptr %_19
%_218 = load i32,ptr %_19
store i32 %_218,ptr %_20
%_219 = load i32,ptr %_20
store i32 %_219,ptr %_21
%_220 = load i32,ptr %_21
store i32 %_220,ptr %_22
%_221 = load i32,ptr %_22
store i32 %_221,ptr %_23
%_222 = load i32,ptr %_23
store i32 %_222,ptr %_24
%_223 = load i32,ptr %_24
store i32 %_223,ptr %_25
%_224 = load i32,ptr %_25
store i32 %_224,ptr %_26
%_225 = load i32,ptr %_26
store i32 %_225,ptr %_27
%_226 = load i32,ptr %_27
store i32 %_226,ptr %_28
%_227 = load i32,ptr %_28
store i32 %_227,ptr %_29
%_228 = load i32,ptr %_29
store i32 %_228,ptr %_30
%_229 = load i32,ptr %_30
store i32 %_229,ptr %_31
%_230 = load i32,ptr %_31
store i32 %_230,ptr %_32
%_231 = load i32,ptr %_32
store i32 %_231,ptr %_33
%_232 = load i32,ptr %_33
store i32 %_232,ptr %_34
%_233 = load i32,ptr %_34
store i32 %_233,ptr %_35
%_234 = load i32,ptr %_35
store i32 %_234,ptr %_36
%_235 = load i32,ptr %_36
store i32 %_235,ptr %_37
%_236 = load i32,ptr %_37
store i32 %_236,ptr %_38
%_237 = load i32,ptr %_38
store i32 %_237,ptr %_39
%_238 = load i32,ptr %_39
store i32 %_238,ptr %_40
%_239 = load i32,ptr %_40
store i32 %_239,ptr %_41
%_240 = load i32,ptr %_41
store i32 %_240,ptr %_42
%_241 = load i32,ptr %_42
store i32 %_241,ptr %_43
%_242 = load i32,ptr %_43
store i32 %_242,ptr %_44
%_243 = load i32,ptr %_44
store i32 %_243,ptr %_45
%_244 = load i32,ptr %_45
store i32 %_244,ptr %_46
%_245 = load i32,ptr %_46
store i32 %_245,ptr %_47
%_246 = load i32,ptr %_47
store i32 %_246,ptr %_48
%_247 = load i32,ptr %_48
store i32 %_247,ptr %_49
%_248 = load i32,ptr %_49
store i32 %_248,ptr %_50
%_249 = load i32,ptr %_50
store i32 %_249,ptr %_51
%_250 = load i32,ptr %_51
store i32 %_250,ptr %_52
%_251 = load i32,ptr %_52
store i32 %_251,ptr %_53
%_252 = load i32,ptr %_53
store i32 %_252,ptr %_54
%_253 = load i32,ptr %_54
store i32 %_253,ptr %_55
%_254 = load i32,ptr %_55
store i32 %_254,ptr %_56
%_255 = load i32,ptr %_56
store i32 %_255,ptr %_57
%_256 = load i32,ptr %_57
store i32 %_256,ptr %_58
%_257 = load i32,ptr %_58
store i32 %_257,ptr %_59
%_258 = load i32,ptr %_10
store i32 %_258,ptr %_11
%_259 = load i32,ptr %_11
store i32 %_259,ptr %_12
%_260 = load i32,ptr %_12
store i32 %_260,ptr %_13
%_261 = load i32,ptr %_13
store i32 %_261,ptr %_14
%_262 = load i32,ptr %_14
store i32 %_262,ptr %_15
%_263 = load i32,ptr %_15
store i32 %_263,ptr %_16
%_264 = load i32,ptr %_16
store i32 %_264,ptr %_17
%_265 = load i32,ptr %_17
store i32 %_265,ptr %_18
%_266 = load i32,ptr %_18
store i32 %_266,ptr %_19
%_267 = load i32,ptr %_19
store i32 %_267,ptr %_20
%_268 = load i32,ptr %_20
store i32 %_268,ptr %_21
%_269 = load i32,ptr %_21
store i32 %_269,ptr %_22
%_270 = load i32,ptr %_22
store i32 %_270,ptr %_23
%_271 = load i32,ptr %_23
store i32 %_271,ptr %_24
%_272 = load i32,ptr %_24
store i32 %_272,ptr %_25
%_273 = load i32,ptr %_25
store i32 %_273,ptr %_26
%_274 = load i32,ptr %_26
store i32 %_274,ptr %_27
%_275 = load i32,ptr %_27
store i32 %_275,ptr %_28
%_276 = load i32,ptr %_28
store i32 %_276,ptr %_29
%_277 = load i32,ptr %_29
store i32 %_277,ptr %_30
%_278 = load i32,ptr %_30
store i32 %_278,ptr %_31
%_279 = load i32,ptr %_31
store i32 %_279,ptr %_32
%_280 = load i32,ptr %_32
store i32 %_280,ptr %_33
%_281 = load i32,ptr %_33
store i32 %_281,ptr %_34
%_282 = load i32,ptr %_34
store i32 %_282,ptr %_35
%_283 = load i32,ptr %_35
store i32 %_283,ptr %_36
%_284 = load i32,ptr %_36
store i32 %_284,ptr %_37
%_285 = load i32,ptr %_37
store i32 %_285,ptr %_38
%_286 = load i32,ptr %_38
store i32 %_286,ptr %_39
%_287 = load i32,ptr %_39
store i32 %_287,ptr %_40
%_288 = load i32,ptr %_40
store i32 %_288,ptr %_41
%_289 = load i32,ptr %_41
store i32 %_289,ptr %_42
%_290 = load i32,ptr %_42
store i32 %_290,ptr %_43
%_291 = load i32,ptr %_43
store i32 %_291,ptr %_44
%_292 = load i32,ptr %_44
store i32 %_292,ptr %_45
%_293 = load i32,ptr %_45
store i32 %_293,ptr %_46
%_294 = load i32,ptr %_46
store i32 %_294,ptr %_47
%_295 = load i32,ptr %_47
store i32 %_295,ptr %_48
%_296 = load i32,ptr %_48
store i32 %_296,ptr %_49
%_297 = load i32,ptr %_49
store i32 %_297,ptr %_50
%_298 = load i32,ptr %_50
store i32 %_298,ptr %_51
%_299 = load i32,ptr %_51
store i32 %_299,ptr %_52
%_300 = load i32,ptr %_52
store i32 %_300,ptr %_53
%_301 = load i32,ptr %_53
store i32 %_301,ptr %_54
%_302 = load i32,ptr %_54
store i32 %_302,ptr %_55
%_303 = load i32,ptr %_55
store i32 %_303,ptr %_56
%_304 = load i32,ptr %_56
store i32 %_304,ptr %_57
%_305 = load i32,ptr %_57
store i32 %_305,ptr %_58
%_306 = load i32,ptr %_58
store i32 %_306,ptr %_59
%_307 = load i32,ptr %_10
store i32 %_307,ptr %_11
%_308 = load i32,ptr %_11
store i32 %_308,ptr %_12
%_309 = load i32,ptr %_12
store i32 %_309,ptr %_13
%_310 = load i32,ptr %_13
store i32 %_310,ptr %_14
%_311 = load i32,ptr %_14
store i32 %_311,ptr %_15
%_312 = load i32,ptr %_15
store i32 %_312,ptr %_16
%_313 = load i32,ptr %_16
store i32 %_313,ptr %_17
%_314 = load i32,ptr %_17
store i32 %_314,ptr %_18
%_315 = load i32,ptr %_18
store i32 %_315,ptr %_19
%_316 = load i32,ptr %_19
store i32 %_316,ptr %_20
%_317 = load i32,ptr %_20
store i32 %_317,ptr %_21
%_318 = load i32,ptr %_21
store i32 %_318,ptr %_22
%_319 = load i32,ptr %_22
store i32 %_319,ptr %_23
%_320 = load i32,ptr %_23
store i32 %_320,ptr %_24
%_321 = load i32,ptr %_24
store i32 %_321,ptr %_25
%_322 = load i32,ptr %_25
store i32 %_322,ptr %_26
%_323 = load i32,ptr %_26
store i32 %_323,ptr %_27
%_324 = load i32,ptr %_27
store i32 %_324,ptr %_28
%_325 = load i32,ptr %_28
store i32 %_325,ptr %_29
%_326 = load i32,ptr %_29
store i32 %_326,ptr %_30
%_327 = load i32,ptr %_30
store i32 %_327,ptr %_31
%_328 = load i32,ptr %_31
store i32 %_328,ptr %_32
%_329 = load i32,ptr %_32
store i32 %_329,ptr %_33
%_330 = load i32,ptr %_33
store i32 %_330,ptr %_34
%_331 = load i32,ptr %_34
store i32 %_331,ptr %_35
%_332 = load i32,ptr %_35
store i32 %_332,ptr %_36
%_333 = load i32,ptr %_36
store i32 %_333,ptr %_37
%_334 = load i32,ptr %_37
store i32 %_334,ptr %_38
%_335 = load i32,ptr %_38
store i32 %_335,ptr %_39
%_336 = load i32,ptr %_39
store i32 %_336,ptr %_40
%_337 = load i32,ptr %_40
store i32 %_337,ptr %_41
%_338 = load i32,ptr %_41
store i32 %_338,ptr %_42
%_339 = load i32,ptr %_42
store i32 %_339,ptr %_43
%_340 = load i32,ptr %_43
store i32 %_340,ptr %_44
%_341 = load i32,ptr %_44
store i32 %_341,ptr %_45
%_342 = load i32,ptr %_45
store i32 %_342,ptr %_46
%_343 = load i32,ptr %_46
store i32 %_343,ptr %_47
%_344 = load i32,ptr %_47
store i32 %_344,ptr %_48
%_345 = load i32,ptr %_48
store i32 %_345,ptr %_49
%_346 = load i32,ptr %_49
store i32 %_346,ptr %_50
%_347 = load i32,ptr %_50
store i32 %_347,ptr %_51
%_348 = load i32,ptr %_51
store i32 %_348,ptr %_52
%_349 = load i32,ptr %_52
store i32 %_349,ptr %_53
%_350 = load i32,ptr %_53
store i32 %_350,ptr %_54
%_351 = load i32,ptr %_54
store i32 %_351,ptr %_55
%_352 = load i32,ptr %_55
store i32 %_352,ptr %_56
%_353 = load i32,ptr %_56
store i32 %_353,ptr %_57
%_354 = load i32,ptr %_57
store i32 %_354,ptr %_58
%_355 = load i32,ptr %_58
store i32 %_355,ptr %_59
%_356 = load i32,ptr %_10
store i32 %_356,ptr %_11
%_357 = load i32,ptr %_11
store i32 %_357,ptr %_12
%_358 = load i32,ptr %_12
store i32 %_358,ptr %_13
%_359 = load i32,ptr %_13
store i32 %_359,ptr %_14
%_360 = load i32,ptr %_14
store i32 %_360,ptr %_15
%_361 = load i32,ptr %_15
store i32 %_361,ptr %_16
%_362 = load i32,ptr %_16
store i32 %_362,ptr %_17
%_363 = load i32,ptr %_17
store i32 %_363,ptr %_18
%_364 = load i32,ptr %_18
store i32 %_364,ptr %_19
%_365 = load i32,ptr %_19
store i32 %_365,ptr %_20
%_366 = load i32,ptr %_20
store i32 %_366,ptr %_21
%_367 = load i32,ptr %_21
store i32 %_367,ptr %_22
%_368 = load i32,ptr %_22
store i32 %_368,ptr %_23
%_369 = load i32,ptr %_23
store i32 %_369,ptr %_24
%_370 = load i32,ptr %_24
store i32 %_370,ptr %_25
%_371 = load i32,ptr %_25
store i32 %_371,ptr %_26
%_372 = load i32,ptr %_26
store i32 %_372,ptr %_27
%_373 = load i32,ptr %_27
store i32 %_373,ptr %_28
%_374 = load i32,ptr %_28
store i32 %_374,ptr %_29
%_375 = load i32,ptr %_29
store i32 %_375,ptr %_30
%_376 = load i32,ptr %_30
store i32 %_376,ptr %_31
%_377 = load i32,ptr %_31
store i32 %_377,ptr %_32
%_378 = load i32,ptr %_32
store i32 %_378,ptr %_33
%_379 = load i32,ptr %_33
store i32 %_379,ptr %_34
%_380 = load i32,ptr %_34
store i32 %_380,ptr %_35
%_381 = load i32,ptr %_35
store i32 %_381,ptr %_36
%_382 = load i32,ptr %_36
store i32 %_382,ptr %_37
%_383 = load i32,ptr %_37
store i32 %_383,ptr %_38
%_384 = load i32,ptr %_38
store i32 %_384,ptr %_39
%_385 = load i32,ptr %_39
store i32 %_385,ptr %_40
%_386 = load i32,ptr %_40
store i32 %_386,ptr %_41
%_387 = load i32,ptr %_41
store i32 %_387,ptr %_42
%_388 = load i32,ptr %_42
store i32 %_388,ptr %_43
%_389 = load i32,ptr %_43
store i32 %_389,ptr %_44
%_390 = load i32,ptr %_44
store i32 %_390,ptr %_45
%_391 = load i32,ptr %_45
store i32 %_391,ptr %_46
%_392 = load i32,ptr %_46
store i32 %_392,ptr %_47
%_393 = load i32,ptr %_47
store i32 %_393,ptr %_48
%_394 = load i32,ptr %_48
store i32 %_394,ptr %_49
%_395 = load i32,ptr %_49
store i32 %_395,ptr %_50
%_396 = load i32,ptr %_50
store i32 %_396,ptr %_51
%_397 = load i32,ptr %_51
store i32 %_397,ptr %_52
%_398 = load i32,ptr %_52
store i32 %_398,ptr %_53
%_399 = load i32,ptr %_53
store i32 %_399,ptr %_54
%_400 = load i32,ptr %_54
store i32 %_400,ptr %_55
%_401 = load i32,ptr %_55
store i32 %_401,ptr %_56
%_402 = load i32,ptr %_56
store i32 %_402,ptr %_57
%_403 = load i32,ptr %_57
store i32 %_403,ptr %_58
%_404 = load i32,ptr %_58
store i32 %_404,ptr %_59
%_405 = load i32,ptr %_10
store i32 %_405,ptr %_11
%_406 = load i32,ptr %_11
store i32 %_406,ptr %_12
%_407 = load i32,ptr %_12
store i32 %_407,ptr %_13
%_408 = load i32,ptr %_13
store i32 %_408,ptr %_14
%_409 = load i32,ptr %_14
store i32 %_409,ptr %_15
%_410 = load i32,ptr %_15
store i32 %_410,ptr %_16
%_411 = load i32,ptr %_16
store i32 %_411,ptr %_17
%_412 = load i32,ptr %_17
store i32 %_412,ptr %_18
%_413 = load i32,ptr %_18
store i32 %_413,ptr %_19
%_414 = load i32,ptr %_19
store i32 %_414,ptr %_20
%_415 = load i32,ptr %_20
store i32 %_415,ptr %_21
%_416 = load i32,ptr %_21
store i32 %_416,ptr %_22
%_417 = load i32,ptr %_22
store i32 %_417,ptr %_23
%_418 = load i32,ptr %_23
store i32 %_418,ptr %_24
%_419 = load i32,ptr %_24
store i32 %_419,ptr %_25
%_420 = load i32,ptr %_25
store i32 %_420,ptr %_26
%_421 = load i32,ptr %_26
store i32 %_421,ptr %_27
%_422 = load i32,ptr %_27
store i32 %_422,ptr %_28
%_423 = load i32,ptr %_28
store i32 %_423,ptr %_29
%_424 = load i32,ptr %_29
store i32 %_424,ptr %_30
%_425 = load i32,ptr %_30
store i32 %_425,ptr %_31
%_426 = load i32,ptr %_31
store i32 %_426,ptr %_32
%_427 = load i32,ptr %_32
store i32 %_427,ptr %_33
%_428 = load i32,ptr %_33
store i32 %_428,ptr %_34
%_429 = load i32,ptr %_34
store i32 %_429,ptr %_35
%_430 = load i32,ptr %_35
store i32 %_430,ptr %_36
%_431 = load i32,ptr %_36
store i32 %_431,ptr %_37
%_432 = load i32,ptr %_37
store i32 %_432,ptr %_38
%_433 = load i32,ptr %_38
store i32 %_433,ptr %_39
%_434 = load i32,ptr %_39
store i32 %_434,ptr %_40
%_435 = load i32,ptr %_40
store i32 %_435,ptr %_41
%_436 = load i32,ptr %_41
store i32 %_436,ptr %_42
%_437 = load i32,ptr %_42
store i32 %_437,ptr %_43
%_438 = load i32,ptr %_43
store i32 %_438,ptr %_44
%_439 = load i32,ptr %_44
store i32 %_439,ptr %_45
%_440 = load i32,ptr %_45
store i32 %_440,ptr %_46
%_441 = load i32,ptr %_46
store i32 %_441,ptr %_47
%_442 = load i32,ptr %_47
store i32 %_442,ptr %_48
%_443 = load i32,ptr %_48
store i32 %_443,ptr %_49
%_444 = load i32,ptr %_49
store i32 %_444,ptr %_50
%_445 = load i32,ptr %_50
store i32 %_445,ptr %_51
%_446 = load i32,ptr %_51
store i32 %_446,ptr %_52
%_447 = load i32,ptr %_52
store i32 %_447,ptr %_53
%_448 = load i32,ptr %_53
store i32 %_448,ptr %_54
%_449 = load i32,ptr %_54
store i32 %_449,ptr %_55
%_450 = load i32,ptr %_55
store i32 %_450,ptr %_56
%_451 = load i32,ptr %_56
store i32 %_451,ptr %_57
%_452 = load i32,ptr %_57
store i32 %_452,ptr %_58
%_453 = load i32,ptr %_58
store i32 %_453,ptr %_59
%_454 = load i32,ptr %_59
%_455 = icmp ne i32 %_454,0
br i1 %_455,label %_b.7,label %_b.8

_b.6:
%_457 = load i32,ptr %_4
ret i32 %_457

}
define i32 @main(){
call void @_initGlobal()
br label %_b.9
_b.9:
store i32 100,ptr @_1
%_458 = alloca i32
%_459 = call i32 @test()
store i32 %_459,ptr %_458
store i32 200,ptr @_1
%_460 = load i32,ptr %_458
%_461 = call ptr @printInt(i32 %_460)
%_462 = call i32 @test()
%_463 = load i32,ptr %_458
%_464 = add i32 %_463,%_462
%_465 = sub i32 %_464,300
ret i32 %_465

}
