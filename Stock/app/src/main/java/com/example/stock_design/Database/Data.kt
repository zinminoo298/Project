package com.example.stock_design.Database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.stock_design.*
import com.example.stock_design.Adapters.rowview_date
import com.example.stock_design.Adapters.rowview_location
import com.example.stock_design.Modle.Item
import com.example.stock_design.Modle.Item_Detail
import com.example.stock_design.Modle.Item_search
import com.example.stock_design.Modle.Master_data
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

var name: String?=null
var quantity2: Int?=0
var quantity1: Int?=0
var item_code: String?=null
var master_name: String?=null

var item_tran: Int?=0
var qty_tran: Int?=0
var new_item: Int?=0
var date: String?=null

var item_master: Int?=0
var qty_master: Int?=0

var view_ic:String? = null

class DataBaseHelper(val context: Context) {

    companion object {
        private val REAL_DATABASE="database.db"
        private val DB_PATH="/data/data/com.example.stock_design/databases"

        //table
        private val TABLE_NAME="Master"
        private val TABLE_NAME1="Transaction_table"
        private val COL_ID="barcode1"
        private val COL_BRANCH="branch"
        private val COL_NAME="name"
        private val COL_QUANTITY="qty"
        private val COL_ID1="barcode"
        private val COL_QUANTITY1="onhand_qty"
        private val DATE="26/6/2019"
        private val COL_DATE="date"
        private val COL_LOCATION="location"

    }

