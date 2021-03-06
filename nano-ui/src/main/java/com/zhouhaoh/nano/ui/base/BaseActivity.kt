package com.zhouhaoh.nano.ui.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.zhouhaoh.nano.state.LoadingState
import com.zhouhaoh.nano.ui.strategy.CommonUIStrategy
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 *BaseActivity
 * > Created by zhouhaoh  on 19/10/16.
 */
abstract class BaseActivity : AppCompatActivity(), IView {

    private val uiStrategy by inject<CommonUIStrategy> {
        parametersOf(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(uiStrategy)
        initView(savedInstanceState)
        initData()
    }

    /**
     *初始化View
     */
    protected fun initView(savedInstanceState: Bundle?) {

    }

    /**
     *初始化数据
     */
    abstract fun initData()

    override fun showLoading(loadingState: LoadingState) {
        uiStrategy.showLoading(loadingState)
    }

    override fun dismissLoading() {
        uiStrategy.dismissLoading()
    }

    override fun toast(message: String?, resId: Int) {
        uiStrategy.toast(message, resId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}