package com.example.riotgg.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SavedGuideDbHelper(context: Context) :
    SQLiteOpenHelper(context, "valorant_guides.db", null, 1) {
    
    //Create Table for saved guides
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS SavedGuides (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                url TEXT NOT NULL UNIQUE
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS SavedGuides")
        onCreate(db)
    }

    fun saveGuide(title: String, url: String): Boolean {
        val db = writableDatabase
        return try {
            val values = ContentValues().apply {
                put("title", title)
                put("url", url)
            }
            db.insertOrThrow("SavedGuides", null, values)
            true
        } catch (e: SQLiteConstraintException) {
            false // duplicate
        }
    }

    fun deleteGuide(url: String): Boolean {
        val db = writableDatabase
        return db.delete("SavedGuides", "url = ?", arrayOf(url)) > 0
    }

    fun isGuideSaved(url: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT 1 FROM SavedGuides WHERE url = ?", arrayOf(url))
        val exists = cursor.moveToFirst()
        cursor.close()
        return exists
    }
}
