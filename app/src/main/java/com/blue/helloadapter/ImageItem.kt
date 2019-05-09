package com.blue.helloadapter

import android.widget.Toast
import com.blue.helloadapter.databinding.ImageItemLayoutBinding
import com.blue.magicadapter.BaseItem
import com.blue.magicadapter.ItemViewHolder

/**
 * image style
 */
class ImageItem(
        val resId: Int
) : BaseItem() {

    override fun getLayout(): Int = R.layout.image_item_layout

    override fun onBinding(holder: ItemViewHolder) {
        (holder.binding as ImageItemLayoutBinding).apply {
            iv.setImageResource(resId)
            iv.setOnClickListener {
                Toast.makeText(iv.context, "click image on ${holder.adapterPosition}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}