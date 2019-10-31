package com.zhouhaoh.nano.sample.data

data class TestResponse(override val errMsg: String?, val objectId: String?) :
    BaseResponseV2(errMsg)