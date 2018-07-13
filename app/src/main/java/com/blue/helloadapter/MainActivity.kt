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

    private val adapter by lazy { MagicAdapter() }

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

        with(adapter) {
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
            adapter = adapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.actionbar_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                adapter.removeItem(0)
                adapter.notifyItemRemoved(0)
            }
            R.id.addTv -> {
                adapter.addItem(1, TextItem("menu add text"))
                adapter.notifyItemInserted(1)
            }
            R.id.addBtn -> {
                adapter.addItem(1, ButtonItem("menu add text"))
                adapter.notifyItemInserted(1)
            }
            R.id.addIv -> {
                adapter.addItem(1, ImageItem(R.drawable.s2))
                adapter.notifyItemInserted(1)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
