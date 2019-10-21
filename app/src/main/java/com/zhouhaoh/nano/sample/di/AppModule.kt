package com.zhouhaoh.nano.sample.di

import android.content.Context
import com.zhouhaoh.nano.sample.base.AppConfig
import com.zhouhaoh.nano.sample.base.AppUIStrategy
import com.zhouhaoh.nano.di.createWebService
import com.zhouhaoh.nano.module.NanoConfig
import com.zhouhaoh.nano.sample.api.ApiService
import com.zhouhaoh.nano.ui.CommonUIStrategy
import org.koin.dsl.module

val appModule = module {
    //项目配置
    single<NanoConfig> { AppConfig(get()) }
    //
    factory<CommonUIStrategy>(override = true) { (windowContext: Context) ->
        AppUIStrategy(windowContext)
    }
    single { createWebService<ApiService>(get(), get(), get()) }
}