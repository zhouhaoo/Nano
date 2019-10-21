package com.zhouhaoh.nano.module

import com.zhouhaoh.nano.ui.DefaultUIStrategy
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Retrofit

/**
 *项目配置类,由app配置后，通过koin在Application初始化
 * > Created by zhouhaoh  on 19/10/16.
 */
interface NanoConfig : KoinComponent {
    /**
     * 网络请求BaseUrl
     */
    val BASE_URL: String

    val DEBUG: Boolean

    fun initSDK()

    /**
     * 配置okhttp
     */
    fun okHttpOption(builder: OkHttpClient.Builder)

    /**
     * 配置retrofit
     */
    fun retrofitOption(builder: Retrofit.Builder)

}