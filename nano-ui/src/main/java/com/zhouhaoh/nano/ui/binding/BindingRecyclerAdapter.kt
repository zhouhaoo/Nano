package com.zhouhaoh.nano.ui.binding

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.zhouhaoh.nano.ui.BR


/**
 * 可以加载更多的直接使用的adapter
 * Created by wangchangwei
 *
 * @param items 数据
 * @param layoutRes 布局
 * @param viewModel vm
 * @param loadMore 是否需要加载更多的分页功能
 *
 * on 2019/06/14.
 */
class BindingRecyclerAdapter<T, B : ViewDataBinding>(
    private var items: MutableList<T>,
    private @LayoutRes var layoutRes: Int,
    private val viewModel: ViewModel? = null,
    private val loadMore: Boolean = true
) : BaseBindingAdapter<T, B>(
    items,
    layoutRes,
    viewModel,
    loadMore
) {
    override fun bindItem(binding: B, item: T, position: Int) {
        binding.setVariable(BR.item, item)
        binding.setVariable(BR.position, position)
        binding.setVariable(BR.viewModel, viewModel)

    }


}