    /*Open database and start copying database from assets folder*/
    fun openDatabase(): SQLiteDatabase {
        val dbFile=context.getDatabasePath(REAL_DATABASE)
        if (!dbFile.exists()) {
            try {
                val checkDB=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)

                checkDB?.close()
                copyDatabase(dbFile)
            } catch (e: IOException) {
                throw RuntimeException("Error creating source database", e)
            }

        }
        return SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READWRITE)
    }

    /*Copy database from assets folder to package*/
    @SuppressLint("WrongConstant")
    private fun copyDatabase(dbFile: File) {
        val `is`=context.assets.open(REAL_DATABASE)
        val os=FileOutputStream(dbFile)

        val buffer=ByteArray(1024)
        while(`is`.read(buffer)>0) {
            os.write(buffer)
            Log.d("#DB", "writing>>")
        }

        os.flush()
        os.close()
        `is`.close()
        Log.d("#DB", "completed..")
    }

    /*Get data from transaction table for Summery page */
    val Summery: MutableList<Item_search>
        get() {
            val seItem=ArrayList<Item_search>()
            val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
            val selectQuery=
                "Select date,location,SUM(qty) as qty,COUNT(barcode1)as item FROM Transaction_table Group By location Order By date"
            val cursor=db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {

                do {
                    val item=Item_search()
                    item.itm=cursor.getInt(cursor.getColumnIndex("item"))
                    item.qty=cursor.getInt(cursor.getColumnIndex("qty"))
                    item.date=cursor.getString(cursor.getColumnIndex(COL_DATE))
                    item.location=cursor.getString(cursor.getColumnIndex(COL_LOCATION))
                    seItem.add(item)
                } while(cursor.moveToNext())
            }
            db.close()
            return seItem
        }

    /*Get data from transaction table for Summery detail page*/
    val Detail: List<Item_Detail>
        get() {
            val seItem=ArrayList<Item_Detail>()
            val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
            val selectQuery=
                "Select * From Transaction_table Where date='$rowview_date' And location='$rowview_location'"
            val cursor=db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {

                do {
                    val item=Item_Detail()
                    item.id=cursor.getString(cursor.getColumnIndex("barcode1"))
                    item.qty=cursor.getInt(cursor.getColumnIndex("qty"))
                    seItem.add(item)
                } while(cursor.moveToNext())
            }
            db.close()
            return seItem
        }

    fun viewIC(){
        val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
        val selectQuery = "SELECT * FROM MASTER WHERE Master.barcode = '$record_code'"
        val cursor = db.rawQuery(selectQuery,null)
        if(cursor.moveToFirst()){
            view_ic = cursor.getString(cursor.getColumnIndex("item_code"))
            println("itmcode"+view_ic)
        }

        else{
            println("GG")
        }
    }

    /*Add data to transaction table*/
    fun addItem(item: Item) {
        val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
        val values=ContentValues()
        values.put(COL_ID, item.br)
        values.put(COL_BRANCH, user_record)
        values.put(COL_QUANTITY, item.quantity)
        values.put(
            COL_DATE,
            record_date
        )
        values.put(
            COL_LOCATION,
            record_location
        )
        values.put("item_code1",item.ic)
        val id=db.insertWithOnConflict(TABLE_NAME1, null, values, SQLiteDatabase.CONFLICT_IGNORE)
        if (id == -1L) {
            println("OKOK")
            val selectQuery=
                "SELECT qty FROM $TABLE_NAME1 WHERE $COL_ID=? AND Transaction_table.date = '$record_date' AND Transaction_table.location='$record_location' "
            val cursor=db.rawQuery(selectQuery, arrayOf(item.br.toString()))

           if(cursor.moveToFirst()){
               var quantity=cursor.getInt(cursor.getColumnIndex(COL_QUANTITY))
               val valu=ContentValues()
               valu.put(COL_QUANTITY, quantity + 1)

               db.update(
                   TABLE_NAME1, valu, "$COL_ID=?", arrayOf(item.br.toString())
               )
           }

            else{
               println("FFF")
           }

        }

//        val master=ContentValues()
//        master.put(COL_ID1, item.br)
//        master.put(COL_NAME, "new item")
//        master.put(COL_QUANTITY1, "0")
//        db.insertWithOnConflict(TABLE_NAME, null, master, SQLiteDatabase.CONFLICT_IGNORE)
        db.close()
    }

    /*get data from transaction table for view after adding data*/
    fun viewData() {
        val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
        val selectQuery=
            "SELECT * FROM Transaction_table LEFT JOIN Master ON Master.barcode = Transaction_table.barcode1 WHERE Transaction_table.date = '$record_date'  AND Transaction_table.location='$record_location' AND Transaction_table.barcode1= '$record_code'"
        val cursor=db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {

            do {
                item_code=cursor.getString(cursor.getColumnIndex("item_code"))
                quantity2=cursor.getInt(cursor.getColumnIndex(COL_QUANTITY))
                master_name=cursor.getString(cursor.getColumnIndex(COL_NAME))
                quantity1=cursor.getInt(cursor.getColumnIndex(COL_QUANTITY1))

                println(master_name)

                Log.e(TAG, DatabaseUtils.dumpCurrentRowToString(cursor))

            } while(cursor.moveToNext())
        }

        db.close()
    }

    /*get summery data from transaction table for main page*/
    fun getTransactionSummery() {
        val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
        val selectQuery="SELECT SUM (qty) FROM Transaction_table"
        val Query=db.rawQuery(selectQuery, null)
        if (Query.moveToFirst()) {
            qty_tran=Query.getInt(0)
        }

        val cursor=DatabaseUtils.queryNumEntries(db, "Transaction_table")
        item_tran=cursor.toInt()


        val selectQuery1=
            "SELECT COUNT (barcode1) FROM Transaction_table LEFT JOIN Master ON Master.barcode = Transaction_table.barcode1 WHERE Master.name='new item' "
        val Query1=db.rawQuery(selectQuery1, null)
        if (Query1.moveToFirst()) {
            new_item=Query1.getInt(0)

        }

        val selectQuery2="SELECT $COL_DATE FROM Transaction_table ORDER BY rowid DESC LIMIT 1"
        val Query2=db.rawQuery(selectQuery2, null)
        if (Query2.moveToFirst()) {
            date=Query2.getString(0)
            println("CCCCCCC" + date)

        }

        db.close()

    }

    /*get summery data from master table for main page*/
    fun getMasterSummery() {
        val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)

        val selectQuery="SELECT SUM ($COL_QUANTITY1) FROM Master"
        val Query=db.rawQuery(selectQuery, null)
        if (Query.moveToFirst()) {
            qty_master=Query.getInt(0)
        }

        val cursor=DatabaseUtils.queryNumEntries(db, "Master")
        item_master=cursor.toInt()


        db.close()
    }

    val MasterData: MutableList<Master_data>
        get() {
            val seItem=ArrayList<Master_data>()
            val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
            val selectQuery=
                "Select * From Master"
            val cursor=db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {

                do {
                    val item=Master_data()
                    item.br=cursor.getString(cursor.getColumnIndex("barcode"))
                    item.quantity=cursor.getInt(cursor.getColumnIndex("onhand_qty"))
                    item.ic=cursor.getString(cursor.getColumnIndex("item_code"))
                    seItem.add(item)
                } while(cursor.moveToNext())
            }
            db.close()
            return seItem
        }

    /*update data to transaction table */
    fun updateItem(item: Item): Int {

        val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
        val values=ContentValues()
        values.put(COL_ID, item.br)
        values.put(COL_QUANTITY, item.quantity)


        return db.update(TABLE_NAME1, values, "$COL_ID=?", arrayOf(item.br.toString()))
    }

    /*delete data from transaction table*/
    fun deleteItem(item: Item) {
        val db=context.openOrCreateDatabase(REAL_DATABASE, Context.MODE_PRIVATE, null)
        db.delete(TABLE_NAME1, "$COL_ID=?", arrayOf(item.br.toString()))
        db.close()
    }


}