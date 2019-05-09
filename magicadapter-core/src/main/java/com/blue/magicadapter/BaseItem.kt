package com.blue.magicadapter

/**
 * base
 */
abstract class BaseItem : IItem {

    private var adapter: MagicAdapter? = null

    override fun getVariableId(): Int = BR.item

    override fun attachAdapter(adapter: MagicAdapter) {
        this.adapter = adapter
    }

    override fun onBinding(holder: ItemViewHolder) {
    }

    override fun onViewAttachedToWindow(holder: ItemViewHolder) {
    }

    override fun onViewDetachedFromWindow(holder: ItemViewHolder) {
    }

    fun getAdapter(): MagicAdapter? = adapter
}