package com.zhouhaoh.nano.sample.data

data class TestResponse(override val message: String?, override val code: Int) :
    BaseResponseV2(message, code)