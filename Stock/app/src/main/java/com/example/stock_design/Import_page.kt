package com.example.stock_design

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_test.*

class Import_page : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import_page)

        im_setting.setOnClickListener{
            val intent = Intent(this,DB_setup::class.java)
            startActivity(intent)
        }

        im_file.setOnClickListener{
            val intent = Intent(this,Import::class.java)
            startActivity(intent)
        }
    }
}
