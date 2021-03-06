package com.panaceasoft.pskotlinmaterial.activity.uicomponent.searchview

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.panaceasoft.pskotlinmaterial.R
import kotlinx.android.synthetic.main.ui_search_view_search_view_basic_activity.*
import java.util.*

class UiSearchViewSearchViewBasicActivity : AppCompatActivity() {

    internal lateinit var list: ArrayList<String>
    internal lateinit var adapter: ArrayAdapter<String>

    internal lateinit var fruits: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ui_search_view_search_view_basic_activity)

        initToolbar()

        fruits = resources.getStringArray(R.array.fruits)

        list = ArrayList(Arrays.asList(*fruits))
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        listView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (list.contains(query)) {

                    adapter.filter.filter(query)
                } else {

                    Toast.makeText(applicationContext, "No Match Found", Toast.LENGTH_SHORT).show()
                }

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {

                adapter.filter.filter(newText)
                return false
            }
        })
    }

    //region Init Toolbar

    private fun initToolbar() {

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_black_24)

        if (toolbar.navigationIcon != null) {
            toolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this, R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP)
        }

        toolbar.title = "Basic SearchView"

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
