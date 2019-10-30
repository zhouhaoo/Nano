package com.zhouhaoh.nano.mvvm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhouhaoh.nano.core.CompleteState
import com.zhouhaoh.nano.core.ErrorState
import com.zhouhaoh.nano.core.LoadingState
import com.zhouhaoh.nano.core.StateDispatcher
import com.zhouhaoh.nano.data.ApiException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext


/**
 * BaseViewModel

 * > Created by zhouhaoh  on 19/10/16.
 */
open class BaseViewModel : ViewModel() {
    /**
     * 状态分发
     */
    val state = MutableLiveData<StateDispatcher>()

    /**
     * 开始协程执行
     */
    fun launch(
        context: CoroutineContext = Dispatchers.Main,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit = {},
        finallyBlock: suspend CoroutineScope.() -> Unit = {},
        loadingState: LoadingState = LoadingState(),
        controlException: Boolean = false,
        tryBlock: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(context = context) {
            coroutineScope {
                try {
                    state.postValue(loadingState)
                    tryBlock()
                } catch (t: Throwable) {
                    Timber.e(t)
                    if (controlException && t is ApiException) {
                        catchBlock(t)
                    } else {
                        state.value = ErrorState(t)
                    }
                } finally {
                    state.postValue(CompleteState())
                    finallyBlock()
                }
            }
        }
    }
}