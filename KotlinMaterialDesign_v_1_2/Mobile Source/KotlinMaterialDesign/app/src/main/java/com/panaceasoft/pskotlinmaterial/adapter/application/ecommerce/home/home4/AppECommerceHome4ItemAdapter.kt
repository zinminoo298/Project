package com.panaceasoft.pskotlinmaterial.adapter.application.ecommerce.home.home4

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.panaceasoft.pskotlinmaterial.R
import com.panaceasoft.pskotlinmaterial.`object`.ShopItem
import com.panaceasoft.pskotlinmaterial.utils.Utils
import kotlinx.android.synthetic.main.app_ecommerce_home_4_item.view.*
import java.util.*

/**
 * Created by Panacea-Soft on 6/9/18.
 * Contact Email : teamps.is.cool@gmail.com
 */


class AppECommerceHome4ItemAdapter(private val shopItemArrayList: ArrayList<ShopItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var itemClickListener: OnItemClickListener


    interface OnItemClickListener {
        fun onItemClick(view: View, obj: ShopItem, position: Int)

        fun onLikeClick(view: View, obj: ShopItem, position: Int)

    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.app_ecommerce_home_4_item, parent, false)

        return ItemViewHolder(itemView)

    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        if (viewHolder is ItemViewHolder) {

            // Get Shop Item
            val shopItem = shopItemArrayList[position]

            // Convert Item Holder

            // Get Context
            val context = viewHolder.holderCardView.context

            // Set Data to UI
            viewHolder.itemNameTextView.text = shopItem.name

            val price = shopItem.currency + shopItem.price
            viewHolder.priceTextView.text = price

            val originalPrice = shopItem.currency + shopItem.originalPrice
            viewHolder.originalPriceTextView.text = originalPrice

            if (shopItem.discount == "0") {
                viewHolder.originalPriceTextView.visibility = View.GONE
            } else {
                viewHolder.originalPriceTextView.visibility = View.VISIBLE
            }

            viewHolder.originalPriceTextView.paintFlags = viewHolder.originalPriceTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

            if (shopItem.isLiked!!) {
                viewHolder.likeImageView.setColorFilter(ContextCompat.getColor(context, R.color.md_red_500), android.graphics.PorterDuff.Mode.SRC_IN)
            } else {
                viewHolder.likeImageView.setColorFilter(ContextCompat.getColor(context, R.color.md_grey_400), android.graphics.PorterDuff.Mode.SRC_IN)
            }

            // Set Image
            val id = Utils.getDrawableInt(context, shopItem.imageName)
            Utils.setImageToImageView(context, viewHolder.itemImageView, id)

            // Listeners
                viewHolder.holderCardView.setOnClickListener { v: View -> itemClickListener.onItemClick(v, shopItemArrayList[position], position) }
                viewHolder.likeImageView.setOnClickListener { v: View -> itemClickListener.onLikeClick(v, shopItemArrayList[position], position) }
        }
    }

    override fun getItemCount(): Int {
        return shopItemArrayList.size
    }

    inner class ItemViewHolder internal constructor(view: View) : RecyclerView.ViewHolder(view) {
        internal var itemImageView: ImageView = view.itemImageView
        internal var itemNameTextView: TextView = view.itemNameTextView
        internal var priceTextView: TextView = view.priceTextView
        internal var originalPriceTextView: TextView = view.originalPriceTextView
        internal var likeImageView: ImageView = view.likeImageView
        internal var holderCardView: CardView = view.holderCardView

    }
}
