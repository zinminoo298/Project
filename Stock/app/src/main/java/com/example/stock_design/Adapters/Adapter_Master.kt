package com.example.stock_design.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.stock_design.Detail
import com.example.stock_design.Modle.Item_search
import com.example.stock_design.R
import kotlinx.android.synthetic.main.summery_row.view.*
import android.content.Context.MODE_PRIVATE
import android.R.id.edit
import android.content.SharedPreferences
import android.content.DialogInterface
import com.example.stock_design.Modle.Master_data
import kotlinx.android.synthetic.main.row_master_layout.view.*


/*Custom view adapter for summery data  */
class Adapter_Master(
    internal var stitem: MutableList<Master_data>,
    val context: Context

) : BaseAdapter() {

    internal val inflater: LayoutInflater

    init {
//        inflater = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.inflater=LayoutInflater.from(context)
    }



    /*view for each summery data*/
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView: View
//        val name = DataBaseHelper()

        rowView=inflater.inflate(R.layout.row_master_layout, null)

        rowView.textView_br.text=stitem[position].br.toString()
        rowView.textView_ic.text=stitem[position].ic.toString()
        rowView.textView_qty.text=stitem[position].quantity.toString()

        return rowView
    }

//    fun refresh(newList: List<Item_search>) {
//        stitem.clear()
//        stitem.addAll(newList)
//        notifyDataSetChanged()
//    }

    override fun getItem(position: Int): Any {
        return stitem[position]
    }

    override fun getItemId(position: Int): Long {
        return stitem[position].quantity.toLong()
    }

    override fun getCount(): Int {
        return stitem.size
    }
}