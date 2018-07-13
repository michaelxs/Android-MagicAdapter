package com.blue.helloadapter

import android.databinding.ViewDataBinding
import android.widget.Toast
import com.blue.helloadapter.databinding.ButtonItemLayoutBinding
import com.blue.magicadapter.BaseItem

/**
 * button style
 */
class ButtonItem(
        val text: String
) : BaseItem() {

    override fun getLayout(): Int = R.layout.button_item_layout

    override fun onBinding(binding: ViewDataBinding) {
        (binding as ButtonItemLayoutBinding).apply {
            btn.setOnClickListener {
                Toast.makeText(btn.context, "button on click", Toast.LENGTH_SHORT).show()
            }
        }
    }
}