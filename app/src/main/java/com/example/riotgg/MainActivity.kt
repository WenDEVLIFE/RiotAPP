package com.example.riotgg

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar // ✅ Import Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var toolbarText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // ✅ Find the Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar) // ✅ Set Toolbar as ActionBar

        // Initialize Drawer Layout and Toggle
        drawerLayout = findViewById(R.id.drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close
        )
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // Enable drawer toggle in the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        // Initialize Bottom Navigation and Toolbar Text
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Initialize Side Navigation View
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)

        // Set default fragment
        setCurrentFragment(FirstFragment())

        // Handle Bottom Navigation Clicks
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    setCurrentFragment(FirstFragment())
                }
                R.id.valorant -> {
                    setCurrentFragment(SecondFragment())
                }
                R.id.leagueoflegends -> {
                    setCurrentFragment(ThirdFragment())
                }
            }
            true
        }
    }

    // Handle Side Navigation Clicks
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                setCurrentFragment(FirstFragment())
            }
            R.id.nav_valorant -> {
                setCurrentFragment(SecondFragment())
            }
            R.id.nav_league -> {
                setCurrentFragment(ThirdFragment())
            }

            R.id.nav_exit ->{
                showExitDialog()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    // Handle Fragment Switching
    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.flFragment, fragment).commit()
    }

    // Handle Drawer Toggle Clicks
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    // Handle Back Button Press to Close Drawer and Show Confirmation Dialog
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // Show confirmation dialog before closing the app
            AlertDialog.Builder(this)
                .setMessage("Close the app?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    super.onBackPressed() // Close the app if 'Yes' is clicked
                }
                .setNegativeButton("No", null) // Do nothing if 'No' is clicked
                .show()
        }
    }

    private fun showExitDialog() {
        AlertDialog.Builder(this)
            .setTitle("Exit App")
            .setMessage("Are you sure you want to exit the app?")
            .setPositiveButton("Yes") { dialog, _ ->
                finish() // Closes the app
            }
            .setNegativeButton("No", null) // Do nothing if 'No' is clicked
            .show()
    }
}
