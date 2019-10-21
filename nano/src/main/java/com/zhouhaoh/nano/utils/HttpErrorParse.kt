package com.zhouhaoh.nano.utils

import android.net.ParseException
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.zhouhaoh.nano.R
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * #### 解析常见错误，返回中文提示
 * [throwable] 错误类型，返回String int的值
 *
 * > Created by zhouhaoh  on 2019/01/31.
 */
fun parseException(throwable: Throwable): Int {
    return when (throwable) {
        is UnknownHostException -> R.string.net_error
        is ConnectException -> R.string.net_error
        is SocketTimeoutException -> R.string.time_out_error
        is HttpException -> parseCode(throwable)
        is JsonParseException -> R.string.parse_error
        is JsonIOException -> R.string.parse_error
        is ParseException -> R.string.parse_error
        is JSONException -> R.string.parse_error
        else -> R.string.unknown_error
    }
}

/**
 * 解析[httpException] 错误code
 */
private fun parseCode(httpException: HttpException): Int {
    return when (httpException.code()) {
        307 -> R.string.error_307
        404 -> R.string.error_404
        in 403..406 -> R.string.error_403
        408 -> R.string.time_out_error
        in 500..505 -> R.string.error_500
        else -> {
            R.string.error_other
        }
    }
}