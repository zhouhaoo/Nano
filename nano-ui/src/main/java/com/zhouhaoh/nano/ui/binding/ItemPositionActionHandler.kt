package com.zhouhaoa.nano.ui.binding


interface ItemPositionActionHandler<T> : ItemActionHandler<T> {

    fun onItemClick(t: T, position: Int)

}