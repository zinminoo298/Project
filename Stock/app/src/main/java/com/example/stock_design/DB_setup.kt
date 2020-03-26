package com.example.stock_design

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.stock_design.Database.DataBaseHelper
import com.example.stock_design.Import
import com.example.stock_design.R
import kotlinx.android.synthetic.main.activity_db_setup.*


var br_val:Int = 0
var ic_val:Int = 0
var nm_val:Int = 0
var pr_val:Int = 0
var qt_val:Int = 0

var col_br:Int = 0
var col_ic:Int = 0
var col_nm:Int = 0
var col_pr:Int = 0
var col_qt:Int = 0

var col_br1:Int = 0
var col_ic1:Int = 0
var col_nm1:Int = 0
var col_pr1:Int = 0
var col_qt1:Int = 0

var col_br2:Int = 0
var col_ic2:Int = 0
var col_nm2:Int = 0
var col_pr2:Int = 0
var col_qt2:Int = 0


var deli: String? = null
var file_type:String? = null

var types = arrayOf("txt","csv")

class DB_setup : AppCompatActivity() {

    internal lateinit var db:DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db_setup)

        loadbr()
        loadic()
        loadnm()
        loadpr()
        loadqt()
        loadcol()
        loadfileType()

        db = DataBaseHelper(this)
        db.openDatabase()

        val check_br = findViewById<CheckBox>(R.id.ck_bc)
        val check_ic = findViewById<CheckBox>(R.id.ck_ic)
        val check_name = findViewById<CheckBox>(R.id.ck_nm)
        val check_pr = findViewById<CheckBox>(R.id.ck_pr)
        val check_qt = findViewById<CheckBox>(R.id.ck_qt)

        edt_br.setText(col_br.toString())
        edt_ic.setText(col_ic.toString())
        edt_nm.setText(col_nm.toString())
        edt_pr.setText(col_pr.toString())
        edt_qt.setText(col_qt.toString())

        edt_br1.setText(col_br1.toString())
        edt_ic1.setText(col_ic1.toString())
        edt_nm1.setText(col_nm1.toString())
        edt_pr1.setText(col_pr1.toString())
        edt_qt1.setText(col_qt1.toString())

        edt_br2.setText(col_br2.toString())
        edt_ic2.setText(col_ic2.toString())
        edt_nm2.setText(col_nm2.toString())
        edt_pr2.setText(col_pr2.toString())
        edt_qt2.setText(col_qt2.toString())

        if(br_val == 1){
            check_br.isChecked = true
            edt_br.isEnabled = true
            edt_br1.isEnabled = true
            edt_br2.isEnabled = true
        }
        else{
            edt_br.isEnabled = false
            edt_br1.isEnabled = false
            edt_br2.isEnabled = false
        }

        if(ic_val == 1){
            check_ic.isChecked = true
            edt_ic.isEnabled = true
            edt_ic1.isEnabled = true
            edt_ic2.isEnabled = true
        }
        else{
            edt_ic.isEnabled = false
            edt_ic1.isEnabled = false
            edt_ic2.isEnabled = false
        }

        if(nm_val == 1){
            check_name.isChecked = true
            edt_nm.isEnabled = true
            edt_nm1.isEnabled = true
            edt_nm2.isEnabled = true
        }
        else{
            edt_nm.isEnabled = false
            edt_nm1.isEnabled = false
            edt_nm2.isEnabled = false
        }

        if(pr_val == 1){
            check_pr.isChecked = true
            edt_pr.isEnabled = true
            edt_pr1.isEnabled = true
            edt_pr2.isEnabled = true
        }
        else{
            edt_pr.isEnabled = false
            edt_pr1.isEnabled = false
            edt_pr2.isEnabled = false
        }

        if(qt_val == 1){
            check_qt.isChecked = true
            edt_qt.isEnabled = true
            edt_qt1.isEnabled = true
            edt_qt2.isEnabled = true
        }
        else{
            edt_qt.isEnabled = false
            edt_qt1.isEnabled = false
            edt_qt2.isEnabled = false
        }

        check_br.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener(){ buttonView:CompoundButton, ischecked : Boolean->

            if(ischecked){
                Toast.makeText(this,"CK_BR CHECKED",Toast.LENGTH_SHORT).show()
                setbr(1)
                edt_br.isEnabled = true
                edt_br1.isEnabled = true
                edt_br2.isEnabled = true
            }

            else{
                Toast.makeText(this,"CK_BR UNCHECKED",Toast.LENGTH_SHORT).show()
                setbr(0)
                edt_br.isEnabled = false
                edt_br1.isEnabled = false
                edt_br2.isEnabled = false
//                    edt_br.setText("0")
            }
        })

        check_ic.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener(){ buttonView:CompoundButton, ischecked : Boolean->

            if(ischecked){
                Toast.makeText(this,"CK_IC CHECKED",Toast.LENGTH_SHORT).show()
                setic(1)
                edt_ic.isEnabled = true
                edt_ic1.isEnabled = true
                edt_ic2.isEnabled = true

            }

            else{
                Toast.makeText(this,"CK_IC UNCHECKED",Toast.LENGTH_SHORT).show()
                setic(0)
                edt_ic.isEnabled = false
                edt_ic1.isEnabled = false
                edt_ic2.isEnabled = false

//                edt_ic.setText("0")
            }
        })

        check_name.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener(){ buttonView:CompoundButton, ischecked : Boolean->

            if(ischecked){
                Toast.makeText(this,"CK_NM CHECKED",Toast.LENGTH_SHORT).show()
                setnm(1)
                edt_nm.isEnabled = true
                edt_nm1.isEnabled = true
                edt_nm2.isEnabled = true

            }

            else{
                Toast.makeText(this,"CK_NM UNCHECKED",Toast.LENGTH_SHORT).show()
                setnm(0)
                edt_nm.isEnabled = false
                edt_nm1.isEnabled = false
                edt_nm2.isEnabled = false
//                edt_nm.setText("0")
            }
        })

        check_pr.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener(){ buttonView:CompoundButton, ischecked : Boolean->

            if(ischecked){
                Toast.makeText(this,"CK_PR CHECKED",Toast.LENGTH_SHORT).show()
                setpr(1)
                edt_pr.isEnabled = true
                edt_pr1.isEnabled =true
                edt_pr2.isEnabled = true


            }

            else{
                Toast.makeText(this,"CK_PR UNCHECKED",Toast.LENGTH_SHORT).show()
                setpr(0)
                edt_pr.isEnabled = false
                edt_pr1.isEnabled = false
                edt_pr2.isEnabled = false

//                edt_pr.setText("0")
            }
        })

        check_qt.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener(){ buttonView:CompoundButton, ischecked : Boolean->

            if(ischecked){
                Toast.makeText(this,"CK_QTY CHECKED",Toast.LENGTH_SHORT).show()
                setqt(1)
                edt_qt.isEnabled = true
                edt_qt1.isEnabled = true
                edt_qt2.isEnabled = true


            }

            else{
                Toast.makeText(this,"CK_QTY UNCHECKED",Toast.LENGTH_SHORT).show()
                setqt(0)
                edt_qt.isEnabled = false
                edt_qt1.isEnabled = false
                edt_qt2.isEnabled = false
//                edt_qt.setText("0")
            }
        })


        btn_save.setOnClickListener {

            val a =Integer.parseInt(edt_br.text.toString())
            val b =Integer.parseInt(edt_ic.text.toString())
            val c =Integer.parseInt(edt_nm.text.toString())
            val d =Integer.parseInt(edt_pr.text.toString())
            val e =Integer.parseInt(edt_qt.text.toString())

            val a1 =Integer.parseInt(edt_br1.text.toString())
            val b1 =Integer.parseInt(edt_ic1.text.toString())
            val c1 =Integer.parseInt(edt_nm1.text.toString())
            val d1 =Integer.parseInt(edt_pr1.text.toString())
            val e1 =Integer.parseInt(edt_qt1.text.toString())

            val a2 =Integer.parseInt(edt_br2.text.toString())
            val b2 =Integer.parseInt(edt_ic2.text.toString())
            val c2 =Integer.parseInt(edt_nm2.text.toString())
            val d2 =Integer.parseInt(edt_pr2.text.toString())
            val e2 =Integer.parseInt(edt_qt2.text.toString())
            setcol(a,b,c,d,e,a1,b1,c1,d1,e1,a2,b2,c2,d2,e2)

            col_br =Integer.parseInt(edt_br.text.toString())
            col_ic =Integer.parseInt(edt_ic.text.toString())
            col_nm =Integer.parseInt(edt_nm.text.toString())
            col_pr =Integer.parseInt(edt_pr.text.toString())
            col_qt =Integer.parseInt(edt_qt.text.toString())

            col_br1 =Integer.parseInt(edt_br1.text.toString())
            col_ic1 =Integer.parseInt(edt_ic1.text.toString())
            col_nm1 =Integer.parseInt(edt_nm1.text.toString())
            col_pr1 =Integer.parseInt(edt_pr1.text.toString())
            col_qt1 =Integer.parseInt(edt_qt1.text.toString())

            col_br2 =Integer.parseInt(edt_br2.text.toString())
            col_ic2 =Integer.parseInt(edt_ic2.text.toString())
            col_nm2 =Integer.parseInt(edt_nm2.text.toString())
            col_pr2 =Integer.parseInt(edt_pr2.text.toString())
            col_qt2 =Integer.parseInt(edt_qt2.text.toString())

            Toast.makeText(this,"Import Seeting Saved",Toast.LENGTH_SHORT).show()

        }

        btn_goimport.setOnClickListener {

            val intent=Intent(this, Import::class.java)
            startActivity(intent)
        }

        if(file_type == "txt"){
             types = arrayOf("txt","csv")
        }
        if(file_type =="csv"){
             types = arrayOf("csv","txt")
        }

        // Initializing an ArrayAdapter
        val adapter = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            types // Array
        )

        // Set the drop down view resource
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        // Finally, data bind the spinner object with dapter
        spinner2.adapter = adapter

        // Set an on item selected listener for spinner object
        spinner2.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
