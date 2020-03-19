package com.panaceasoft.pskotlinmaterial.adapter.application.news.home.home1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.panaceasoft.pskotlinmaterial.R
import com.panaceasoft.pskotlinmaterial.`object`.News
import com.panaceasoft.pskotlinmaterial.utils.Utils

/**
 * Created by Panacea-Soft on 8/9/18.
 * Contact Email : teamps.is.cool@gmail.com
 */


class AppNewsHome1CoverFlowPagerAdapter(private val context: Context?, private val newsList: List<News>) : PagerAdapter() {
    private lateinit var itemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(view: View, obj: News, position: Int)
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.itemClickListener = mItemClickListener
    }

    override fun getCount(): Int {
        return newsList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layoutInflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.app_news_home_1_cover_flow_pager_item, container, false)

        val cardImageView = view.findViewById<ImageView>(R.id.cardImageView)
        val newsTitleTextView = view.findViewById<TextView>(R.id.newsTitleTextView)

        val news = newsList[position]

        newsTitleTextView.text = news.title

        val context = cardImageView.context

        val id = Utils.getDrawableInt(context, news.newsImage)
        Utils.setImageToImageView(context, cardImageView, id)

        val vp = container as ViewPager
        vp.addView(view, 0)

        // Listeners

        cardImageView.setOnClickListener { v: View -> itemClickListener.onItemClick(v, newsList[position], position) }


        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}