package com.example.stock_design

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.*
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.stock_design.Database.*
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


//var noti: String?="Select Master File"
var master_date: String?="Master File not selected"
var master_path: String?=null
var user_record: String? = null

class MainActivity : AppCompatActivity() {
    internal lateinit var db: DataBaseHelper
    internal lateinit var lbl: TextView
    internal lateinit var btnimport: ImageView
    internal lateinit var scan: Button
    private val DB_PATH="/data/data/com.example.stock_design/databases"
    private val REAL_DATABASE="database.db"
    private var MASTER:String? = null
    private var Tran:String? = null
    private var All:String? = null
    val requestcode=1
    var selectedItems= -1
    var STORAGE_PERMISSION_CODE = 1;


    private lateinit var mToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLoacle()
        loadvalue()
        setContentView(R.layout.activity_main)

        db=DataBaseHelper(this)
        db.openDatabase()
        db.getTransactionSummery()
        db.getMasterSummery()
        summery()

        /*show summery data on main page*/
        val itm_tran=findViewById<TextView>(R.id.tran_itm)
        itm_tran.text=item_tran.toString()

        val qty_transaction=findViewById<TextView>(R.id.tran_qty)
        qty_transaction.text=qty_tran.toString()

//        val newitem=findViewById<TextView>(R.id.new_itm)
//        newitem.text=new_item.toString()

        val mnewitem=findViewById<TextView>(R.id.m_new_itm)
        mnewitem.text=new_item.toString()

        val dt=findViewById<TextView>(R.id.tran_dt)
        dt.text=date.toString()

        val itm_master=findViewById<TextView>(R.id.master_itm)
        itm_master.text=item_master.toString()

        val master_qty=findViewById<TextView>(R.id.master_qty)
        master_qty.text=qty_master.toString()


        /*Drawer for menu setting*/
        val mDrawerLayout=findViewById<DrawerLayout>(R.id.drawlayout)
        mToggle=ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close)

        mDrawerLayout.addDrawerListener(mToggle)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        btn_new.setOnClickListener {
            showDialogAnimation(R.style.DialogSlide, this)
//            val intent = Intent(this, Counting::class.java)
//            startActivity(intent)

        }

        search.setOnClickListener{
            val intent = Intent(this,Search::class.java)
            startActivity(intent)
        }

        clear.setOnClickListener{
            clearDialog(this)
        }

        imp.setOnClickListener{
            val intent = Intent(this,Import_page::class.java)
            startActivity(intent)
        }

        export.setOnClickListener{
            val filepath="/storage/emulated/0/Download/result.csv"
            val file=File(filepath)
            if(file.exists())
            {
                export()
                Toast.makeText(this, "FILE EXPORT SUCCESSFUL", Toast.LENGTH_LONG).show()

            }
            else{
                generateNoteOnSD(this,"/result.csv/")
                if(file.exists())
                {
                    export()
                    Toast.makeText(this, "FILE EXPORT SUCCESSFUL", Toast.LENGTH_LONG).show()
                }

                else{
                    Toast.makeText(this, "EXPORT UNSUCCESSFUL. MAKE SURE TO GIVE STORAGE ACCESS", Toast.LENGTH_LONG).show()
                }

            }
        }

