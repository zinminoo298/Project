package com.panaceasoft.pskotlinmaterial.adapter.application.directory.gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.panaceasoft.pskotlinmaterial.R
import com.panaceasoft.pskotlinmaterial.`object`.SectionGalleryListHolder
import com.panaceasoft.pskotlinmaterial.utils.Utils
import kotlinx.android.synthetic.main.app_directory_gallery_3_item.view.*
import kotlinx.android.synthetic.main.app_directory_gallery_3_section.view.*

/**
 * Created by Panacea-Soft on 6/29/18.
 * Contact Email : teamps.is.cool@gmail.com
 */


class AppDirectoryGallery3Adapter(private val sectionGalleryListHolderList: List<SectionGalleryListHolder>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val SECTION = 0
    private val ITEM = 1
    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: SectionGalleryListHolder, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == SECTION) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.app_directory_gallery_3_section, parent, false)

            return SectionViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.app_directory_gallery_3_item, parent, false)

            return PlaceViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(reholder: RecyclerView.ViewHolder, position: Int) {

        if (sectionGalleryListHolderList[position].isSectionTitle) {
            if (reholder is SectionViewHolder) {

                val layoutParams = reholder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
                reholder.sectionTitleTextView.text = sectionGalleryListHolderList[position].name
                layoutParams.isFullSpan = true
            }

        } else {
            if (reholder is PlaceViewHolder) {
                val section = sectionGalleryListHolderList[position]
                val context = reholder.placeImageView.context
                val id = Utils.getDrawableInt(context, section.image.image)
                Utils.setImageToImageView(context, reholder.placeImageView, id)

                reholder.placeImageView.setOnClickListener { view ->
                    itemClickListener.onItemClick(view, sectionGalleryListHolderList[position], position)
                }
                val layoutParams = reholder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
                layoutParams.isFullSpan = false
            }
        }

    }

    override fun getItemCount(): Int {
        return sectionGalleryListHolderList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (sectionGalleryListHolderList[position].isSectionTitle) {
            SECTION
        } else {
            ITEM
        }
    }

    inner class PlaceViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var placeImageView: ImageView = view.itemImageView

    }

    inner class SectionViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var sectionTitleTextView: TextView = view.sectionTitleTextView

    }

}

