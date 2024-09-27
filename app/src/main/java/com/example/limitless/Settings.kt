package com.example.limitless

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.limitless.data.User
import com.google.android.material.bottomnavigation.BottomNavigationView

class Settings : AppCompatActivity() {
   // private lateinit var sharedPreferences: SharedPreferences
    lateinit var bottomNavBar: BottomNavigationView
    private var themeChanged = false

    /*companion object {
        private const val PREFS_NAME = "MyAppPrefs"
        private const val KEY_DARK_MODE = "dark_mode"
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Nicks Animation things
        //val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
       // val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
       // val btt = AnimationUtils.loadAnimation(this, R.anim.btt)
//        val btt2 = AnimationUtils.loadAnimation(this, R.anim.btt2)
//        val btt3 = AnimationUtils.loadAnimation(this, R.anim.btt3)
//        val btt4 = AnimationUtils.loadAnimation(this, R.anim.btt4)

        val BackgroundImage = findViewById(R.id.BackgroundImage) as ImageView
        val profileImage = findViewById(R.id.profileImage) as ImageView
        val linearLayout7 = findViewById(R.id.linearLayout7) as LinearLayout
        val goals: LinearLayout = findViewById(R.id.ll4)

        goals.setOnClickListener{
            Goals()
        }

        //BackgroundImage.startAnimation(ttb)
       // profileImage.startAnimation(stb)
       // linearLayout7.startAnimation(btt)
        //till here
       // sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        bottomNavBar = findViewById(R.id.NavBar)

        // Apply the saved theme before setting the content view
       // applyTheme()


        /*switchTheme.isChecked = isDarkModeEnabled()

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            saveDarkModeState(isChecked)
            applyTheme()
            recreate() // Recreate the activity to apply the new theme
        }*/
        //val switchTheme: Switch = findViewById(R.id.switchTheme)
        ThemeManager.updateNavBarColor(this, bottomNavBar)
        val switchTheme: Switch = findViewById(R.id.switchTheme)
        // Setup switchTheme and listen for changes
        switchTheme.isChecked = ThemeManager.isDarkModeEnabled(this)
        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (ThemeManager.isDarkModeEnabled(this) != isChecked) {
                // Only apply the theme if there's an actual change
                ThemeManager.saveDarkModeState(this, isChecked)

                // Delay theme application slightly to prevent flickering
                switchTheme.postDelayed({
                    ThemeManager.applyTheme(this)
                }, 200) // Delay in milliseconds (200ms should be smooth)
            }
        }
        bottomNavBar.setSelectedItemId(R.id.ic_settings)
        bottomNavBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_workouts -> {
                    navigateToActivityLeft(Exercise_Activity::class.java)
                    true
                }

                R.id.ic_nutrition -> {
                    navigateToActivityLeft(Diet_Activity::class.java)
                    true
                }

                R.id.ic_Report -> {
                    navigateToActivityLeft(Report_Activity::class.java)
                    true
                }

                R.id.ic_home -> {
                    navigateToActivityLeft(MainActivity::class.java)
                    true
                }

                else -> false
            }
        }
    }
    // Helper function to navigate to another activity with transition
    private fun navigateToActivityLeft(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        val options = ActivityOptionsCompat.makeCustomAnimation(
            this, R.anim.slide_in_left, R.anim.slide_out_right
        )
        startActivity(intent, options.toBundle())
    }

    /*public fun isDarkModeEnabled(): Boolean {
        return sharedPreferences.getBoolean(KEY_DARK_MODE, false)
    }

    private fun saveDarkModeState(isDarkMode: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_DARK_MODE, isDarkMode).apply()
    }

    private fun applyTheme() {
        val isDarkMode = isDarkModeEnabled()
        AppCompatDelegate.setDefaultNightMode(
            if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
        )
        val backgroundColor = if (isDarkMode) {
            ContextCompat.getColor(this, R.color.darkBackground) // Define a dark color in your colors.xml
        } else {
            ContextCompat.getColor(this, R.color.lightBackground) // Define a light color in your colors.xml
        }

        bottomNavBar.setBackgroundColor(backgroundColor)
    }*/
    private fun Goals(){
        // Inflate the custom layout
        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.layout_set_goal, null)

        val seekbar : SeekBar = dialogView.findViewById(R.id.seekbarTime)
        val showSeekbar: TextView = dialogView.findViewById(R.id.lblSelectPeriod)
        val loseWeight: RadioButton = dialogView.findViewById(R.id.rbLoseWeight)
        val gainWeight: RadioButton = dialogView.findViewById(R.id.rbGainWeight)
        
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                showSeekbar.text = "Time Period: $progress weeks"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })

        // Build and show the dialog
        AlertDialog.Builder(this)
            .setTitle("Set Goal")
            .setView(dialogView) // Set the custom view
            .setPositiveButton("Confirm") { dialog, which ->

            }
            .setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()

            }
            .create()
            .show()
    }
}
