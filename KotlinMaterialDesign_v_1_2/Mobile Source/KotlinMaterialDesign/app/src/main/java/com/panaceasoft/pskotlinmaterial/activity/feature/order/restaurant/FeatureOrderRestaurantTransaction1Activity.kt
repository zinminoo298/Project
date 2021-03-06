package com.panaceasoft.pskotlinmaterial.activity.application.restaurant.transaction

import android.graphics.Paint
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.panaceasoft.pskotlinmaterial.R
import kotlinx.android.synthetic.main.app_restaurant_transaction_transaction_1_activity.*

class FeatureOrderRestaurantTransaction1Activity : AppCompatActivity() {

    //region override functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feature_order_restaurant_transaction_1_activity)

        initToolbar()

        initActions()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_clear_all, menu)
        return super.onCreateOptionsMenu(menu)
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

    private fun initActions() {

        viewDetailsTextView1.paintFlags = viewDetailsTextView1.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        viewDetailsTextView1.setOnClickListener {Toast.makeText(applicationContext, "Click View Details", Toast.LENGTH_SHORT).show() }

        viewDetailsTextView2.paintFlags = viewDetailsTextView2.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        viewDetailsTextView2.setOnClickListener {Toast.makeText(applicationContext, "Click View Details", Toast.LENGTH_SHORT).show() }

        viewDetailsTextView3.paintFlags = viewDetailsTextView3.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        viewDetailsTextView3.setOnClickListener {Toast.makeText(applicationContext, "Click View Details", Toast.LENGTH_SHORT).show() }

        viewDetailsTextView4.paintFlags = viewDetailsTextView4.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        viewDetailsTextView4.setOnClickListener {Toast.makeText(applicationContext, "Click View Details", Toast.LENGTH_SHORT).show() }
    }

    private fun initToolbar() {

        toolbar.setNavigationIcon(R.drawable.baseline_menu_black_24)

        if (toolbar.navigationIcon != null) {
            toolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this,R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP)
        }

        toolbar.title = "Order 1"

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
