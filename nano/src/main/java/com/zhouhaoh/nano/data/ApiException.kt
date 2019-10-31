package com.zhouhaoh.nano.data

/**
 *api异常
 */
class ApiException(override val message: String?, val code: Int = 0) : Exception()
