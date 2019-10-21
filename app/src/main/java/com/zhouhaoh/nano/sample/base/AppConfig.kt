package com.zhouhaoh.nano.sample.base

import android.app.Application
import com.hjq.toast.ToastUtils
import com.zhouhaoh.nano.module.NanoConfig
import com.zhouhaoh.nano.sample.BuildConfig
import retrofit2.Retrofit
import timber.log.Timber

/**
 * ### app通用配置
 * > Created by zhouhaoh  on 2019/10/20.
 */
class AppConfig(private val app: Application) : NanoConfig {
    override val DEBUG = BuildConfig.DEBUG

    override val BASE_URL = "http://gank.io"

    init {
        initSDK()
    }

    override fun initSDK() {
        ToastUtils.init(app)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
//            Timber.plant(CrashReportingTree())
        }
    }

    override fun retrofitOption(builder: Retrofit.Builder) {
//        builder.addConverterFactory(createGson().c)
    }

    override fun okHttpOption(builder: okhttp3.OkHttpClient.Builder) {
//        builder.addInterceptor(TokenInterceptor())
    }

}