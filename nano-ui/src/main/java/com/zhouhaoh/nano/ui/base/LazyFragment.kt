package com.zhouhaoh.nano.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zhouhaoh.nano.state.LoadingState
import com.zhouhaoh.nano.ui.component.stateView.StateLayout
import com.zhouhaoh.nano.ui.strategy.CommonUIStrategy
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class LazyFragment : Fragment(), IView {

    private var isViewCreated: Boolean = false

    private var isViewVisible: Boolean = false

    private var isInitialized: Boolean = false

    var fragmentView: View? = null
    protected var stateLayout: StateLayout? = null

    private val uiStrategy by inject<CommonUIStrategy> {
        parametersOf(this.activity)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        isViewVisible = userVisibleHint
        prepareLoad()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(layoutId(), container, false)
            stateLayout = StateLayout(context!!)
                .wrap(fragmentView)
        }
        return stateLayout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewCreated = true
        lifecycle.addObserver(uiStrategy)
        initData(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prepareLoad()
    }

    private fun prepareLoad() {
        if (isViewCreated && isViewVisible && !isInitialized) {
            onLazyLoad()
            isInitialized = true
        }
    }


    /**
     * 初始化
     */
    abstract fun initData(savedInstanceState: Bundle?)

    abstract fun layoutId(): Int

    abstract fun onLazyLoad()


    override fun showLoading(loadingState: LoadingState) {
        uiStrategy.showLoading(loadingState)
    }

    override fun dismissLoading() {
        uiStrategy.dismissLoading()
    }

    override fun toast(message: String?, resId: Int) {
        uiStrategy.toast(message, resId)
    }
}