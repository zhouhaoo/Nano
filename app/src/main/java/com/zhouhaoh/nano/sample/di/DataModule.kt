package com.zhouhaoh.nano.sample.di

import com.zhouhaoh.nano.sample.mvvm.SampleRepository
import org.koin.dsl.module

val dataModule = module {
    single {
        SampleRepository(get())
    }
}