//        val btn_lan = findViewById<Button>(R.id.btn_lan)
//
//        btn_lan.setOnClickListener{
//            changeLanguage()
//        }
        val navigationView: NavigationView=findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->

            // close drawer when item is tapped
            mDrawerLayout.closeDrawers()
            menuItem.isChecked=!menuItem.isChecked

            // Handle navigation view item clicks here.
            when (menuItem.itemId) {

                R.id.nav_clear -> {
                    clearDialog(this)                }

                R.id.nav_new -> {
                    showDialogAnimation(R.style.DialogSlide,this)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }

                R.id.nav_exit -> {
                    finish()
                    System.exit(0)
                }
                R.id.nav_import -> {
//                    importDialog(R.style.DialogSlide,this)
                    val intent = Intent(this,Import::class.java)
                    startActivity(intent)
                }

                R.id.nav_search -> {
                    val intent=Intent(this, Search::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                }

                R.id.nav_lan->{
                    changeLanguage(this)
                }

                R.id.nav_export -> {
                    val filepath="/storage/emulated/0/Download/result.csv"
                    val file=File(filepath)
                    if(file.exists())
                    {
                        export()
                        Toast.makeText(this, "FILE EXPORT SUCCESSFUL", Toast.LENGTH_LONG).show()

                    }
                    else{
                        generateNoteOnSD(this,"/result.csv/")
                        if(file.exists())
                        {
                            export()
                            Toast.makeText(this, "FILE EXPORT SUCCESSFUL", Toast.LENGTH_LONG).show()
                        }

                        else{
                            Toast.makeText(this, "EXPORT UNSUCCESSFUL. MAKE SURE TO GIVE STORAGE ACCESS", Toast.LENGTH_LONG).show()
                        }

                    }



                }
            }


            true
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Storage Permission is Granted",
                Toast.LENGTH_SHORT).show();
        } else {
            requestStoragePermission();
        }

    }
    /*show summery data on main page*/
    private fun summery() {
        db.getTransactionSummery()
        db.getMasterSummery()

        val itm_tran=findViewById<TextView>(R.id.tran_itm)
        itm_tran.text=item_tran.toString()

        val qty_transaction=findViewById<TextView>(R.id.tran_qty)
        qty_transaction.text=qty_tran.toString()

//        val newitem=findViewById<TextView>(R.id.new_itm)
//        newitem.text=new_item.toString()

        val mnewitem=findViewById<TextView>(R.id.m_new_itm)
        mnewitem.text=new_item.toString()

        val dt=findViewById<TextView>(R.id.tran_dt)
        dt.text=date.toString()

        val itm_master=findViewById<TextView>(R.id.master_itm)
        itm_master.text=item_master.toString()

        val master_qty=findViewById<TextView>(R.id.master_qty)
        master_qty.text=qty_master.toString()

    }

    private fun requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE
            )
        }

        else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), STORAGE_PERMISSION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /*dialog page for user record*/
    private fun showDialogAnimation(type: Int,context: Context) {
        val builder=AlertDialog.Builder(this)
        val inflater=this.layoutInflater
        val view=inflater.inflate(R.layout.layout_dialog, null)
        builder.setView(view)
        val dialog: AlertDialog=builder.create()
        dialog.window?.attributes?.windowAnimations=type
        dialog.setMessage(context.getString(R.string.info))
        dialog.show()

        val Date=view.findViewById<TextView>(R.id.edit_date)
        val Location=view.findViewById<TextView>(R.id.edit_location)
        val Name=view.findViewById<TextView>(R.id.edit_name)

        val dateF=SimpleDateFormat("dd/MM/yy", Locale.ENGLISH)
        val date=dateF.format(Calendar.getInstance().time)
        Date.text=date.toString()




        val c=Calendar.getInstance()
        var year=c.get(Calendar.YEAR)
        var month=c.get(Calendar.MONTH)
        var day=c.get(Calendar.DAY_OF_MONTH)

        Date.setOnClickListener {
            val dpd=DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, myear, mmonth, mday ->
                day=mday
                month=mmonth + 1
                year=myear

                Date.text=("" + day + "/" + month + "/" + year)

                record_date=Date.text.toString()
            }, year, month, day)
