package com.blue.magicadapter

import android.support.annotation.IdRes
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

/**
 * 最简洁的万能适配器
 */
class MagicAdapter : RecyclerView.Adapter<ItemViewHolder>() {

    // 数据源
    private val items = mutableListOf<IItem>()

    // 响应事件
    var itemClick: ((ItemViewHolder) -> Unit)? = null
    var itemLongClick: ((ItemViewHolder) -> Unit)? = null
    var viewAttached: ((ItemViewHolder) -> Unit)? = null
    var viewDetached: ((ItemViewHolder) -> Unit)? = null

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].getLayout()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder.create(parent, viewType)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        setItemOnClick(holder, if (hasTag(holder.itemView, R.id.item_click)) null else View.OnClickListener {
            itemClick?.invoke(holder)
        })
        setItemOnLongClick(holder, if (hasTag(holder.itemView, R.id.item_long_click)) null else View.OnLongClickListener {
            itemLongClick?.invoke(holder)
            true
        })
        val item = items[holder.adapterPosition]
        item.attachAdapter(this)
        item.onBinding(holder)
        holder.bindTo(item)
    }

    private fun hasTag(view: View, @IdRes tag: Int) = view.getTag(tag) != null

    /**
     * 设置 item 点击事件（复用）
     */
    private fun setItemOnClick(holder: ItemViewHolder, clickListener: View.OnClickListener?) {
        var listener = clickListener
        val tag = holder.itemView.getTag(R.id.item_click)
        if (tag == null) {
            holder.itemView.setTag(R.id.item_click, listener)
        } else {
            listener = tag as View.OnClickListener
        }
        holder.itemView.setOnClickListener(listener)
    }

    /**
     * 设置 item 长按事件（复用）
     */
    private fun setItemOnLongClick(holder: ItemViewHolder, longClickListener: View.OnLongClickListener?) {
        var listener = longClickListener
        val tag = holder.itemView.getTag(R.id.item_long_click)
        if (tag == null) {
            holder.itemView.setTag(R.id.item_long_click, listener)
        } else {
            listener = tag as View.OnLongClickListener
        }
        holder.itemView.setOnLongClickListener(listener)
    }

    override fun onViewAttachedToWindow(holder: ItemViewHolder) {
        val pos = holder.adapterPosition
        if (items.size > pos && pos >= 0) {
            items[pos].onViewAttachedToWindow(holder)
        }
        viewAttached?.invoke(holder)
    }

    override fun onViewDetachedFromWindow(holder: ItemViewHolder) {
        val pos = holder.adapterPosition
        if (items.size > pos && pos >= 0) {
            items[pos].onViewDetachedFromWindow(holder)
        }
        viewDetached?.invoke(holder)
    }

    fun addItem(item: IItem) {
        items.add(item)
    }

    fun addItem(index: Int, item: IItem) {
        items.add(index, item)
    }

    fun setItem(index: Int, item: IItem) {
        items[index] = item
    }

    fun addItems(items: List<IItem>) {
        this.items.addAll(items)
    }

    fun <T : IItem> addItems(items: Collection<T>) {
        this.items.addAll(items)
    }

    fun removeItem(item: IItem) {
        items.remove(item)
    }

    fun removeItem(index: Int) {
        items.removeAt(index)
    }

    fun clearItems() {
        items.clear()
    }
}