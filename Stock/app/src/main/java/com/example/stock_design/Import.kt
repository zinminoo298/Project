package com.example.stock_design

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.*
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.stock_design.Database.DataBaseHelper
import kotlinx.android.synthetic.main.activity_import.*
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.lang.StringBuilder

var noti: String?="Select Master File"
var file:String? =null
var line:Int? = 0
var com_check:String? = null
var in_line:Int? = 0
var code_check:String? =null




class Import : AppCompatActivity() {

    val requestcode = 1
    private var progressBar: ProgressBar? = null
    internal lateinit var lbl: TextView
    internal lateinit var cond: TextView
    internal lateinit var btnimport: EditText
    internal lateinit var db: DataBaseHelper
    internal lateinit var import: Button
    internal lateinit var exit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_import)

        loadbr()
        loadic()
        loadnm()
        loadpr()
        loadqt()
        loadcol()
        loadfileType()

        btn_choose.setOnClickListener {
            importDialog(R.style.DialogSlide, this)
        }
    }

    private fun importDialog(type: Int, context: Context) {
        val builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val view = inflater.inflate(R.layout.import__dialog, null)
        builder.setView(view)
        val dialog: AlertDialog = builder.create()
        dialog.window?.attributes?.windowAnimations = type
        dialog.setMessage("OPEN MASTER")
        lbl = TextView(this)
        lbl = view.findViewById(R.id.edit_filechoose)
        lbl.text = noti.toString()
        progressBar = view.findViewById(R.id.progress_bar)
        progressBar!!.visibility = View.GONE
        cond = view.findViewById(R.id.in_cond)
        cond.setText("Importing ...")
        cond!!.visibility = View.GONE
        dialog.show()

        db = DataBaseHelper(this)
        val async = AsyncTaskRunner(this)


        lbl.setOnClickListener {
            cond!!.visibility = View.GONE
            val fileintent = Intent(Intent.ACTION_GET_CONTENT)
            fileintent.type = "*t/*"
            try {
                startActivityForResult(fileintent, requestcode)
                dialog.show()
            } catch (e: ActivityNotFoundException) {
                lbl.text = "No activity can handle picking a file. Showing alternatives."
            }
        }

        import = view.findViewById<Button>(R.id.btn_import)
        import.setOnClickListener {
            //            progressBar!!.visibility = View.VISIBLE
            cond.setText("Importing ...")
            cond!!.visibility = View.VISIBLE
            import.setEnabled(false)
            exit.setEnabled(false)
            async.execute()
            dialog.dismiss()
        }

        exit = view.findViewById<Button>(R.id.btn_exit)
        exit.setOnClickListener {
            dialog.dismiss()

        }


    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null)
            return
        if (requestCode == requestcode) {
            val filepath = data.data
            file = filepath.toString()
            val fileee = File(file!!)
            val size = fileee.length()
            val sizeMB = size / 1024
//            MB = sizeMB.toString()
//            println("SIZE IS "+MB)
            val cursor = contentResolver.openInputStream(android.net.Uri.parse(filepath.toString()))
            lbl.text = filepath.toString()
//            master_path=filepath.toString()
            noti = cursor.toString()
            val db = this.openOrCreateDatabase("database.db", Context.MODE_PRIVATE, null)
            val tableName = "Master"
            db.execSQL("delete from $tableName")
            val text = StringBuilder()
            try {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        val file = InputStreamReader(cursor)
                        val buffer = BufferedReader(file)
                        var lineCount = 0
                        db.beginTransaction()

                        while (true) {
                            val line1 = buffer.readLine()
                            lineCount++
                            line = lineCount
                            if (line1 == null) break


                        }
                        println(line.toString())
                        db.setTransactionSuccessful()
                        db.endTransaction()
                    } catch (e: IOException) {
                        if (db.inTransaction())
                            db.endTransaction()
                        val d = Dialog(this)
                        d.setTitle(e.message.toString() + "first")
                        d.show()
                    }

                } else {
                    if (db.inTransaction())
                        db.endTransaction()
                    val d = Dialog(this)
                    d.setTitle("Only CSV files allowed")
                    d.show()
                }
            } catch (ex: Exception) {
                if (db.inTransaction())
                    db.endTransaction()

                val d = Dialog(this)
                d.setTitle(ex.message.toString() + "second")
                d.show()
            }

        }

    }

    private class AsyncTaskRunner(val context: Context) : AsyncTask<String, String, String>() {

        internal lateinit var db: DataBaseHelper
        internal lateinit var pgd: ProgressDialog
        var resp: String? = null
        var cancel: String? = null


        override fun doInBackground(vararg params: String?): String {
            db = DataBaseHelper(context)
            resp = "working ASYNC"
            println(resp)

            if(file_type == "txt"){
                try {
                    import()
                } catch (e: Exception) {
                    println(e)
                }
            }

            if(file_type == "csv"){
                try {
                    import_csv()
                } catch (e: Exception) {
                    println(e)
                }
            }

            return resp!!
        }

        override fun onPreExecute() {
            pgd = ProgressDialog(context)
            pgd.setMessage("Loading")
            pgd.setTitle("Importing Data")

            pgd.setButton(
                DialogInterface.BUTTON_NEGATIVE,
                "Cancel",
                DialogInterface.OnClickListener { dialog, which ->
                    cancel = "stop"
                    dialog.dismiss()
                    cancel = null
                })
            pgd.show()
            pgd.setCancelable(false)

            super.onPreExecute()
            // ...
        }

        override fun onPostExecute(result: String?) {
            pgd.dismiss()
            val dialog: AlertDialog.Builder = AlertDialog.Builder(context)
            dialog.setTitle("STATUS!!")
            println(com_check)
            if (com_check == "complete") {
                dialog.setMessage("IMPORT COMPLETE ")
            }
            if (com_check == "error") {
                dialog.setMessage("IMPORT ERROR!! After Article Code : " + code_check)
            }

            dialog.setNegativeButton("OK", DialogInterface.OnClickListener() { dialog, which ->
                dialog.dismiss()

            })
            dialog.show()
            pgd.setCancelable(false)


            super.onPostExecute(result)
        }


        fun import() {

            val filepath = file
            val cursor =
                context.contentResolver.openInputStream(android.net.Uri.parse(filepath.toString()))
            val db1 = context.openOrCreateDatabase("database.db", Context.MODE_PRIVATE, null)
//            master_path=filepath.toString()
            noti = cursor.toString()
            val tableName = "Master"
            db1.execSQL("delete from $tableName")
            val text = StringBuilder()
            try {
                try {
                    val file = InputStreamReader(cursor)
                    var lineCount = 0
                    val buffer = BufferedReader(file)
                    val contentValues = ContentValues()
                    db1.beginTransaction()

                    while (true) {

                        lineCount++
                        in_line = lineCount
                        val line = buffer.readLine()

                        if (line == null) break
                        else {
                            if (cancel == "stop") {
                                db1.endTransaction()
                            }

                            val br_array = ArrayList<String>()
                            var ic_array = ArrayList<String>()
                            var nm_array = ArrayList<String>()
                            var pr_array = ArrayList<String>()
                            var qt_array = ArrayList<String>()
                            var total_array = ArrayList<String>()


                            if (br_val == 1) {
                                for (i in col_br - 1..col_br1 - 1) {
                                    val barcode = line[i].toString().replace(" ", "")
                                    br_array.add(barcode)
                                    println("BC" + barcode)
//                                    Toast.makeText(context,"OKOK",Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                br_array.add("")
                                println("fail")
                            }


                            if (ic_val == 1) {
//                                val a = col_br + col_ic-1
                                for (i in col_ic - 1..col_ic1 - 1) {
                                    val itemcode = line[i].toString().replace(" ", "")
                                    ic_array.add(itemcode)
                                    println("IC" + itemcode)

                                }
                            } else {
                                ic_array.add("")
                            }


                            if (nm_val == 1) {
//                                val b = col_ic+col_br + col_nm -1
//                                val b1 = col_br+col_ic
                                for (i in col_nm - 1..col_nm1 - 1) {
                                    val name = line[i].toString()
                                    nm_array.add(name)
                                    println("NM" + name)

                                }
                            } else {
                                nm_array.add("")
                            }


                            if (pr_val == 1) {
//                                val c =col_br+col_ic+ col_nm + col_pr -1
//                                val c1 = col_br+col_ic+col_nm
                                for (i in col_pr - 1..col_pr1 - 1) {
                                    val price = line[i].toString().replace(" ", "")
                                    pr_array.add(price)
                                    println("PR" + price)

                                }
                            } else {
                                pr_array.add("")
                            }


                            if (qt_val == 1) {
//                                val d = col_br+col_ic+col_nm+ col_pr + col_qt -1
//                                val d1 = col_br+col_ic+col_nm+col_pr
                                for (i in col_qt - 1..col_qt1 - 1) {
                                    val qty = line[i].toString().replace(" ", "")
                                    qt_array.add(qty)
                                    println("QTY" + qty)

                                }
                            } else {
                                qt_array.add("")
                            }

                            val barcode_list =
                                br_array.toString().replace(", ", "").replace("[", "")
                                    .replace("]", "")
                            val itemcode_list =
                                ic_array.toString().replace(", ", "").replace("[", "")
                                    .replace("]", "")
                            val name_list =
                                nm_array.toString().replace(", ", "").replace("[", "")
                                    .replace("]", "")
                            val price_list: String =
                                pr_array.toString().replace(", ", "").replace("[", "")
                                    .replace("]", "")
                            val qty_list: String =
                                qt_array.toString().replace(", ", "").replace("[", "")
                                    .replace("]", "")

                            total_array.add(barcode_list)
                            total_array.add(itemcode_list)
                            total_array.add(name_list)
                            total_array.add(price_list)
                            total_array.add(qty_list)

                            code_check = barcode_list


                            println(total_array)
                            val br = total_array[0].toString()
                            val ic = total_array[1].toString()
                            val nm = total_array[2].toString()
                            val pr = total_array[3].toString()
                            val qt = total_array[4].toString()

                            contentValues.put("barcode", br)
                            contentValues.put("item_code", ic)
                            contentValues.put("name", nm)
                            contentValues.put("onhand_qty", qt)
                            contentValues.put("price", pr)
                            db1.insert(tableName, null, contentValues)
                        }

                    }
                    com_check = "complete"
                    println("CHECK" + com_check)
                    println(in_line.toString())


                    db1.setTransactionSuccessful()
                    db1.endTransaction()
                } catch (e: IOException) {
                    if (db1.inTransaction())
                        db1.endTransaction()
                    println("ERROR")
                    com_check = "error"
                    println(e)
                }

            } catch (ex: Exception) {
                if (db1.inTransaction())
                    db1.endTransaction()
                println("ERROR!!!")
                com_check = "error"
                println(ex)
            }

        }

        fun import_csv() {

            val filepath = file
            val cursor =
                context.contentResolver.openInputStream(android.net.Uri.parse(filepath.toString()))
            val db1 = context.openOrCreateDatabase("database.db", Context.MODE_PRIVATE, null)
//            master_path=filepath.toString()
            noti = cursor.toString()
            val tableName = "Master"
            db1.execSQL("delete from $tableName")
            val text = StringBuilder()
            try {
                try {
                    val file = InputStreamReader(cursor)
                    var lineCount = 0
                    val buffer = BufferedReader(file)
                    val contentValues = ContentValues()
                    db1.beginTransaction()

                    while (true) {

                        val line = buffer.readLine()
                        if (line == null) break
                        val str = line.split("$deli".toRegex())
                            .toTypedArray()

                        var barcode:String? = null
                        var itemcode:String? = null
                        var name:String? = null
                        var price:String? = null
                        var qty:String? = null

                        if(br_val == 1){
                            barcode = str[col_br2-1].toString()
                        }
                        else{
                            barcode = ""
                        }

                        if(ic_val == 1){
                            itemcode = str[col_ic2-1].toString()
                        }
                        else{
                            itemcode = ""
                        }

                        if(nm_val == 1){
                            name = str[col_nm2-1].toString()
                        }
                        else{
                            name = ""
                        }

                        if(pr_val == 1){
                            price = str[col_pr2-1].toString()
                        }
                        else{
                            price = ""
                        }

                        if(qt_val == 1){
                            qty = str[col_qt2-1].toString()
                        }
                        else{
                            qty = ""
                        }



                        contentValues.put("barcode", barcode)
                        contentValues.put("item_code", itemcode)
                        contentValues.put("name", name)
                        contentValues.put("price", price)
                        contentValues.put("onhand_qty", qty)
                        db1.insert(tableName, null, contentValues)
                    }
                    com_check = "complete"
                    println("CHECK" + com_check)
                    println(in_line.toString())


                    db1.setTransactionSuccessful()
                    db1.endTransaction()
                } catch (e: IOException) {
                    if (db1.inTransaction())
                        db1.endTransaction()
                    println("ERROR")
                    com_check = "error"
                    println(e)
                }

            } catch (ex: Exception) {
                if (db1.inTransaction())
                    db1.endTransaction()
                println("ERROR!!!")
                com_check = "error"
                println(ex)
            }

        }
    }



    fun loadbr(){
        var prefs=getSharedPreferences("br", Activity.MODE_PRIVATE)
        val language:Int = prefs.getInt("val", 0)
        br_val = language
    }

    fun loadic(){
        var prefs=getSharedPreferences("ic", Activity.MODE_PRIVATE)
        val language:Int = prefs.getInt("val1", 0)
        ic_val = language
    }

    fun loadnm(){
        var prefs=getSharedPreferences("nm", Activity.MODE_PRIVATE)
        val language:Int = prefs.getInt("val2", 0)
        nm_val = language
    }

    fun loadpr(){
        var prefs=getSharedPreferences("nm", Activity.MODE_PRIVATE)
        val language:Int = prefs.getInt("val3", 0)
        pr_val = language
    }

    fun loadqt(){
        var prefs=getSharedPreferences("nm", Activity.MODE_PRIVATE)
        val language:Int = prefs.getInt("val4", 0)
        qt_val = language
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

    fun loadfileType(){
        var pref=getSharedPreferences("file_type", Activity.MODE_PRIVATE)
        val file = pref.getString("f", "txt")
        file_type = file    }


}
