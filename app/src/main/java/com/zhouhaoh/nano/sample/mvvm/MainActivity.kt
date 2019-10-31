package com.zhouhaoh.nano.sample.mvvm

import com.zhouhaoh.nano.ui.base.BaseActivity
import com.zhouhaoh.nano.sample.R
import com.zhouhaoh.nano.sample.base.applyState
import com.zhouhaoh.nano.sample.databinding.ActivityMainBinding
import com.zhouhaoh.nano.ui.ext.bindingView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {
    val viewModel by viewModel<SampleViewModel>()
    private val binding by bindingView<MainActivity, ActivityMainBinding>()
    override val layoutResID: Int
        get() = R.layout.activity_main

    override fun initData() {
        applyState(viewModel)
        binding.viewModel = viewModel
    }
}
