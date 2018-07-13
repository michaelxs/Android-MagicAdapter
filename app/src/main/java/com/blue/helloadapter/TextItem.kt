package com.blue.helloadapter

import com.blue.magicadapter.BaseItem

/**
 * text style
 */
class TextItem(
        val text: String
) : BaseItem() {

    override fun getLayout(): Int = R.layout.text_item_layout

}