package com.blue.helloadapter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.blue.magicadapter.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val mAdapter by lazy { MagicAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
    }

    private fun initView() {
        val items = mutableListOf<IItem>().apply {
            // add text item
            (1..3).forEach {
                add(TextItem("this is test item"))
            }
            // add button item
            (1..2).forEach {
                add(ButtonItem("this is button item"))
            }
            // add image item
            (1..3).forEach {
                add(ImageItem(R.drawable.s1))
            }
            // add text item
            (1..5).forEach {
                add(TextItem("this is test item"))
            }
            // add button item
            (1..2).forEach {
                add(ButtonItem("this is button item"))
            }
            // add image item
            (1..3).forEach {
                add(ImageItem(R.drawable.s1))
            }
        }

        with(mAdapter) {
            addItems(items)
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(holder: ItemViewHolder) {
                    Toast.makeText(this@MainActivity, "onItemClick position = ${holder.adapterPosition}", Toast.LENGTH_SHORT).show()
                }
            }
            onItemLongClickListener = object : OnItemLongClickListener {
                override fun onItemLongClick(holder: ItemViewHolder) {
                    Toast.makeText(this@MainActivity, "onItemLongClick position = ${holder.adapterPosition}", Toast.LENGTH_SHORT).show()
                }

            }
        }

        with(recyclerView){
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.actionbar_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                mAdapter.removeItem(0)
                mAdapter.notifyItemRemoved(0)
            }
            R.id.addTv -> {
                mAdapter.addItem(1, TextItem("menu add text"))
                mAdapter.notifyItemInserted(1)
            }
            R.id.addBtn -> {
                mAdapter.addItem(1, ButtonItem("menu add text"))
                mAdapter.notifyItemInserted(1)
            }
            R.id.addIv -> {
                mAdapter.addItem(1, ImageItem(R.drawable.s2))
                mAdapter.notifyItemInserted(1)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
