package com.farhandeveloper.datamate

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "student.db"
        private const val TBL_STUDENT = "tbl_student"
        private const val ID = "id"
        private const val NAME = "name"
        private const val EMAIL = "email"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblStudent = ("CREATE TABLE " + TBL_STUDENT + "(" +
                ID + " INTEGER PRIMARY KEY, " + NAME + " TEXT, " + EMAIL + " TEXT)")
        db?.execSQL(createTblStudent)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TBL_STUDENT")
        onCreate(db)
    }

    fun insertStudent(std: StudentModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(NAME, std.name)
        contentValues.put(EMAIL, std.email)

        val success = db.insert(TBL_STUDENT, null, contentValues)
        db.close()

        return success
    }

    fun getAllStudent(): ArrayList<StudentModel> {
        val stdList: ArrayList<StudentModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_STUDENT"
        val db = this.readableDatabase

        val cursor: Cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(NAME))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(EMAIL))

                val std = StudentModel(id = id, name = name, email = email)
                stdList.add(std)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return stdList
    }

    fun updateStudent(std: StudentModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, std.id)
        contentValues.put(NAME, std.name)
        contentValues.put(EMAIL, std.email)

        val success = db.update(TBL_STUDENT, contentValues, "$ID = ${std.id}", null)
        db.close()

        return success
    }

    fun deleteStudentById(id: Int): Int {
        val db = this.writableDatabase

        val success = db.delete(TBL_STUDENT, "$ID = $id", null)
        db.close()

        return success
    }
}