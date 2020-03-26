package com.example.stock_design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.io.File

class Test : AppCompatActivity() {
    private lateinit var mToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)




//        val mDrawerLayout=findViewById<DrawerLayout>(R.id.drl)
//        mToggle= ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)
//
//        mDrawerLayout.addDrawerListener(mToggle)
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setHomeButtonEnabled(true)
//
//        val navigationView: NavigationView =findViewById(R.id.nav_view1)
//        navigationView.setNavigationItemSelectedListener { menuItem ->
//
//            // close drawer when item is tapped
//            mDrawerLayout.closeDrawers()
//            menuItem.isChecked=!menuItem.isChecked
//
//            // Handle navigation view item clicks here.
//            when (menuItem.itemId) {
//
//                R.id.nav_clear -> {
//                                  }
//
//                R.id.nav_new -> {
//
//                }
//
//                R.id.nav_exit -> {
//
//                }
//                R.id.nav_import -> {
//
//                }
//
//                R.id.nav_search -> {
//
//                }
//
//                R.id.nav_lan->{
//
//                }
//
//                R.id.nav_export -> {
//
//                }
//            }
//
//
//            true
//        }

    }
}
