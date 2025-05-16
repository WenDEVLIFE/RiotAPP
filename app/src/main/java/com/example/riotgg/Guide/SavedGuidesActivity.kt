package com.example.riotgg.Guide

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.riotgg.R
import com.example.riotgg.db.*

class SavedGuidesActivity : AppCompatActivity() {

    private lateinit var dbHelper: SavedGuideDbHelper
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val guideTitles = mutableListOf<String>()
    private val guideUrls = mutableListOf<String>()
    private lateinit var emptyMessage: TextView
    
    private fun openExternalLink(url: String) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setTitle("External Link")
        alertDialogBuilder.setMessage("This link leads to an external app. Continue?")

        alertDialogBuilder.setPositiveButton("Yes") { dialog, _ ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            dialog.dismiss()
        }

        alertDialogBuilder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_savedguides)

        listView = findViewById(R.id.guidesListView)
        emptyMessage = findViewById(R.id.emptyMessage)
        dbHelper = SavedGuideDbHelper(this)

        findViewById<ImageView>(R.id.toolbarBack).setOnClickListener {
            finish()
        }

        loadSavedGuides()
        
        listView.setOnItemClickListener { _, _, position, _ ->
           val url = guideUrls[position]
           openExternalLink(url)
        }

        val gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?, e2: MotionEvent?,
                velocityX: Float, velocityY: Float
            ): Boolean {
                if (e1 != null && e2 != null && (e1.x - e2.x) > 100) {
                    val position = listView.pointToPosition(e1.x.toInt(), e1.y.toInt())
                    if (position != AdapterView.INVALID_POSITION) {
                        confirmDelete(position)
                    }
                }
                return false
            }
        })

        listView.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            false
        }
    }

    private fun loadSavedGuides() {
        guideTitles.clear()
        guideUrls.clear()

        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT title, url FROM SavedGuides", null)
        while (cursor.moveToNext()) {
            guideTitles.add(cursor.getString(0))
            guideUrls.add(cursor.getString(1))
        }
        cursor.close()

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, guideTitles)
        listView.adapter = adapter

        if (guideTitles.isEmpty()) {
           emptyMessage.visibility = View.VISIBLE
           listView.visibility = View.GONE
        } else {
           emptyMessage.visibility = View.GONE
           listView.visibility = View.VISIBLE
        }

    }

    private fun confirmDelete(position: Int) {
        val title = guideTitles[position]
        val url = guideUrls[position]

        AlertDialog.Builder(this)
            .setTitle("Delete Saved Guide")
            .setMessage("Are you sure you want to delete \"$title\"?")
            .setPositiveButton("Delete") { _, _ ->
                dbHelper.deleteGuide(url)
                loadSavedGuides()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}

