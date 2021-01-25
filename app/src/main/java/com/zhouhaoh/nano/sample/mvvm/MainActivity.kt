package com.zhouhaoh.nano.sample.mvvm

import com.zhouhaoh.nano.sample.base.applyState
import com.zhouhaoh.nano.ui.base.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    val viewModel by viewModel<SampleViewModel>()
    override fun initData() {
        applyState(viewModel)

    }
}
