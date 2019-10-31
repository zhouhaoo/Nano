package com.zhouhaoh.nano.sample.base

import android.content.Context
import com.hjq.toast.ToastUtils
import com.zhouhaoh.nano.ui.strategy.DefaultUIStrategy

/**
 * ### app基本通用Ui策略 示例
 * > Created by zhouhaoh  on 2019/10/20.
 */
class AppUIStrategy(windowContext: Context) : DefaultUIStrategy(windowContext) {

    override fun toast(message: String?, resId: Int) {
        if (resId == 0) ToastUtils.show(message) else ToastUtils.show(resId)
    }

}