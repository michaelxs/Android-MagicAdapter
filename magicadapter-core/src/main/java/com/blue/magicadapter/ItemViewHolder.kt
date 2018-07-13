package com.blue.magicadapter

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * 统一ViewHolder
 */
class ItemViewHolder(
        val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {

    var item: IItem? = null

    /**
     * 统一构建viewholder
     */
    companion object {
        fun create(parent: ViewGroup, viewType: Int): ItemViewHolder {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), viewType, parent, false)
            return ItemViewHolder(binding)
        }
    }

    /**
     * 绑定数据
     */
    fun bindTo(item: IItem) {
        this.item = item
        binding.setVariable(item.getVariableId(), item)
        binding.executePendingBindings()
    }

}