package com.zhouhaoh.nano.module

import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import retrofit2.Retrofit

/**
 *项目配置类,由app配置后，通过koin 初始化配置
 * > Created by zhouhaoh  on 19/10/30.
 */
class NanoCore(
    val okHttpOption: OkHttpOption,
    val retrofitOption: RetrofitOption,
    private val customOption: CustomOption
) : KoinComponent {
    private constructor(builder: Builder) : this(
        builder.okHttpOption,
        builder.retrofitOption,
        builder.customOption
    ) {
        customOption.invoke()
    }

    class Builder {
        var okHttpOption: OkHttpOption = {}
        var retrofitOption: RetrofitOption = {}
        var customOption: CustomOption = {}
        fun build() = NanoCore(this)
    }

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }
}

/**
 * okHttp初始化
 */
fun NanoCore.Builder.okHttp(okHttpOption: OkHttpOption) {
    this.okHttpOption = okHttpOption
}

/**
 * retofit初始化
 */
fun NanoCore.Builder.retrofit(retrofitOption: RetrofitOption) {
    this.retrofitOption = retrofitOption
}

/**
 * ### 自定义配置初始化
 * 因为构造方法中invoke，所以是第一个执行的代码块
 */
fun NanoCore.Builder.custom(customOption: CustomOption) {
    this.customOption = customOption
}

typealias OkHttpOption = OkHttpClient.Builder.() -> Unit

typealias RetrofitOption = Retrofit.Builder.() -> Unit

typealias CustomOption = () -> Unit

