package com.zhouhaoh.nano.ui

import android.content.Context
import com.zhouhaoh.nano.core.LoadingState

class DefaultUIStrategy(windowContext: Context) : CommonUIStrategy(windowContext) {
    override fun showLoading(loadingState: LoadingState) {
    }

    override fun dismissLoading() {

    }

    override fun toast(message: String?, resId: Int) {

    }
}