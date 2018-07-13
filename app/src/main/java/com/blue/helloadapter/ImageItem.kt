package com.blue.helloadapter

import android.databinding.ViewDataBinding
import android.widget.Toast
import com.blue.helloadapter.databinding.ImageItemLayoutBinding
import com.blue.magicadapter.BaseItem

/**
 * image style
 */
class ImageItem(
        val resId: Int
) : BaseItem() {

    override fun getLayout(): Int = R.layout.image_item_layout

    override fun onBinding(binding: ViewDataBinding) {
        (binding as ImageItemLayoutBinding).apply {
            iv.setImageResource(resId)
            iv.setOnClickListener {
                Toast.makeText(iv.context, "click image on ${getViewHolder()?.adapterPosition}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}