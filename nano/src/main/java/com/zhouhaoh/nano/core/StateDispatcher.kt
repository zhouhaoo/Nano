package com.zhouhaoh.nano.core

import com.zhouhaoh.nano.R


/**
 * ###数据状态分发类
 * 分发数据最终状态到UI层
 * > Created by zhouhaoh  on 2019/01/08.
 */
sealed class StateDispatcher


/**
 * 加载中...
 * @param show true 为默认dialog隐藏
 * @param loadingMessage 加载中的提示语
 */
class LoadingState(
    val show: Boolean = true,
    val loadingMessage: Int = R.string.nano_loading
) : StateDispatcher()

/**
 * 错误状态
 * @param throwable
 */
class ErrorState(val throwable: Throwable) : StateDispatcher()

/**
 * 成功
 *@param defaultDialog true 为默认dialog加载
 * @param successHint 成功后提示语
 */
class SuccessState(
    val defaultDialog: Boolean = true,
    val successHint: Int
) : StateDispatcher()

/**
 * 提示状态 [messageInfo] 提示的内容
 */
class HintState(val messageInfo: Int) : StateDispatcher()

/**
 * 完成状态
 */
class CompleteState : StateDispatcher()