package com.example.stock_design

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import com.example.stock_design.Database.*
import com.example.stock_design.Modle.Item
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_counting.*


var record_location: String?=null
var record_date: String?=null
var record_code: String?=null
var master_record: Int?=0
var tran_qty: Int?=0
var record_id: String?=null
var up_tran_qty: Int?=0

class Counting : AppCompatActivity() {


    val quan: Int=1
    internal lateinit var db: DataBaseHelper
    internal lateinit var gg:MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gg = MainActivity()
        setContentView(R.layout.activity_counting)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        db=DataBaseHelper(this)//Initiate the database

        var ID=findViewById<EditText>(R.id.edt_id)

        txt_date.text=record_date
        txt_location.text=record_location

        /*Add data to database*/
//        btn_add.setOnClickListener {
//
//            if (edt_id.text.toString() == "") {
//                Toast.makeText(this, "Add The Barcode Please!!", Toast.LENGTH_SHORT).show()
//            } else {
//                record_code=edt_id.text.toString()
//                db.viewIC()
//
//                val item=Item(
//                    edt_id.text.toString(),
//                    user_record.toString(),
//                    view_ic.toString(),
//                    Integer.parseInt(quan.toString()),
//                    record_date.toString(),
//                    record_location.toString()
//                )
//                record_code=edt_id.text.toString()
//                db.addItem(item)
////                db.checkId(item)
//
//                db.viewData()
//
//                txt_tran_qty.setText(quantity2.toString())
//                txt_master_qty.text=quantity1.toString()
//                txt_description.text=master_name.toString()
//                txt_code.text=item_code
//
//                if (master_name == "new item") {
//                    txt_master_qty.text=""
//                    slash.text=""
//                } else {
//                    txt_master_qty.text=quantity1.toString()
//                    slash.text="/"
//                }
//
//                tran_qty=txt_tran_qty.text.toString().toInt()
//                record_id=edt_id.text.toString()
//                edt_id.text=null
//            }
//
//        }

        edt_id.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->

            //            if ( event.keyCode == KeyEvent.FLAG_EDITOR_ACTION && event.action == KeyEvent.ACTION_UP) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (edt_id.text.toString() == "") {
                    Toast.makeText(this, "Add The Barcode Please!!", Toast.LENGTH_SHORT).show()
                    println("1")
                    edt_id.requestFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(edt_id.getWindowToken(), 0)
                } else {
                    if (edt_qty.text.toString() == "" || edt_qty.text.toString() == "0") {
                        Toast.makeText(this, "Add The Quantity Please!!", Toast.LENGTH_SHORT).show()
                        println("2")
                    }else {
                        record_code=edt_id.text.toString()
                        db.viewIC()
                        println("3")
                        val item=Item(
                            edt_id.text.toString(),
                            user_record.toString(),
                            view_ic.toString(),
                            Integer.parseInt(quan.toString()),
                            record_date.toString(),
                            record_location.toString()
                        )
                        record_code=edt_id.text.toString()
                        db.addItem(item)
//                db.checkId(item)

                        db.viewData()

                        txt_tran_qty.setText(quantity2.toString())
                        txt_master_qty.text=quantity1.toString()
                        txt_description.text=master_name.toString()
                        txt_code.text=item_code

                        if (master_name == "new item") {
                            txt_master_qty.text=""
                            slash.text=""
                        } else {
                            txt_master_qty.text=quantity1.toString()
                            slash.text="/"
                        }

                        tran_qty=txt_tran_qty.text.toString().toInt()
                        record_id=edt_id.text.toString()
                        edt_id.text=null
                    }
                }
            }

            false
        })

        /*Add data to database by onKeyListener*/
        edt_id.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->

            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_DOWN) {
                val ii: String=ID.text.toString()
                var id: String

                try {
                    id=ii
                    println(id)
                    Integer.parseInt(quan.toString())
                    record_code.toString()
                    println("GG")


                    edt_id.text.toString()
                    record_code=edt_id.text.toString()
                    db.viewIC()
                    val item=Item(
                        edt_id.text.toString(),
                        user_record.toString(),
                        view_ic.toString(),
                        Integer.parseInt(quan.toString()),
                        record_date.toString(),
                        record_location.toString()
                    )
                    record_code=edt_id.text.toString()
                    db.addItem(item)
//                    db.checkId(item)
                    db.viewData()
                    txt_tran_qty.setText(quantity2.toString())
                    txt_master_qty.text=quantity1.toString()
                    txt_description.text=master_name.toString()
                    txt_code.text=item_code

                    if (master_name == "new item") {
                        txt_master_qty.text=""
                        slash.text=""
                    } else {
                        txt_master_qty.text=quantity1.toString()
                        slash.text="/"
                    }

                    tran_qty=txt_tran_qty.text.toString().toInt()
                    record_id=edt_id.text.toString()
                    edt_id.text=null
//                    val imm =
//                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//                    imm.showSoftInput(edt_id, InputMethodManager.SHOW_IMPLICIT)
                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                }
                minus.isFocusable = false
                update.isFocusable = false
                plus.isFocusable = false
                edt_id.requestFocus()

//                edt_id.text=null
//                edt_id.requestFocus()
            }

            false

        })

        plus.setOnClickListener{
            var i =Integer.parseInt(txt_tran_qty.text.toString())
            if(txt_tran_qty.text.toString() == ""){
                Toast.makeText(this,"Please Scan First",Toast.LENGTH_SHORT).show()
            }
            else {
                i++
                var j = i++
                txt_tran_qty.text = j.toString()
            }
        }

        minus.setOnClickListener{
            var i = Integer.parseInt(txt_tran_qty.text.toString())
            if(txt_tran_qty.text.toString() == ""){
                Toast.makeText(this,"Please Scan First",Toast.LENGTH_SHORT).show()
            }
            else {
                if(Integer.parseInt(txt_tran_qty.text.toString()) == 0 || Integer.parseInt(txt_tran_qty.text.toString()) < 0){
                    Toast.makeText(this,"Quantity must be over 0",Toast.LENGTH_SHORT).show()
                    txt_tran_qty.text = "1"
                }
                i--
                var j = i--
                txt_tran_qty.text = j.toString()
            }
        }

        update.setOnClickListener {
//            showDialogAnimation(R.style.DialogSlide, "Edit Quantity")
            val item=Item(
                record_id.toString(),
                user_record.toString(),
                view_ic.toString(),
                Integer.parseInt(txt_tran_qty.text.toString()),
                record_date.toString(),
                record_location.toString()
            )
            db.updateItem(item)
//            up_tran_qty=txt_tran_qty.text.toString().toInt()
//            txt_tran_qty.text=up_tran_qty.toString()
            Toast.makeText(this, "Item Updated", Toast.LENGTH_LONG).show()
        }

        /*Bottom Nav Bar actions*/