//                text_view.text = "Spinner selected : ${parent.getItemAtPosition(position).toString()}"
                if (parent.getItemAtPosition(position).toString() == "txt"){
                    spinner.isEnabled = false
                    file_type = "txt"
                    edt_br.visibility = View.VISIBLE
                    edt_br1.visibility = View.VISIBLE
                    edt_ic.visibility = View.VISIBLE
                    edt_ic1.visibility = View.VISIBLE
                    edt_nm.visibility = View.VISIBLE
                    edt_nm1.visibility = View.VISIBLE
                    edt_pr.visibility = View.VISIBLE
                    edt_pr1.visibility = View.VISIBLE
                    edt_qt.visibility = View.VISIBLE
                    edt_qt1.visibility = View.VISIBLE

                    edt_br2.visibility = View.GONE
                    edt_ic2.visibility = View.GONE
                    edt_nm2.visibility = View.GONE
                    edt_pr2.visibility = View.GONE
                    edt_qt2.visibility = View.GONE

                    setfileType("txt")
                }

                if (parent.getItemAtPosition(position).toString() == "csv"){
                    spinner.isEnabled = true
                    file_type = "csv"
                    edt_br.visibility = View.GONE
                    edt_br1.visibility = View.GONE
                    edt_ic.visibility = View.GONE
                    edt_ic1.visibility = View.GONE
                    edt_nm.visibility = View.GONE
                    edt_nm1.visibility = View.GONE
                    edt_pr.visibility = View.GONE
                    edt_pr1.visibility = View.GONE
                    edt_qt.visibility = View.GONE
                    edt_qt1.visibility = View.GONE

                    edt_br2.visibility = View.VISIBLE
                    edt_ic2.visibility = View.VISIBLE
                    edt_nm2.visibility = View.VISIBLE
                    edt_pr2.visibility = View.VISIBLE
                    edt_qt2.visibility = View.VISIBLE
                    setfileType("csv")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }

        val deli_types = arrayOf(",","|",":",";")

        // Initializing an ArrayAdapter
        val adapter1 = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            deli_types // Array
        )

        // Set the drop down view resource
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)

        // Finally, data bind the spinner object with dapter
        spinner.adapter = adapter1
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent:AdapterView<*>, view: View, position: Int, id: Long){
                // Display the selected item text on text view
                if (parent.getItemAtPosition(position).toString() == ","){
                    deli = ","
                }

                if (parent.getItemAtPosition(position).toString() == "|"){
                    deli = "|"
                }

                if (parent.getItemAtPosition(position).toString() == ":"){
                    deli = ":"
                }

                if (parent.getItemAtPosition(position).toString() == ";"){
                    deli = ";"
                    println("OKOKOKOKOK")
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>){
                // Another interface callback
            }
        }

    }
    fun setbr( v:Int) {
        var editor=getSharedPreferences("br", MODE_PRIVATE).edit()
        editor.putInt("val", v)
        editor.apply()
    }

    fun loadbr(){
        var prefs=getSharedPreferences("br", Activity.MODE_PRIVATE)
        val language:Int = prefs.getInt("val", 0)
        br_val = language
    }

    fun setic( v:Int) {
        var editor=getSharedPreferences("ic", MODE_PRIVATE).edit()
        editor.putInt("val1", v)
        editor.apply()
    }

    fun loadic(){
        var prefs=getSharedPreferences("ic", Activity.MODE_PRIVATE)
        val language:Int = prefs.getInt("val1", 0)
        ic_val = language
    }

    fun setnm( v:Int) {
        var editor=getSharedPreferences("nm", MODE_PRIVATE).edit()
        editor.putInt("val2", v)
        editor.apply()
    }

    fun loadnm(){
        var prefs=getSharedPreferences("nm", Activity.MODE_PRIVATE)
        val language:Int = prefs.getInt("val2", 0)
        nm_val = language
    }

    fun setpr( v:Int) {
        var editor=getSharedPreferences("nm", MODE_PRIVATE).edit()
        editor.putInt("val3", v)
        editor.apply()
    }

    fun loadpr(){
        var prefs=getSharedPreferences("nm", Activity.MODE_PRIVATE)
        val language:Int = prefs.getInt("val3", 0)
        pr_val = language
    }

    fun setqt( v:Int) {
        var editor=getSharedPreferences("nm", MODE_PRIVATE).edit()
        editor.putInt("val4", v)
        editor.apply()
    }

    fun loadqt(){
        var prefs=getSharedPreferences("nm", Activity.MODE_PRIVATE)
        val language:Int = prefs.getInt("val4", 0)
        qt_val = language
    }

    fun setcol(a:Int,b:Int,c:Int,d:Int,e:Int,a1:Int,b1:Int,c1:Int,d1:Int,e1:Int,a2:Int,b2:Int,c2:Int,d2:Int,e2:Int){
        var editor1=getSharedPreferences("col_br", MODE_PRIVATE).edit()
        editor1.putInt("a", a)
        editor1.apply()

        var editor2=getSharedPreferences("col_ic", MODE_PRIVATE).edit()
        editor2.putInt("b", b)
        editor2.apply()

        var editor3=getSharedPreferences("col_nm", MODE_PRIVATE).edit()
        editor3.putInt("c", c)
        editor3.apply()

        var editor4=getSharedPreferences("col_pr", MODE_PRIVATE).edit()
        editor4.putInt("d", d)
        editor4.apply()

        var editor5=getSharedPreferences("col_qt", MODE_PRIVATE).edit()
        editor5.putInt("e", e)
        editor5.apply()



        var editor1a=getSharedPreferences("col_br1", MODE_PRIVATE).edit()
        editor1a.putInt("a1", a1)
        editor1a.apply()

        var editor2b=getSharedPreferences("col_ic1", MODE_PRIVATE).edit()
        editor2b.putInt("b1", b1)
        editor2b.apply()

        var editor3c=getSharedPreferences("col_nm1", MODE_PRIVATE).edit()
        editor3c.putInt("c1", c1)
        editor3c.apply()

        var editor4d=getSharedPreferences("col_pr1", MODE_PRIVATE).edit()
        editor4d.putInt("d1", d1)
        editor4d.apply()

        var editor5e=getSharedPreferences("col_qt1", MODE_PRIVATE).edit()
        editor5e.putInt("e1", e1)
        editor5e.apply()



        var editor1a2=getSharedPreferences("col_br2", MODE_PRIVATE).edit()
        editor1a2.putInt("a2", a2)
        editor1a2.apply()

        var editor2b2=getSharedPreferences("col_ic2", MODE_PRIVATE).edit()
        editor2b2.putInt("b2", b2)
        editor2b2.apply()

        var editor3c2=getSharedPreferences("col_nm2", MODE_PRIVATE).edit()
        editor3c2.putInt("c2", c2)
        editor3c2.apply()

        var editor4d2=getSharedPreferences("col_pr2", MODE_PRIVATE).edit()
        editor4d2.putInt("d2", d2)
        editor4d2.apply()

        var editor5e2=getSharedPreferences("col_qt2", MODE_PRIVATE).edit()
        editor5e2.putInt("e2", e2)
        editor5e2.apply()
    }

    fun loadcol(){
        var prefs1=getSharedPreferences("col_br", Activity.MODE_PRIVATE)
        val col1:Int = prefs1.getInt("a", 0)
        col_br = col1

        var prefs2=getSharedPreferences("col_ic", Activity.MODE_PRIVATE)
        val col2:Int = prefs2.getInt("b", 0)
        col_ic = col2

        var prefs3=getSharedPreferences("col_nm", Activity.MODE_PRIVATE)
        val col3:Int = prefs3.getInt("c", 0)
        col_nm = col3

        var prefs4=getSharedPreferences("col_pr", Activity.MODE_PRIVATE)
        val col4:Int = prefs4.getInt("d", 0)
        col_pr = col4

        var prefs5=getSharedPreferences("col_qt", Activity.MODE_PRIVATE)
        val col5:Int = prefs5.getInt("e", 0)
        col_qt = col5


        var prefs1a=getSharedPreferences("col_br1", Activity.MODE_PRIVATE)
        val col1a:Int = prefs1a.getInt("a1", 0)
        col_br1 = col1a

        var prefs2b=getSharedPreferences("col_ic1", Activity.MODE_PRIVATE)
        val col2b:Int = prefs2b.getInt("b1", 0)
        col_ic1 = col2b

        var prefs3c=getSharedPreferences("col_nm1", Activity.MODE_PRIVATE)
        val col3c:Int = prefs3c.getInt("c1", 0)
        col_nm1 = col3c

        var prefs4d=getSharedPreferences("col_pr1", Activity.MODE_PRIVATE)
        val col4d:Int = prefs4d.getInt("d1", 0)
        col_pr1 = col4d

        var prefs5e=getSharedPreferences("col_qt1", Activity.MODE_PRIVATE)
        val col5e:Int = prefs5e.getInt("e1", 0)
        col_qt1 = col5e


        var prefs1a2=getSharedPreferences("col_br2", Activity.MODE_PRIVATE)
        val col1a2:Int = prefs1a2.getInt("a2", 0)
        col_br2 = col1a2

        var prefs2b2=getSharedPreferences("col_ic2", Activity.MODE_PRIVATE)
        val col2b2:Int = prefs2b2.getInt("b2", 0)
        col_ic2 = col2b2

        var prefs3c2=getSharedPreferences("col_nm2", Activity.MODE_PRIVATE)
        val col3c2:Int = prefs3c2.getInt("c2", 0)
        col_nm2 = col3c2

        var prefs4d2=getSharedPreferences("col_pr2", Activity.MODE_PRIVATE)
        val col4d2:Int = prefs4d2.getInt("d2", 0)
        col_pr2 = col4d2

        var prefs5e2=getSharedPreferences("col_qt2", Activity.MODE_PRIVATE)
        val col5e2:Int = prefs5e2.getInt("e2", 0)
        col_qt2 = col5e2
    }

    fun setfileType(f:String){
        var editor=getSharedPreferences("file_type", MODE_PRIVATE).edit()
        editor.putString("f", f)
        editor.apply()
    }

    fun loadfileType(){
        var pref=getSharedPreferences("file_type", Activity.MODE_PRIVATE)
        val file = pref.getString("f", "txt")
        file_type = file
    }

}
