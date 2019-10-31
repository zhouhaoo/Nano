package com.zhouhaoh.nano.ui.binding

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zhouhaoh.nano.ui.BR
import com.zhouhaoh.nano.ui.R
import com.zhouhaoh.nano.ui.databinding.AbsFooterLoadMoreBinding

/**
 * 利用类型别名定义空的lambda表达式
 */
typealias ItemListener = () -> Unit

/**
 *
 * baseadpater
 * Created by wangchangwei
 * on 2019/6/06.
 *
 * @param items 数据
 * @param layoutRes 布局
 * @param viewModel vm
 * @param isLoadingMore 标记是否是否需要加载更多布局
 *
 */
abstract class BaseBindingAdapter<T, B : ViewDataBinding>(
    private var items: MutableList<T>,
    private @LayoutRes var layoutRes: Int,
    private val viewModel: ViewModel? = null,
    private var isLoadingMore: Boolean = true
) :
    RecyclerView.Adapter<BaseBindingAdapter<T, B>.ViewHolder>() {
    private var itemClickListener: ((item: T, binding: B) -> Unit)? = null


    val pageSize = 20

    companion object {
        val NORMAL = 1
        val LOADING = 2
        val FAIL = 3
        val COMPLETED = 4
    }

    var loadState = NORMAL

    private val ITEM_TYPE_NORMAL = 0xEEEE
    private val ITEM_TYPE_FOOTER = 0xFFFF

    init {
        //+getItemId 防止item闪烁
        setHasStableIds(true)
    }

    override fun getItemId(position: Int) = position.toLong()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val gridManager = layoutManager as GridLayoutManager
            gridManager.setSpanSizeLookup(object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == gridManager.getItemCount() - 1) {
                        gridManager.getSpanCount()
                    } else getSpanSize(position)
                }
            })
        }
        recyclerView.addOnScrollListener(onScrollListener)

    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerView.removeOnScrollListener(onScrollListener)
        handler.removeCallbacksAndMessages(this)
    }

    override fun getItemViewType(position: Int) =
        if (isLoadingMore && position == itemCount - 1) ITEM_TYPE_FOOTER else ITEM_TYPE_NORMAL


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        ITEM_TYPE_FOOTER -> createFooterViewHolder(parent)
        else -> {
            val inflater = LayoutInflater.from(parent.context)
            val binding = DataBindingUtil.inflate<B>(inflater, layoutRes, parent, false)
            ViewHolder(binding)
        }
    }


    fun createFooterViewHolder(parent: ViewGroup): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<B>(layoutInflater, R.layout.abs_footer_load_more, parent, false)
        return ViewHolderFooter(binding)
    }

    override fun getItemCount() = if (items.size == 0) 0 else {
        if (isLoadingMore) items.size + 1 else items.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Timber.d("holder.itemViewType:${holder.itemViewType},$isLoadingMore")
        when (holder.itemViewType) {
            ITEM_TYPE_FOOTER -> (holder as ViewHolderFooter).bindFooter()
            ITEM_TYPE_NORMAL -> holder.bind(items[position], position)
        }
    }


    open inner class ViewHolder(val binding: B) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: T, position: Int) {
            if (viewModel != null) {
                binding.setVariable(BR.viewModel, viewModel)
            }
            bindItem(binding, item, position)
            bindAgoExecute(binding, item, position)
            binding.executePendingBindings()
            if (itemClickListener != null) {
                binding.root.setOnClickListener {
                    itemClickListener?.invoke(item, binding)
                }
            }
            bindAfterExecute(binding, item, position)
        }

    }

    inner class ViewHolderFooter(val footerBinding: B) : ViewHolder(footerBinding) {
        fun bindFooter() {
            if (footerBinding is AbsFooterLoadMoreBinding) {
                itemView.setOnClickListener {
                    loadState = LOADING
                    changeLoadState()
                    if (isLoadingMore) {
                        onLoadListener.invoke()
                    }
                }

                bindLoadState(loadState, footerBinding)
            }
        }

        internal fun bindLoadState(
            loadState: Int,
            footerBinding: AbsFooterLoadMoreBinding
        ) {
            //Timber.d("bindLoadState:" + loadState)
            when (loadState) {
                NORMAL -> {
                    itemView.setEnabled(true)
                    footerBinding.progressBar.visibility = GONE
                    footerBinding.text.setText(R.string.abs_load_more)
                }
                LOADING -> {
                    itemView.setEnabled(false)
                    footerBinding.progressBar.visibility = VISIBLE
                    footerBinding.text.setText(R.string.abs_load_loading)
                }
                FAIL -> {
                    itemView.setEnabled(true)
                    footerBinding.progressBar.visibility = GONE
                    footerBinding.text.setText(R.string.abs_load_fail)
                }
                COMPLETED -> {
                    itemView.setEnabled(false)
                    /*if (getItemCount() < 10) {
                                           mProgressBar.setVisibility(GONE);
                                           mTextView.setVisibility(GONE);
                                           return;
                                       }*/
                    footerBinding.progressBar.visibility = GONE
                    footerBinding.text.setText(R.string.abs_load_completed)
                }
            }
        }

    }


    abstract fun bindItem(binding: B, item: T, position: Int)

    /**
     * 绑定前执行
     */
    open fun bindAgoExecute(binding: B, item: T, position: Int) {}

    /**
     *  绑定后执行
     */
    open fun bindAfterExecute(binding: B, item: T, position: Int) {}

    /**
     * 只有item一个点击事件时实现此方法即可
     */
    fun setItemClick(click: (item: T, binding: B) -> Unit) {
        itemClickListener = click
    }


    // 声明函数变量
    val imageClick: (adapter: BaseBindingAdapter<T, B>) -> Unit =
        { adapter -> viewClicked(adapter) }

    private fun viewClicked(adapter: BaseBindingAdapter<T, B>) {

    }


    private lateinit var onLoadListener: ItemListener

    /**
     * 加载更多的方法
     */
    fun setOnLoadListener(onLoadListener: ItemListener) {
        this.onLoadListener = onLoadListener
    }


    val onScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (!isLoadingMore) return
            if (itemCount < 2) return

            var lastVisiblePosition = 0
            val layoutManager = recyclerView.layoutManager
            if (layoutManager is LinearLayoutManager) {
                lastVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition()
            } else if (layoutManager is StaggeredGridLayoutManager) {
                val staggeredGridLayoutManager = layoutManager as StaggeredGridLayoutManager
                if (layoutManager.getItemCount() > 0) {
                    lastVisiblePosition =
                        staggeredGridLayoutManager.findLastVisibleItemPositions(null)[0]
                } else {
                    lastVisiblePosition = 0
                }
            }

            if (lastVisiblePosition == layoutManager!!.getItemCount() - 1 && loadState == NORMAL) {
                if (isLoadingMore) {
                    onLoadListener.invoke()
                }
                loadState = LOADING
                changeLoadState()
            }
        }
    }


    fun onFinishLoad() {
        loadState = NORMAL
        changeLoadState()
    }

    fun onLoadFail() {
        loadState = FAIL
        changeLoadState()
    }

    fun onCompleted() {
        loadState = COMPLETED
        changeLoadState()
    }

    private val handler = android.os.Handler()
    //改变加载状态
    private fun changeLoadState() {
        //Timber.d("changeLoadState:$loadState")
        handler.post { notifyItemChanged(itemCount - 1) }
    }

    fun setNewData(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        if (isLoadingMore) checkCompleted()
        notifyDataSetChanged()
    }


    fun addData(data: List<T>) {
        items.addAll(data)
        if (data.size < pageSize) loadState = COMPLETED else loadState =
            NORMAL
        changeLoadState()
        notifyItemRangeInserted(items.size - data.size, data.size)
    }

    fun checkCompleted() = if (items.size < pageSize) loadState =
        COMPLETED else loadState =
        NORMAL


}