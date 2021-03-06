package com.panaceasoft.pskotlinmaterial.activity.uicomponent.tabs

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.panaceasoft.pskotlinmaterial.R
import com.panaceasoft.pskotlinmaterial.fragment.uicomponent.container.tablayout.UiContainerTabLayoutTab1Fragment
import com.panaceasoft.pskotlinmaterial.fragment.uicomponent.container.tablayout.UiContainerTabLayoutTab2Fragment
import com.panaceasoft.pskotlinmaterial.fragment.uicomponent.container.tablayout.UiContainerTabLayoutTab3Fragment
import com.panaceasoft.pskotlinmaterial.utils.common_adapter.ViewPagerAdapter
import kotlinx.android.synthetic.main.ui_tabs_scrollable_tab_layout_activity.*

class UiTabsScrollableTabLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_tabs_scrollable_tab_layout_activity)

        initToolbar()

        setupViewPager(viewPager)

        tabLayout.setupWithViewPager(viewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(UiContainerTabLayoutTab1Fragment(), "Tab 1")
        adapter.addFragment(UiContainerTabLayoutTab2Fragment(), "Tab 2")
        adapter.addFragment(UiContainerTabLayoutTab3Fragment(), "Tab 3")
        adapter.addFragment(UiContainerTabLayoutTab1Fragment(), "Tab 4")
        adapter.addFragment(UiContainerTabLayoutTab2Fragment(), "Tab 5")
        adapter.addFragment(UiContainerTabLayoutTab3Fragment(), "Tab 6")
        adapter.addFragment(UiContainerTabLayoutTab3Fragment(), "Tab 7")
        viewPager.adapter = adapter
    }

    //region Init Toolbar

    private fun initToolbar() {

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_black_24)

        if (toolbar.navigationIcon != null) {
            toolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this, R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP)
        }

        toolbar.title = "Scrollable Tab Layout"

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}