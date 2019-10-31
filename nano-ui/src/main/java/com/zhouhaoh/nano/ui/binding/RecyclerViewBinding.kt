package com.zhouhaoa.nano.ui.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


/**
 * ### dataBinding 绑定[recyclerView]的[adapter]
 *  Created by wangchangwei on 19/06/12.
 */
@BindingAdapter("bind_adapter")
fun bindAdapter(
    recyclerView: RecyclerView,
    adapter: RecyclerView.Adapter<out RecyclerView.ViewHolder>?
) {
    adapter?.apply {
        recyclerView.adapter = adapter
    }
}