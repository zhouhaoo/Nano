package com.zhouhaoh.nano.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.zhouhaoh.nano.core.NanoCore
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * ### Retrofit Remote Web Service dataSource
 * 提供okhttp实例和retrofit
 * > Created by zhouhaoh  on 2019/01/08.
 */
val nanoModule = module {
    single { createGson() }
    single(createdAtStart = true) { createOkHttpClient(get()) }
}

/**
 * Retrofit Remote
 */
fun createOkHttpClient(nanoCore: NanoCore): OkHttpClient {
    return OkHttpClient.Builder().apply(nanoCore.okHttpOption).build()
}

/**
 * 接口services
 */
inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    gson: Gson,
    nanoCore: NanoCore
): T {
    val retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .apply(nanoCore.retrofitOption)
        .build()
    return retrofit.create(T::class.java)
}

fun createGson(): Gson {
    return GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()
}