//            dpd.datePicker.setMinDate(System.currentTimeMillis())
            dpd.show()
        }


        val OK_button=view.findViewById<Button>(R.id.btn_ok)
        OK_button.setOnClickListener {
            if (Date.text.toString() == "" || Name.text.toString() == "" || Location.text.toString() == "") {
                Toast.makeText(this, "Please Fill The Fields", Toast.LENGTH_SHORT).show()
            } else {
                record_location=Location.text.toString()
                record_date=Date.text.toString()
                println("Date" + record_date)
                dialog.dismiss()
                val intent=Intent(this, Counting::class.java)
                startActivity(intent)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                user_record = Name.text.toString()
                println("YOYOYOYOOOYOYOIYO"+Name.text.toString())

            }
        }
    }

    /*export data to csv file*/
    private fun  export() {
        try {
            db=DataBaseHelper(this)
            val db=this.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
            val selectQuery=
                "Select T.date,T.location, T.barcode1,M.onhand_qty,T.qty,(M.onhand_qty-T.qty) as 'DIFFERENCE',T.branch from Transaction_table T Left Outer Join Master M On M.barcode = T.barcode1  "
            val cursor=db.rawQuery(selectQuery, null)
            var rowcount: Int
            var colcount: Int

            val saveFile=File("/sdcard/Download/result.csv")
            val fw=FileWriter(saveFile)


            val bw=BufferedWriter(fw)
            rowcount=cursor.getCount()
            colcount=cursor.getColumnCount()





            if (rowcount>0) {
                cursor!!.moveToFirst()

                for (i in 0 until colcount) {
                    if (i != colcount - 1) {

                        bw.write(cursor!!.getColumnName(i) + ",")

                    } else {

                        bw.write(cursor!!.getColumnName(i))

                    }
                }
                bw.newLine()

                for (i in 0 until rowcount) {
                    cursor!!.moveToPosition(i)

                    for (j in 0 until colcount) {
                        if (j != colcount - 1)
                            bw.write(cursor!!.getString(j) + ",")
                        else
                            if(cursor!!.getString(j) == null)
                                bw.write("null")
                            else
                                bw.write(cursor!!.getString(j))
//                                    bw.write(cursor!!.getString(j))
                    }
                    bw.newLine()
                }
                bw.flush()

            }

        }
        catch (ex: Exception) {
            ex.printStackTrace()

        }

    }

    /*import csv file to database*/
