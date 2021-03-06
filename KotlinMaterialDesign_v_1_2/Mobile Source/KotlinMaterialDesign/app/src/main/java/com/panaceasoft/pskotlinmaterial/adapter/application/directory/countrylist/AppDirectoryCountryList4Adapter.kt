package com.panaceasoft.pskotlinmaterial.adapter.application.directory.countrylist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.panaceasoft.pskotlinmaterial.R
import com.panaceasoft.pskotlinmaterial.`object`.Country
import com.panaceasoft.pskotlinmaterial.utils.Utils
import kotlinx.android.synthetic.main.app_directory_country_list_4_item.view.*
import java.util.*

class AppDirectoryCountryList4Adapter(private val countryArrayList: ArrayList<Country>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: Country, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.app_directory_country_list_4_item, parent, false)

        return CountryViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is CountryViewHolder) {

            val country = countryArrayList[position]

            viewHolder.countryNameTextView.text = country.name

            val context = viewHolder.countryCardView.context

            val id = Utils.getDrawableInt(context, country.imageName)
            Utils.setImageToImageView(context, viewHolder.countryFlagImageView, id)


            viewHolder.countryCardView.setOnClickListener { v: View -> itemClickListener.onItemClick(v, countryArrayList[position], position) }

        }
    }

    override fun getItemCount(): Int {
        return countryArrayList.size
    }

    inner class CountryViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var countryNameTextView: TextView = view.countryNameTextView
        internal var countryFlagImageView: ImageView = view.countryFlagImageView
        internal var countryCardView: CardView = view.countryCardView

    }
}