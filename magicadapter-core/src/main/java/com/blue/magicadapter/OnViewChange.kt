package com.blue.magicadapter

/**
 * 视图发生变化
 */
interface OnViewChange {
    fun onViewAttachedToWindow(holder: ItemViewHolder)

    fun onViewDetachedFromWindow(holder: ItemViewHolder)
}