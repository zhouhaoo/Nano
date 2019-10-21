package com.zhouhaoh.nano.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.zhouhaoh.nano.module.NanoConfig
import com.zhouhaoh.nano.ui.CommonUIStrategy
import com.zhouhaoh.nano.ui.DefaultUIStrategy
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * ### Retrofit Remote Web Service dataSource
 * 提供okhttp实例和retrofit
 * > Created by zhouhaoh  on 2019/01/08.
 */
val nanoModule = module {
    single { createGson() }
//    factory <CommonUIStrategy> { DefaultUIStrategy() }
    single(createdAtStart = true) { createOkHttpClient(get()) }
}

fun createOkHttpClient(nano: NanoConfig): OkHttpClient {
    val builder = OkHttpClient.Builder()
    val httpLoggingInterceptor = LoggingInterceptor.Builder()
    httpLoggingInterceptor.apply {
        loggable(nano.DEBUG)
        setLevel(Level.BODY)
        log(Platform.INFO)
        request("Request")
        response("Response")
    }

    nano.okHttpOption(builder)
    return builder
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor.build())
        .build()
}

/**
 * 接口services
 */
inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    gson: Gson,
    nano: NanoConfig
): T {
    val builder = Retrofit.Builder()
    nano.retrofitOption(builder)
    val retrofit = builder
        .baseUrl(nano.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    return retrofit.create(T::class.java)
}

fun createGson(): Gson {
    return GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
}