//    private fun importDialog(type: Int,context: Context) {
//        val builder=AlertDialog.Builder(this)
//        val inflater=this.layoutInflater
//        val view=inflater.inflate(R.layout.import__dialog, null)
//        builder.setView(view)
//        val dialog: AlertDialog=builder.create()
//        dialog.window?.attributes?.windowAnimations=type
//        dialog.setMessage(context.getString(R.string.open_master))
//        lbl=EditText(this)
//        lbl=view.findViewById(R.id.edit_master)
//        lbl.text=noti.toString()
//        dialog.show()
//
//        db=DataBaseHelper(this)
//
//        btnimport=view.findViewById(R.id.img_import)
//
//        btnimport.setOnClickListener {
//            val fileintent=Intent(Intent.ACTION_GET_CONTENT)
//            fileintent.type="text/csv"
//            try {
//                startActivityForResult(fileintent, requestcode)
//            } catch (e: ActivityNotFoundException) {
//                lbl.text="No activity can handle picking a file. Showing alternatives."
//            }
//        }
//
//        scan=view.findViewById<Button>(R.id.btn_scan)
//        scan.setOnClickListener {
//
//            showDialogAnimation(R.style.DialogSlide1, this)
//
//        }
//
////        val clear=view.findViewById<Button>(R.id.btn_clear)
////        clear.setOnClickListener {
////            noti="Select Master File"
////            lbl.text="Select Master File"
////            val myPath=DB_PATH + REAL_DATABASE
////            val db=this.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
////            val tableName="Master"
////            db.execSQL("delete from $tableName")
////            summery()
////        }
//
//    }
    fun generateNoteOnSD(context: Context, sFileName: String) {
        try {
            val root=File( "/storage/emulated/0/Download/")
            if (!root.exists()) {
                root.mkdirs()
            }
            val gpxfile=File(root, sFileName)
            val writer=FileWriter(gpxfile)
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
    /*import main function*/
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null)
            return
        if (requestCode==requestcode) {
            val filepath=data.data
            val cursor=contentResolver.openInputStream(android.net.Uri.parse(filepath.toString()))
            lbl.text=filepath.toString()
            master_path=filepath.toString()
            noti=cursor.toString()
            val db=this.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
            val tableName="Master"
            db.execSQL("delete from $tableName")
            try {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        val file=InputStreamReader(cursor)

                        val buffer=BufferedReader(file)
                        buffer.readLine()
                        val contentValues=ContentValues()
                        db.beginTransaction()
                        while(true) {
                            val line=buffer.readLine()
                            if (line == null) break
                            val str=line.split(",".toRegex(), 4)
                                .toTypedArray()

                            val barcode=str[0].toString()
                            val item_code=str[1].toString()
                            val onhand_qty=str[2].toString()
                            val description=str[3].toString()

                            contentValues.put("barcode", barcode)
                            contentValues.put("item_code", item_code)
                            contentValues.put("onhand_qty", onhand_qty)
                            contentValues.put("description", description)
                            db.insert(tableName, null, contentValues)
                        }

                        db.setTransactionSuccessful()

                        val dateF=SimpleDateFormat("dd/MM/yy", Locale.getDefault())
                        val date=dateF.format(Calendar.getInstance().time)
                        master_date=date.toString()

                        db.endTransaction()
                        summery()
                    } catch (e: IOException) {
                        if (db.inTransaction())
                            db.endTransaction()
                        val d=Dialog(this)
                        d.setTitle(e.message.toString() + "first")
                        d.show()
                    }

                } else {
                    if (db.inTransaction())
                        db.endTransaction()
                    val d=Dialog(this)
                    d.setTitle("Only CSV files allowed")
                    d.show()
                }
            } catch (ex: Exception) {
                if (db.inTransaction())
                    db.endTransaction()

                val d=Dialog(this)
                d.setTitle(ex.message.toString() + "second")
                d.show()
            }

        }

    }
    private fun showDialog() {
        // Late initialize an alert dialog object
        lateinit var dialog: androidx.appcompat.app.AlertDialog


        // Initialize a new instance of alert dialog builder object
        val builder=androidx.appcompat.app.AlertDialog.Builder(this)

        // Set a title for alert dialog
//        builder.setTitle("Wanna Quit?")

        // Set a message for alert dialog
        builder.setMessage(R.string.sure )


        // On click listener for dialog buttons
        val dialogClickListener=DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    val db=this.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
                    val tableName="Transaction_table"
                    db.execSQL("delete from $tableName")
                    dialog.dismiss()
                    val a=Intent(this, MainActivity::class.java)
                    a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(a)
                    Toast.makeText(this,"Database Cleared",Toast.LENGTH_SHORT).show()
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


    private fun clearDialog(context: Context){
        lateinit var dialog: AlertDialog
        val listItems=arrayOf(context.getString(R.string.master1), context.getString(R.string.tran),context.getString(R.string.all))
        val builder=AlertDialog.Builder(this)

        val checkvalue=booleanArrayOf(
            false,
            false        )
        builder.setTitle(R.string.c_data)
        builder.setSingleChoiceItems(listItems,-1,DialogInterface.OnClickListener(){dialoginterface,i->

            if (i == 0) {
                MASTER = "Master"
                Tran = null
                All = null
                println(MASTER)
                println(Tran)
                println(All)
            }

            if (i == 1) {
                Tran = "Transaction_table"
                MASTER = null
                All = null
                println(MASTER)
                println(Tran)
                println(All)
            }

            if (i== 2){
                All = "Transaction_table"
                Tran = null
                MASTER = "Master"
                println(MASTER)
                println(Tran)
                println(All)
            }

            if (i!=0 && i!=1 && i!=2){
                MASTER = null
                Tran = null
                All = null
                println(MASTER)
                println(Tran)
                println(All)
            }
        })
//        builder.setMultiChoiceItems(listItems,checkvalue,DialogInterface.OnMultiChoiceClickListener() {dialogInterface,i,_->
//
//
//            if (i == 0) {
//                MASTER = "Master"
//                println(MASTER)
//            }
//
//            if (i == 1) {
//                Tran = "Transaction_table"
//                println(Tran)
//            }
//
//        })

        builder.setPositiveButton("Clear",  DialogInterface.OnClickListener(){ _, _ ->

            val db=this.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)



            if(MASTER==null){
                println("Master is null")
            }
            else {
                db.execSQL("delete from $MASTER")
            }


            if(Tran==null){
                println("Tran is null")
            }
            else {
                db.execSQL("delete from $Tran")
            }

            if(All==null){
                println("ALL is null")
            }
            else {
                db.execSQL("delete from $All")
                db.execSQL("delete from $MASTER")
            }

            if(MASTER == null && Tran == null && All == null){
                Toast.makeText(this,"Select one option", Toast.LENGTH_SHORT).show()
            }

            MASTER = null
            Tran = null
            All = null

            println(MASTER+Tran+All)

            dialog.dismiss()
            summery()
        })

        dialog= builder.create()
        dialog.show()
    }

