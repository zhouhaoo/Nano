package com.zhouhaoh.nano.sample.data

data class BaseResponse<T>(
    val error: Boolean,
    val results: T?,
    val message: String?
)