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
import android.widget.BaseAdapter

import com.example.riotgg.db.*

class SavedGuidesActivity : AppCompatActivity() {

    private lateinit var dbHelper: SavedGuideDbHelper
    private lateinit var listView: ListView
    private lateinit var adapter: BaseAdapter
    private val guideTitles = mutableListOf<String>()
    private val guideUrls = mutableListOf<String>()
    private lateinit var emptyMessage: TextView
    private lateinit var gestureDetector: GestureDetector
    
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

        adapter = GuideAdapter(guideTitles, guideUrls)
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
    
    inner class GuideAdapter(
    private val titles: List<String>,
    private val urls: List<String>
) : BaseAdapter() {

    override fun getCount() = titles.size
    override fun getItem(position: Int) = titles[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: layoutInflater.inflate(R.layout.list_item_guide, parent, false)

        val titleView = view.findViewById<TextView>(R.id.guideTitle)
        val removeIcon = view.findViewById<ImageView>(R.id.removeIcon)

        titleView.text = titles[position]

        titleView.setOnClickListener {
            openExternalLink(urls[position])
        }

        removeIcon.setOnClickListener {
            confirmDelete(position)
        }

        removeIcon.setOnLongClickListener {
            Toast.makeText(this@SavedGuidesActivity, "Delete Guide", Toast.LENGTH_SHORT).show()
            true
        }

        return view
    }
}

}

