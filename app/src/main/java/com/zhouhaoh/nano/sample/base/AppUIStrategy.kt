package com.zhouhaoh.nano.sample.base

import android.content.Context
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.hjq.toast.ToastUtils
import com.zhouhaoh.nano.core.LoadingState
import com.zhouhaoh.nano.sample.R
import com.zhouhaoh.nano.ui.CommonUIStrategy
import kotlinx.android.synthetic.main.dialog_loading.view.*
import timber.log.Timber

/**
 * ### app基本通用Ui策略
 * > Created by zhouhaoh  on 2019/10/20.
 */
class AppUIStrategy(windowContext: Context) : CommonUIStrategy(windowContext) {

    /**
     * dialog
     */
    private lateinit var loadingDialog: MaterialDialog
    /**
     * 进度加载
     */
    private lateinit var loadingView: View

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create() {
        Timber.i("%s  --", "Lifecycle.Event.ON_CREATE")
    }

    init {
        initDialog(windowContext)
    }


    private fun initDialog(windowContext: Context) {
        loadingDialog = MaterialDialog(windowContext)
            .customView(R.layout.dialog_loading, noVerticalPadding = true)
            .noAutoDismiss()
            .cancelable(false)
        loadingView = loadingDialog.getCustomView()

    }

    override fun showLoading(loadingState: LoadingState) {
        if (loadingState.show) {
            if (!loadingDialog.isShowing) {
                loadingView.slackLoadingView.start()
                loadingDialog.show()
            }
            loadingView.slackLoadingView.visibility = View.VISIBLE
            loadingView.slackLoadingView.start()
            loadingView.tv_loading_msg.setText(loadingState.loadingMessage)
        }
        Timber.i("%s  --  %s", "showLoading", loadingState)
    }

    override fun dismissLoading() {
        Timber.i("%s  --", "dismissLoading")
        if (loadingDialog.isShowing) {
            loadingView.slackLoadingView.stop()
            loadingDialog.dismiss()
        }
    }

    override fun toast(message: String?, resId: Int) {
        Timber.i("%s--message:%s---resId:%s", "toast", message, resId)
        if (resId == 0) ToastUtils.show(message) else ToastUtils.show(resId)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stop() {
        Timber.i("%s  --", "Lifecycle.Event.ON_STOP")
    }
}