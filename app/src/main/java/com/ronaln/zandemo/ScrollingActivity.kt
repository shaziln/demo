package com.ronaln.zandemo

import android.os.Bundle
import android.util.Log
import android.view.*
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.item_show_data.view.*

class ScrollingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        initUI()
        initListener()
    }

    private fun initListener() {
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    private fun initUI(){
        val layoutManager= LinearLayoutManager(this)
        recycler_view.layoutManager=layoutManager
        recycler_view.addItemDecoration(DividerItemDecoration(recycler_view.context,layoutManager.orientation))
        recycler_view.adapter=ListAdapter()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}

class Data(var desc:String,
           var isLike:Boolean)

class ListAdapter :RecyclerView.Adapter<DataViewHolder>(){
     var mData: ArrayList<Data> = ArrayList()

    init {
        if (mData.isNullOrEmpty()){
            for (i in 0..15){
                val item=Data("算法问题实战策略：${i}",i%4==0)
                mData.add(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        Log.d("ZanView","onCreateViewHolder")
        val itemView=LayoutInflater.from(parent.context).inflate(R.layout.item_show_data,parent,false)
        return DataViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        Log.d("ZanView","onBindViewHolder:$position")
        holder.showData(mData[position])
    }
}

class DataViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
    fun showData(data: Data){
        itemView.zan_view.tag=data.desc
        itemView.zan_view.isLike=data.isLike
        itemView.text_desc.text=data.desc

    }
}
