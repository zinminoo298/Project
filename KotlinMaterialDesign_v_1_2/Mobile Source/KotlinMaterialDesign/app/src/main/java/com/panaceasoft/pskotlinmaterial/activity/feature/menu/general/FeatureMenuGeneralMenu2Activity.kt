package com.panaceasoft.pskotlinmaterial.activity.feature.menu.general

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.panaceasoft.pskotlinmaterial.R
import com.panaceasoft.pskotlinmaterial.utils.Utils

class FeatureMenuGeneralMenu2Activity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    internal lateinit var toolbar: Toolbar
    internal lateinit var drawer: DrawerLayout
    internal lateinit var headerLayout: View
    internal lateinit var drawerLinearLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.feature_menu_general_menu_2_activity)

        initUI()
    }

    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.itemId

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true

    }

    private fun initUI() {
        initToolbar()

        drawer = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        if (Utils.isRTL) {
            navigationView.textDirection = View.TEXT_DIRECTION_RTL
        } else {
            navigationView.textDirection = View.TEXT_DIRECTION_LTR
        }

        headerLayout = navigationView.getHeaderView(0)
        val userImageView = headerLayout.findViewById<ImageView>(R.id.userImageView)
        Utils.setCircleImageToImageView(this, userImageView, R.drawable.profile1, 0, 0)

        drawerLinearLayout = headerLayout.findViewById(R.id.drawerLinearLayout)

    }

    fun onMenuClicked(view: View) {
        resetMenuColor()

        selectMenu(view)

        drawer.closeDrawer(GravityCompat.START)
    }

    private fun selectMenu(view: View) {
        try {
            val imageView = view as ImageView
            if (imageView.id == R.id.homeImageView) {
                Toast.makeText(this, "Clicked Home.", Toast.LENGTH_SHORT).show()
            } else if (imageView.id == R.id.announceImageView) {
                Toast.makeText(this, "Clicked Announce.", Toast.LENGTH_SHORT).show()
            } else if (imageView.id == R.id.profileImageView) {
                Toast.makeText(this, "Clicked Graph.", Toast.LENGTH_SHORT).show()
            } else if (imageView.id == R.id.clockImageView) {
                Toast.makeText(this, "Clicked History.", Toast.LENGTH_SHORT).show()
            } else if (imageView.id == R.id.profileImageView) {
                Toast.makeText(this, "Clicked Profile.", Toast.LENGTH_SHORT).show()
            } else if (imageView.id == R.id.infoImageView) {
                Toast.makeText(this, "Clicked Info.", Toast.LENGTH_SHORT).show()
            } else if (imageView.id == R.id.settingImageView) {
                Toast.makeText(this, "Clicked Setting.", Toast.LENGTH_SHORT).show()
            } else if (imageView.id == R.id.powerImageView) {
                Toast.makeText(this, "Clicked Power.", Toast.LENGTH_SHORT).show()
            }
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP)
            imageView.setBackgroundColor(ContextCompat.getColor(this,R.color.md_grey_700))
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun resetMenuColor() {

        try {
            val color = ContextCompat.getColor(this, R.color.md_grey_600)

            for (index in 0 until drawerLinearLayout.childCount) {
                val nextChild = drawerLinearLayout.getChildAt(index)

                if (nextChild is ImageView) {
                    nextChild.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                    nextChild.setBackground(null)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun initToolbar() {

        toolbar = findViewById(R.id.toolbar)

        toolbar.setNavigationIcon(R.drawable.baseline_menu_black_24)

        if (toolbar.navigationIcon != null) {
            toolbar.navigationIcon?.setColorFilter(ContextCompat.getColor(this, R.color.md_white_1000), PorterDuff.Mode.SRC_ATOP)
        }

        toolbar.title = "Menu 2"

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

