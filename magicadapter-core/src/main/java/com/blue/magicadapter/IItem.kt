package com.blue.magicadapter

/**
 * 统一item接口
 */
interface IItem {

    /**
     * databinding中的variableId
     */
    fun getVariableId(): Int

    /**
     * item布局文件
     */
    fun getLayout(): Int

    /**
     * 附着的适配器
     */
    fun attachAdapter(adapter: MagicAdapter)

    /**
     * 绑定数据
     */
    fun onBinding(holder: ItemViewHolder)

    /**
     * 视图附着
     */
    fun onViewAttachedToWindow(holder: ItemViewHolder)

    /**
     * 视图移除
     */
    fun onViewDetachedFromWindow(holder: ItemViewHolder)
}