package com.main.limitless

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.main.limitless.Exercise.Exercise_Activity
import com.main.limitless.Nutrition.Diet_Activity
import com.main.limitless.data.DbAccess
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.main.limitless.data.HealthNotifications
import com.main.limitless.data.Notifications
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
        val btt2 = AnimationUtils.loadAnimation(this, R.anim.btt2)
//        val btt3 = AnimationUtils.loadAnimation(this, R.anim.btt3)
//        val btt4 = AnimationUtils.loadAnimation(this, R.anim.btt4)

        val BackgroundImage = findViewById(R.id.BackgroundImage) as ImageView
        val linearLayout7 = findViewById(R.id.linearLayout7) as LinearLayout
        val goals: LinearLayout = findViewById(R.id.ll4)
        val logout: LinearLayout = findViewById(R.id.ll5)
        val deleteUser :TextView = findViewById(R.id.txtDeleteAcc)
        val switch: Switch = findViewById(R.id.switchNotifications)
        switch.isChecked = NotificationUtils.getNotificationState(this)

        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked && isOnline) {
                enableNotifications()
                startHealthNotificationService()
            } else {
                disableNotifications()
                stopHealthNotificationService()
            }

            NotificationUtils.saveNotificationState(this, isChecked)
        }


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
        deleteUser.startAnimation(btt2)
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

        deleteUser.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.delete_account))
                .setMessage(getString(R.string.are_you_sure_you_want_to_delete_your_account_this_action_cannot_be_undone))
                .setPositiveButton(getString(R.string.yes)) { dialog, _ ->
                    dbAccess.DeleteUser(currentUser!!.userId)
                    dialog.dismiss()
                    Toast.makeText(this,
                        getString(R.string.account_deleted_successfully), Toast.LENGTH_SHORT).show()

                }
                .setNegativeButton(getString(R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
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

    private fun startHealthNotificationService() {
        val intent = Intent(this, HealthNotifications::class.java)
        intent.putExtra("weight", currentUser!!.userInfo.weight.toString())
        intent.putExtra("calories", (nutritionViewModel.calorieWallet - nutritionViewModel.CalculateTotalCalories()).toString())
        ContextCompat.startForegroundService(this, intent)
    }

    private fun stopHealthNotificationService() {
        val intent = Intent(this, HealthNotifications::class.java)
        stopService(intent)
    }


    private fun enableNotifications() {
        // Enable notifications (basic example with WorkManager)
        val data = workDataOf(
            "type" to "scheduled",
            "title" to "Reminder",
            "content" to "It's time to check Limitless!"
        )

        val notificationWork = OneTimeWorkRequestBuilder<Notifications>()
            .setInputData(data)
            .build()

        WorkManager.getInstance(applicationContext).enqueue(notificationWork)

    }

    private fun disableNotifications() {
        WorkManager.getInstance(applicationContext).cancelAllWorkByTag("scheduled_notification")
    }


    fun getNotificationState(): Boolean {
        val sharedPref = getSharedPreferences("NotificationPrefs", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("notifications_enabled", false)
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
        val languages = arrayOf(getString(R.string.english), "Afrikaans")

        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.select_language))
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

object NotificationUtils {

    fun saveNotificationState(context: Context, isEnabled: Boolean) {
        val sharedPref = context.getSharedPreferences("NotificationPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("notifications_enabled", isEnabled)
            apply()
        }
    }

    fun getNotificationState(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences("NotificationPrefs", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("notifications_enabled", false)
    }
}

