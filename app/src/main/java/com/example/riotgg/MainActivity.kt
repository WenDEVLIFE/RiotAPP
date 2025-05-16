package com.example.riotgg

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar 
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import android.view.ViewOutlineProvider
// BlurView library
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur

// Window insets helpers
import androidx.core.view.WindowCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var toolbarText: TextView
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawerLayout = findViewById(R.id.drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close
        )
        
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        
         // Enable drawer toggle in the toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        
        val menuIcon: ImageView = findViewById(R.id.toolbarMenu)
        menuIcon.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val logoIcon: ImageView = findViewById(R.id.toolbarLogo)
        logoIcon.setOnClickListener {
            Toast.makeText(this, "Logo clicked", Toast.LENGTH_SHORT).show()
        }
        // Initialize Bottom Navigation and Toolbar Text
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Initialize Side Navigation View
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)

        // Set default fragment
        setCurrentFragment(FirstFragment())

    /*
    val rootView = findViewById<ViewGroup>(R.id.bottomRoot)
    
    val blurView = findViewById<BlurView>(R.id.blurView)

    // We use the window background as the frame-clear, so nothing peek‑through flickers
    val windowBackground = window.decorView.background

    blurView.setupWith(rootView)
        .setFrameClearDrawable(windowBackground)      // snapshot what’s behind
        .setBlurRadius(12f)
        
ViewCompat.setOnApplyWindowInsetsListener(bottomNavigationView) { view, insets ->
    val navInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
    view.setPadding(
        view.paddingLeft,
        view.paddingTop,
        view.paddingRight,
        navInsets.bottom
    )
    insets
}*/
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
