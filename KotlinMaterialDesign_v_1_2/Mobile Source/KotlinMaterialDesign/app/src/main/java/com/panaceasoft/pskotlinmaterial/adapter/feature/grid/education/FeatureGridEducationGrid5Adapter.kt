package com.panaceasoft.pskotlinmaterial.adapter.feature.grid.education

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.panaceasoft.pskotlinmaterial.R
import com.panaceasoft.pskotlinmaterial.`object`.EducationVideo
import com.panaceasoft.pskotlinmaterial.utils.Utils
import kotlinx.android.synthetic.main.feature_grid_education_grid_5_item.view.*

/**
 * Created by Panacea-Soft on 24/8/18.
 * Contact Email : teamps.is.cool@gmail.com
 * Website : http://www.panacea-soft.com
 */
class FeatureGridEducationGrid5Adapter(private val itemList: List<EducationVideo>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var itemClickListener: FeatureGridEducationGrid5Adapter.OnItemClickListener


    interface OnItemClickListener {
        fun onItemClick(view: View, obj: EducationVideo, position: Int)
        fun onMoreClick(view: View, obj: EducationVideo, position: Int)
        fun onPlayClick(view: View, obj: EducationVideo, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: FeatureGridEducationGrid5Adapter.OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.feature_grid_education_grid_5_item, parent, false)

        return PlaceViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is FeatureGridEducationGrid5Adapter.PlaceViewHolder) {

            val item = itemList[position]

            viewHolder.titleTextView.text = item.title

            val videoTime = item.length
            viewHolder.timeTextView.text = videoTime
            viewHolder.categoryTextView.text = item.category
            val context = viewHolder.courseHolderView.context

            val id = Utils.getDrawableInt(context, item.image)
            Utils.setImageToImageView(context, viewHolder.videoImageView, id)



                viewHolder.courseHolderView.setOnClickListener { v: View -> itemClickListener.onItemClick(v, itemList[position], position) }
                viewHolder.moreImageView.setOnClickListener { v: View -> itemClickListener.onMoreClick(v, itemList[position], position) }
                viewHolder.playImageView.setOnClickListener { v: View -> itemClickListener.onPlayClick(v, itemList[position], position) }


        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class PlaceViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {


        internal var courseHolderView: CardView = view.courseHolderView
        internal var playImageView: ImageView = view.playImageView
        internal var videoImageView: ImageView = view.videoImageView
        internal var moreImageView: ImageView = view.moreImageView
        internal var titleTextView: TextView = view.titleTextView
        internal var timeTextView: TextView = view.timeTextView
        internal var categoryTextView: TextView = view.categoryTextView


    }
}