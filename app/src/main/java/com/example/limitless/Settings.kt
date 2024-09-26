package com.example.limitless

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class Settings : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    companion object {
        private const val PREFS_NAME = "MyAppPrefs"
        private const val KEY_DARK_MODE = "dark_mode"
    }

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
        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt)
//        val btt2 = AnimationUtils.loadAnimation(this, R.anim.btt2)
//        val btt3 = AnimationUtils.loadAnimation(this, R.anim.btt3)
//        val btt4 = AnimationUtils.loadAnimation(this, R.anim.btt4)

        val BackgroundImage = findViewById(R.id.BackgroundImage) as ImageView
        val profileImage = findViewById(R.id.profileImage) as ImageView
        val linearLayout7 = findViewById(R.id.linearLayout7) as LinearLayout

        BackgroundImage.startAnimation(ttb)
        profileImage.startAnimation(stb)
        linearLayout7.startAnimation(btt)
        //till here
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Apply the saved theme before setting the content view
        applyTheme()
        val switchTheme: Switch = findViewById(R.id.switchTheme)


        val bottomNavBar: BottomNavigationView = findViewById(R.id.NavBar)

        switchTheme.isChecked = isDarkModeEnabled()

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            saveDarkModeState(isChecked)
            applyTheme()
            recreate() // Recreate the activity to apply the new theme
        }
        bottomNavBar.setSelectedItemId(R.id.ic_settings)
        bottomNavBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.ic_workouts -> {
                    startActivity(Intent(this, Exercise_Activity::class.java))
                    true
                }

                R.id.ic_nutrition -> {
                    startActivity(Intent(this, Diet_Activity::class.java))
                    true
                }

                R.id.ic_Report -> {
                    startActivity(Intent(this, Report_Activity::class.java))
                    true
                }

                R.id.ic_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }

                else -> false
            }
        }
    }

    public fun isDarkModeEnabled(): Boolean {
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

    }
}
