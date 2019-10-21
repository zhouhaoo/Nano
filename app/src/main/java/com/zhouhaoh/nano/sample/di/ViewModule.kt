package com.zhouhaoh.nano.sample.di

import com.zhouhaoh.nano.sample.mvvm.SampleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModel = module {
    viewModel {
        SampleViewModel(get())
    }
}