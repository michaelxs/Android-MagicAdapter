package com.blue.helloadapter

import android.widget.Toast
import com.blue.helloadapter.databinding.ButtonItemLayoutBinding
import com.blue.magicadapter.BaseItem
import com.blue.magicadapter.ItemViewHolder

/**
 * button style
 */
class ButtonItem(
        val text: String
) : BaseItem() {

    override fun getLayout(): Int = R.layout.button_item_layout

    override fun onBinding(holder: ItemViewHolder) {
        (holder.binding as ButtonItemLayoutBinding).apply {
            btn.setOnClickListener {
                Toast.makeText(btn.context, "button on click", Toast.LENGTH_SHORT).show()
            }
        }
    }
}