//        val bottomnavigationview: BottomNavigationView=findViewById(R.id.bottom_navigation)
//        bottomnavigationview.setOnNavigationItemSelectedListener { menuItem ->
//
//            when (menuItem.itemId) {
//                R.id.action_edit -> {
//                    if (txt_code.text == "") {
//                        Toast.makeText(this, "Please scan first", Toast.LENGTH_SHORT).show()
//                    } else {
//                        showDialogAnimation(R.style.DialogSlide, "Edit Quantity")
//                    }
//                }
//
//                R.id.action_delete -> {
//                    Delete()
//                }
//
//                R.id.action_close -> {
//                    showDialog()
//                }
//
//            }
//            true
//
//        }
    }

    /*Delete data from database*/
    private fun Delete() {
        if (txt_code.text.toString() == "") {
            Toast.makeText(this, "Add The Barcode Please!!", Toast.LENGTH_SHORT).show()
        } else {
            val item=Item(
                record_id.toString(),
                user_record.toString(),
                view_ic.toString(),
                Integer.parseInt(quan.toString()),
                record_date.toString(),
                record_location.toString()
            )
            db.deleteItem(item)
            Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show()
            txt_tran_qty.text=""
            txt_master_qty.text=""
            txt_description.text=""
            slash.text=""
            txt_code.text=""
        }
    }

    /*show dialog for edit function*/
    private fun showDialogAnimation(type: Int, message: String) {
        val builder=android.app.AlertDialog.Builder(this)
        val inflater=this.layoutInflater
        val view=inflater.inflate(R.layout.edit_layout, null)
        builder.setView(view)
        val dialog: android.app.AlertDialog=builder.create()
        dialog.window?.attributes?.windowAnimations=type
        dialog.setMessage(message)

        var tran_edit=view.findViewById<EditText>(R.id.edt_edit)
        tran_edit.setText(txt_tran_qty.text.toString())

        dialog.show()

        var btn_edt=view.findViewById<Button>(R.id.btn_edit)
        btn_edt.setOnClickListener {
            val item=Item(
                record_id.toString(),
                user_record.toString(),
                view_ic.toString(),
                Integer.parseInt(tran_edit.text.toString()),
                record_date.toString(),
                record_location.toString()
            )
            db.updateItem(item)
            up_tran_qty=tran_edit.text.toString().toInt()
            txt_tran_qty.text=up_tran_qty.toString()
            Toast.makeText(this, "Item Updated", Toast.LENGTH_LONG).show()
            dialog.dismiss()

        }


    }

    /*Dialog to confirm quitting ths scan activity*/
    private fun showDialog() {
        // Late initialize an alert dialog object
        lateinit var dialog: AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder=AlertDialog.Builder(this)

        // Set a title for alert dialog
        builder.setTitle("Wanna Quit?")

        // Set a message for alert dialog
        builder.setMessage("Are you sure??")


        // On click listener for dialog buttons
        val dialogClickListener=DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val a=Intent(this, MainActivity::class.java)
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(a)
                }
                DialogInterface.BUTTON_NEUTRAL -> {
                    dialog.dismiss()
                }
            }
        }
        // Set the alert dialog positive/yes button
        builder.setPositiveButton("YES", dialogClickListener)

        // Set the alert dialog neutral/cancel button
        builder.setNeutralButton("CANCEL", dialogClickListener)


        // Initialize the AlertDialog using builder object
        dialog=builder.create()

        // Finally, display the alert dialog
        dialog.show()
    }

    /*override title bar back key*/
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }



    override fun onBackPressed() {
        showDialog()
//         super.onBackPressed()
        return
    }
}



