package com.panaceasoft.pskotlinmaterial.activity.feature.grid.news

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.panaceasoft.pskotlinmaterial.R
import com.panaceasoft.pskotlinmaterial.`object`.News
import com.panaceasoft.pskotlinmaterial.adapter.feature.grid.news.FeatureGridNewsGrid5Adapter
import com.panaceasoft.pskotlinmaterial.repository.news.NewsRepository
import kotlinx.android.synthetic.main.feature_grid_news_grid_5_activity.*
import java.util.*

class FeatureGridNewsGrid5Activity : AppCompatActivity() {

    private lateinit var itemArrayList: ArrayList<News>
    private lateinit var mAdapter: FeatureGridNewsGrid5Adapter
    internal var numberOfColumns = 2

    //region override methods
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feature_grid_news_grid_5_activity)

        initData()

        initUI()

        initDataBinding()

        initActions()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else {
            Toast.makeText(applicationContext, item.title, Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }
    //endregion

    //region Init Functions
    private fun initData() {
        itemArrayList = NewsRepository.newsList
    }

    private fun initUI() {
        initToolbar()

        mAdapter = FeatureGridNewsGrid5Adapter(itemArrayList)

        // get RecyclerView and bind
        recyclerView.layoutManager = GridLayoutManager(this, numberOfColumns)
        recyclerView.itemAnimator = DefaultItemAnimator()
    }

    private fun initDataBinding() {
        recyclerView.adapter = mAdapter
    }

    private fun initActions() {
        mAdapter.setOnItemClickListener(object : FeatureGridNewsGrid5Adapter.OnItemClickListener {

            override fun onItemClick(view: View, obj: News, position: Int) {
                Toast.makeText(applicationContext, "Selected : " + obj.title, Toast.LENGTH_LONG).show()
            }

            override fun onBookMarkClick(view: View, obj: News, position: Int) {
                Toast.makeText(applicationContext, "Clicked Bookmark.", Toast.LENGTH_SHORT).show()
            }

            override fun onShareClick(view: View, obj: News, position: Int) {
                Toast.makeText(applicationContext, "Clicked Share.", Toast.LENGTH_SHORT).show()
            }

            override fun onMoreClick(view: View, obj: News, position: Int) {
                Toast.makeText(applicationContext, "Clicked More.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initToolbar() {

        toolbar.setNavigationIcon(R.drawable.baseline_menu_black_24)

        if (toolbar.navigationIcon != null) {
           toolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this, R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP)
        }

        toolbar.title = "Grid 5"

        try {
           toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.md_white_1000))
        } catch (e: Exception) {
            Log.e("TEAMPS", "Can't set color.")
        }

        try {
            setSupportActionBar(toolbar)
        } catch (e: Exception) {
            Log.e("TEAMPS", "Error in set support action bar.")
        }

        try {
            if (supportActionBar != null) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        } catch (e: Exception) {
            Log.e("TEAMPS", "Error in set display home as up enabled.")
        }

    }
    //endregion
}

