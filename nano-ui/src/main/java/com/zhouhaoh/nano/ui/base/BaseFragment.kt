package com.zhouhaoh.nano.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.zhouhaoh.nano.state.LoadingState
import com.zhouhaoh.nano.ui.strategy.CommonUIStrategy
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 *BaseFragment
 * > Created by zhouhaoh  on 19/10/16.
 */
abstract class BaseFragment : Fragment(), IView {

    private val uiStrategy by inject<CommonUIStrategy> {
        parametersOf(this.activity)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lifecycle.addObserver(uiStrategy)
        initData(savedInstanceState)
    }

    /**
     * 初始化
     */
    abstract fun initData(savedInstanceState: Bundle?)

    override fun showLoading(loadingState: LoadingState) {
        uiStrategy.showLoading(loadingState)
    }

    override fun dismissLoading() {
        uiStrategy.dismissLoading()
    }

    override fun toast(message: String?, resId: Int) {
        uiStrategy.toast(message, resId)
    }
}