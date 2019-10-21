package com.zhouhaoh.nano.core



/**
 *UI操作
 * > Created by zhouhaoh  on 19/01/11.
 */
interface IView {
    /**
     * dialog进度加载
     */
    fun showLoading(loadingState: LoadingState)

    /**
     * 隐藏进度加载
     */
    fun dismissLoading()

    /**
     * 错误提示信息
     */
    fun toast(message: String? = null, resId: Int = 0)
}