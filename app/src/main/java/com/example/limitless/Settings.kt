package com.example.limitless

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.limitless.Exercise.Exercise_Activity
import com.example.limitless.Nutrition.Diet_Activity
import com.example.limitless.data.DbAccess
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.Locale

class Settings : AppCompatActivity() {
    lateinit var bottomNavBar: BottomNavigationView
    private var themeChanged = false
    val dbAccess = DbAccess.GetInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        LoadLocate()
        //Nicks Animation things
        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt)

        val btnLanguage = findViewById<Button>(R.id.btnLanguage_ST)
//        val btt2 = AnimationUtils.loadAnimation(this, R.anim.btt2)
//        val btt3 = AnimationUtils.loadAnimation(this, R.anim.btt3)
//        val btt4 = AnimationUtils.loadAnimation(this, R.anim.btt4)

        val BackgroundImage = findViewById(R.id.BackgroundImage) as ImageView
        val linearLayout7 = findViewById(R.id.linearLayout7) as LinearLayout
        val goals: LinearLayout = findViewById(R.id.ll4)
        val logout: LinearLayout = findViewById(R.id.ll5)
        val deleteUser :LinearLayout = findViewById(R.id.ll6)

        logout.setOnClickListener{
            currentUser?.LogOut()
            currentUser = null

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        goals.setOnClickListener{
            val intent = Intent(this, Goal_page::class.java)
            startActivity(intent)
        }

        BackgroundImage.startAnimation(ttb)
        linearLayout7.startAnimation(btt)
        //till here

        ThemeManager.applyTheme(this)

        bottomNavBar = findViewById(R.id.NavBar)
        val switchTheme: Switch = findViewById(R.id.switchTheme)

        ThemeManager.updateNavBarColor(this, bottomNavBar)

        // Setup switchTheme and listen for changes
        switchTheme.isChecked = ThemeManager.isDarkModeEnabled(this)

        switchTheme.setOnCheckedChangeListener { _, isChecked ->
            if (ThemeManager.isDarkModeEnabled(this) != isChecked) {
                // Only apply the theme if there's an actual change
                ThemeManager.saveDarkModeState(this, isChecked)
                recreateActivitySmoothly()
            }
        }

        deleteUser.setOnClickListener{
            dbAccess.DeleteUser(currentUser!!.userId)
        }
        btnLanguage.setOnClickListener {
            showDialog()
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

                R.id.ic_home -> {
                    navigateToActivityLeft(MainActivity::class.java)
                    true
                }

                else -> false
            }
        }
    }
    fun recreateActivitySmoothly() {
        val intent = Intent(this, this::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(intent)

        // Use smooth fade-in, fade-out animations
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        // Add a short delay to ensure smooth nav bar update
        window.decorView.postDelayed({
            val bottomNavBar: BottomNavigationView = findViewById(R.id.NavBar)
            ThemeManager.updateNavBarColor(this, bottomNavBar)
        }, 100)

        finish()
    }

    // Helper function to navigate to another activity with transition
    private fun navigateToActivityLeft(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        val options = ActivityOptionsCompat.makeCustomAnimation(
            this, R.anim.slide_in_left, R.anim.slide_out_right
        )
        startActivity(intent, options.toBundle())
    }

    private fun showDialog(){
        val languages = arrayOf("English", "Afrikaans")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select Language")
        builder.setSingleChoiceItems(languages, -1){ dialog, which ->
            if(which == 0){
                setLocate("en")
                recreate()
            }else if(which == 1){
                setLocate("af")
                recreate()
            }

            dialog.dismiss()

        }
        val dai = builder.create()
        dai.show()
    }

    private fun setLocate(lang: String){
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", lang)
        editor.apply()
    }

    private fun LoadLocate(){
        val sharedPreference = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreference.getString("My_Lang", "")
        setLocate(language!!)
    }
}
