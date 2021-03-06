package com.panaceasoft.pskotlinmaterial.activity.feature.timeline.general

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.panaceasoft.pskotlinmaterial.R
import com.panaceasoft.pskotlinmaterial.`object`.GeneralInboxList
import com.panaceasoft.pskotlinmaterial.adapter.feature.timeline.general.FeatureTimelineGeneralTimeline1Adapter
import com.panaceasoft.pskotlinmaterial.repository.general.GeneralInboxListRepository
import kotlinx.android.synthetic.main.feature_timeline_general_timeline_1_activity.*

class FeatureTimelineGeneralTimeline1Activity : AppCompatActivity() {

    private lateinit var generalLists: List<GeneralInboxList>

    // ui variables
    private lateinit var adapter: FeatureTimelineGeneralTimeline1Adapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.feature_timeline_general_timeline_1_activity)

        initData()

        initUI()

        initDataBindings()

        initActions()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun initData() {

        // get place list
        generalLists = GeneralInboxListRepository.generalInboxList

    }

    private fun initUI() {

        initToolbar()

        // get list adapter
        adapter = FeatureTimelineGeneralTimeline1Adapter(generalLists)

        // get recycler view
        val mLayoutManager = LinearLayoutManager(applicationContext)
        reviewRecyclerView.layoutManager = mLayoutManager
        reviewRecyclerView.itemAnimator = DefaultItemAnimator()
        reviewRecyclerView.isNestedScrollingEnabled = false

    }

    private fun initDataBindings() {

        // bind adapter to recycler
        reviewRecyclerView.adapter = adapter
    }

    private fun initActions() {

        adapter.setOnItemClickListener(object : FeatureTimelineGeneralTimeline1Adapter.OnItemClickListener {
            override fun onItemClick(view: View, obj: GeneralInboxList, position: Int) {
                Toast.makeText(this@FeatureTimelineGeneralTimeline1Activity, "Selected : " + obj.name, Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun initToolbar() {

        toolbar.setNavigationIcon(R.drawable.baseline_menu_black_24)

        if (toolbar.navigationIcon != null) {
            toolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this, R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP)
        }

        toolbar.title = "Timeline 1 (Inbox)"

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
}
