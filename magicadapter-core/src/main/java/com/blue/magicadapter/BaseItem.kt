package com.blue.magicadapter

import android.databinding.ViewDataBinding

/**
 * base
 */
abstract class BaseItem : IItem {

    private var adapter: MagicAdapter? = null
    private var holder: ItemViewHolder? = null

    override fun getVariableId(): Int = BR.item

    override fun attachAdapter(adapter: MagicAdapter) {
        this.adapter = adapter
    }

    override fun onBinding(binding: ViewDataBinding) {
    }

    override fun onViewAttachedToWindow(holder: ItemViewHolder) {
        this.holder = holder
    }

    override fun onViewDetachedFromWindow(holder: ItemViewHolder) {
    }

    fun getAdapter(): MagicAdapter? = adapter

    fun getViewHolder(): ItemViewHolder? = holder
}