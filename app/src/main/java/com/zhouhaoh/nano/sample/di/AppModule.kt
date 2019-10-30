package com.zhouhaoh.nano.sample.di

import android.app.Application
import com.hjq.toast.ToastUtils
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import com.zhouhaoh.nano.di.createWebService
import com.zhouhaoh.nano.module.NanoCore
import com.zhouhaoh.nano.module.custom
import com.zhouhaoh.nano.module.okHttp
import com.zhouhaoh.nano.module.retrofit
import com.zhouhaoh.nano.sample.BuildConfig
import com.zhouhaoh.nano.sample.api.ApiService
import okhttp3.internal.platform.Platform
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import timber.log.Timber

val appModule = module {
    single { createNanoCore(androidApplication()) }
    single { createWebService<ApiService>(get(), get(), get()) }
}

/**
 * 项目配置
 */
private fun createNanoCore(androidApplication: Application): NanoCore {
    return NanoCore.build {
        custom {
            if (BuildConfig.DEBUG) {
                Timber.plant(Timber.DebugTree())
            } else {
//            Timber.plant(CrashReportingTree())
            }
            ToastUtils.init(androidApplication)
        }
        okHttp {
            //日志打印
            val httpLoggingInterceptor = LoggingInterceptor.Builder()
            httpLoggingInterceptor.apply {
                loggable(BuildConfig.DEBUG)
                setLevel(Level.BODY)
                log(Platform.INFO)
                request("Request")
                response("Response")
            }
            addInterceptor(httpLoggingInterceptor.build())
            //添加拦截器，例如token加入
//            addInterceptor(TokenInterceptor())
        }
        retrofit {
            baseUrl("http://gank.io")
            //添加转换器
//            addConverterFactory(xxx)
        }
    }
}