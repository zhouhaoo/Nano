package com.zhouhaoh.nano.sample

import android.app.Application
import com.zhouhaoh.nano.di.nanoModule
import com.zhouhaoh.nano.sample.di.appModule
import com.zhouhaoh.nano.sample.di.dataModule
import com.zhouhaoh.nano.sample.di.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


/**
 * ### App 入口
 * 初始化各个模块
 * > Created by zhouhaoh  on 2019/05/24.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(module)
        }
    }
}

val module = listOf(nanoModule, appModule, viewModel, dataModule)