//    private fun clearDialog() {
//        // Late initialize an alert dialog object
//        lateinit var dialog: androidx.appcompat.app.AlertDialog
//
//
//        // Initialize a new instance of alert dialog builder object
//        val builder=androidx.appcompat.app.AlertDialog.Builder(this)
//
//        // Set a message for alert dialog
//        builder.setMessage("Are you sure??")
//
//
//        // On click listener for dialog buttons
//        val dialogClickListener=DialogInterface.OnClickListener { _, which ->
//            when (which) {
//                DialogInterface.BUTTON_POSITIVE -> {
//                    val db=this.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
//                    val tableName="Transaction_table"
//                    db.execSQL("delete from $tableName")
//                    dialog.dismiss()
//                    summery()
//                    startActivity(Intent(applicationContext, MainActivity::class.java))
//                    Toast.makeText(this,"Database Cleared",Toast.LENGTH_SHORT).show()
//                }
//                DialogInterface.BUTTON_NEUTRAL -> {
//                    dialog.dismiss()
//                    summery()
//                }
//            }
//        }
//        // Set the alert dialog positive/yes button
//        builder.setPositiveButton("YES", dialogClickListener)
//
//        // Set the alert dialog neutral/cancel button
//        builder.setNeutralButton("CANCEL", dialogClickListener)
//
//
//        // Initialize the AlertDialog using builder object
//        dialog=builder.create()
//
//        // Finally, display the alert dialog
//        dialog.show()
//    }

    private fun changeLanguage(context: Context){
        lateinit var dialog: AlertDialog
        val listItems=arrayOf(context.getString(R.string.english),context.getString(R.string.thai),context.getString(R.string.myanmar))
        val builder=AlertDialog.Builder(this)
        builder.setTitle(R.string.c_language)
        builder.setSingleChoiceItems(listItems,selectedItems,DialogInterface.OnClickListener() {dialogInterface,i->
            if (i == 0) {
                setLocale("en")
                recreate()
            }

            if (i == 1) {
                setLocale("th")
                recreate()
            }

            if (i == 2) {
                setLocale("my")
                recreate()
            }
            setvalue(i)
            dialogInterface.dismiss()

        })
        dialog= builder.create()
        dialog.show()

    }

    private fun setLocale( lang:String) {

        val locale=Locale(lang)
        Locale.setDefault(locale)
        val config=Configuration()
        config.locale=locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        var editor=getSharedPreferences("Settings", MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()

    }

    private fun loadLoacle(){
        var prefs=getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language:String? = prefs.getString("My_Lang","")
        println("RESULT"+language)
        setLocale(language!!)
    }

    private fun setvalue( v:Int) {

        var editor=getSharedPreferences("Settings", MODE_PRIVATE).edit()
        editor.putInt("value", v)
        editor.apply()

    }

    private fun loadvalue(){
        var prefs=getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language:Int? = prefs.getInt("value",0)
        selectedItems = prefs.getInt("value",0)
        println("RESULT"+language)
        setvalue(language!!)

    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (mToggle.onOptionsItemSelected(item)) {

            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mToggle.onConfigurationChanged(newConfig)
    }


}
