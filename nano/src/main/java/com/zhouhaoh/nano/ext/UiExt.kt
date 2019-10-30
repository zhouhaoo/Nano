package com.zhouhaoh.nano.ext

import android.view.View
import com.zhouhaoh.nano.ui.loading.SlackLoadingView

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
