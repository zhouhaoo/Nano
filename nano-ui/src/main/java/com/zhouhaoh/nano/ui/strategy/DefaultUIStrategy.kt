package com.zhouhaoh.nano.ui.strategy

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.zhouhaoh.nano.state.LoadingState
import com.zhouhaoh.nano.ui.R
import com.zhouhaoh.nano.ui.ext.stop
import kotlinx.android.synthetic.main.nano_dialog_loading.view.*
import org.koin.android.ext.koin.androidApplication
import org.koin.core.KoinComponent

open class DefaultUIStrategy(windowContext: Context) : CommonUIStrategy(windowContext),
    KoinComponent {

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

    }

    init {
        initDialog(windowContext)
    }

    private fun initDialog(windowContext: Context) {
        loadingDialog = MaterialDialog(windowContext)
            .customView(R.layout.nano_dialog_loading, noVerticalPadding = true)
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
    }

    override fun dismissLoading() {
        if (loadingDialog.isShowing) {
            loadingView.slackLoadingView.stop()
            loadingDialog.dismiss()
        }
    }

    override fun toast(message: String?, resId: Int) {
        if (resId == 0) {
            Toast.makeText(
                getKoin().rootScope.androidApplication(),
                message,
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                getKoin().rootScope.androidApplication(),
                resId,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun stop() {

    }
}
