package com.blue.magicadapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * 最简洁的万能适配器
 */
class MagicAdapter : RecyclerView.Adapter<ItemViewHolder>() {

    // 数据源
    private val items = mutableListOf<IItem>()

    // 响应事件
    var onItemClickListener: OnItemClickListener? = null
    var onItemLongClickListener: OnItemLongClickListener? = null
    var onViewChange: OnViewChange? = null

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = items[position].getLayout()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder.create(parent, viewType)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(holder)
        }
        holder.itemView.setOnLongClickListener {
            onItemLongClickListener?.onItemLongClick(holder)
            true
        }
        val item = items[holder.adapterPosition]
        item.attachAdapter(this)
        item.onBinding(holder.binding)
        holder.bindTo(item)
    }

    override fun onViewAttachedToWindow(holder: ItemViewHolder) {
        val pos = holder.adapterPosition
        if (items.size > pos && pos >= 0) {
            items[pos].onViewAttachedToWindow(holder)
        }
        onViewChange?.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: ItemViewHolder) {
        val pos = holder.adapterPosition
        if (items.size > pos && pos >= 0) {
            items[pos].onViewDetachedFromWindow(holder)
        }
        onViewChange?.onViewDetachedFromWindow(holder)
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
        val pos = items.indexOf(item)
        items.remove(item)
    }

    fun removeItem(index: Int) {
        items.removeAt(index)
    }

    fun clearItems() {
        items.clear()
    }
}