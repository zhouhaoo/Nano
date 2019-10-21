package com.zhouhaoh.nano.sample.base

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.zhouhaoh.nano.core.*
import com.zhouhaoh.nano.data.ApiException
import com.zhouhaoh.nano.mvvm.BaseViewModel
import com.zhouhaoh.nano.sample.data.BaseResponse
import com.zhouhaoh.nano.ui.loading.SlackLoadingView
import com.zhouhaoh.nano.utils.parseException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber


suspend inline fun <reified T> withIOContext(
    crossinline block: suspend CoroutineScope.() -> BaseResponse<T>
): T = withContext(Dispatchers.IO) {
    val response = block()
    if (!response.error){
        Timber.e("response.results")
        response.results ?: T::class.java.newInstance()
    } else{
        Timber.e("response.        throw  ApiException(response.message)")
        throw  ApiException(response.message)
    }
}

fun LifecycleOwner.applyState(viewModel: BaseViewModel) {
    if (this is IView) {
        viewModel.state.observe(this, Observer {
            when (it) {
                is LoadingState -> {
                    showLoading(loadingState = it)
                }
                is ErrorState -> {
                    if (it.throwable is ApiException) {
                        toast(message = it.throwable.message)
                    } else {
                        toast(resId = parseException(it.throwable))
                    }
                }
                is CompleteState -> this.dismissLoading()
            }
        })
    }
}


/**
 * 停止加载进度旋转
 */
fun SlackLoadingView.stop() {
    this.apply {
        reset()
        visibility = View.GONE
    }
}

/**
 * 开始加载进度旋转
 */
fun SlackLoadingView.reStart() {
    this.apply {
        visibility = View.VISIBLE
        start()
    }
}


/**
 * 设置toolbar返回键可用
 */
fun BaseActivity.setBackNavigation(toolbar: Toolbar) {
    setSupportActionBar(toolbar)
    supportActionBar?.apply {
        setDisplayShowTitleEnabled(false)
        setDisplayHomeAsUpEnabled(true)//左侧添加一个默认的返回图标
        setHomeButtonEnabled(true) //设置返回键可用
    }
}

