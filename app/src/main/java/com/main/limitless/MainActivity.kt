package com.main.limitless

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.main.limitless.data.StepCounterService
import com.main.limitless.data.User
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.Manifest
import android.net.ConnectivityManager
import android.net.Network
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityOptionsCompat
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.main.limitless.data.NetworkMonitor
import com.main.limitless.Exercise.Exercise_Activity
import com.main.limitless.Exercise.Workout_Planner
import com.main.limitless.Nutrition.Diet_Activity
import com.main.limitless.data.Notifications
import com.main.limitless.data.ViewModels.ActivityViewModel
import com.main.limitless.data.HealthNotifications
import com.main.limitless.data.ViewModels.NutritionViewModel
import java.time.LocalDate
import java.util.Calendar
import java.util.concurrent.TimeUnit

var  currentUser: User? = null
var nutritionViewModel = NutritionViewModel()
var activityViewModel = ActivityViewModel(LocalDate.now())
var isOnline = true

class MainActivity : AppCompatActivity() {

    private lateinit var networkMonitor: NetworkMonitor
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val REQUEST_CODE_PERMISSIONS = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val serviceIntent = Intent(this, Notifications::class.java)
       startService(serviceIntent)


        if(currentUser == null){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }else{
            startStepCounterService()
            setupWork()
            startHealthNotificationService(currentUser!!.userInfo.weight.toString(), (nutritionViewModel.calorieWallet - nutritionViewModel.CalculateTotalCalories()).toString())
        }

         networkCallback = object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(network: Network) {
                isOnline = true
            }

            override fun onLost(network: Network) {
                isOnline = false
            }
        }

        networkMonitor = NetworkMonitor(this)
        networkMonitor.registerNetworkCallback(networkCallback)

        //Nicks Animation things
        val btnGo = findViewById<Button>(R.id.btnGo)
        btnGo.setOnClickListener{
        val intent = Intent(this,Exercise_Activity::class.java)
        startActivity(intent)
        }


        val ttb = AnimationUtils.loadAnimation(this, R.anim.ttb)
//        val stb = AnimationUtils.loadAnimation(this, R.anim.stb)
        val btt = AnimationUtils.loadAnimation(this, R.anim.btt)
        val btt2 = AnimationUtils.loadAnimation(this, R.anim.btt2)
//        val btt3 = AnimationUtils.loadAnimation(this, R.anim.btt3)
//        val btt4 = AnimationUtils.loadAnimation(this, R.anim.btt4)

        val LinearLayout3 = findViewById<LinearLayout>(R.id.linearLayout3)
        val linearLayout2 = findViewById<LinearLayout>(R.id.linearLayout2)
        val scrollView2 = findViewById<ScrollView>(R.id.scrollView2)

        scrollView2.startAnimation(ttb)
        linearLayout2.startAnimation(btt2)
        LinearLayout3.startAnimation(btt)
        //till here

        ThemeManager.applyTheme(this)

        val bottomNavBar: BottomNavigationView = findViewById(R.id.NavBar)
        val dailyActivity: CardView = findViewById(R.id.dailyActivityCard)
        val workout: CardView = findViewById(R.id.workoutsCard)
        val recyclerView: RecyclerView = findViewById(R.id.PE_ListExercises)


        ThemeManager.updateNavBarColor(this, bottomNavBar)

        bottomNavBar.setSelectedItemId(R.id.ic_home)
        bottomNavBar.setOnNavigationItemSelectedListener{item ->
            when (item.itemId){
                R.id.ic_workouts -> {
                    navigateToActivityRight(Exercise_Activity::class.java)
                    true
                }
                R.id.ic_nutrition -> {
                    navigateToActivityRight(Diet_Activity::class.java)
                    true
                }

                R.id.ic_settings -> {
                    navigateToActivityRight(Settings::class.java)
                    true
                }
                else -> false
            }
        }



        dailyActivity.setOnClickListener{
            val intent = Intent(this, Exercise_Activity::class.java)
            startActivity(intent)
        }
        workout.setOnClickListener{
            val intent = Intent(this, Workout_Planner::class.java)
            startActivity(intent)
        }

        val mLayoutManager = LinearLayoutManager(applicationContext)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        //recyclerView.adapter = moviesAdapter

    }

    // Helper function to navigate to another activity with transition
    private fun navigateToActivityRight(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        val options = ActivityOptionsCompat.makeCustomAnimation(
            this, R.anim.slide_in_right, R.anim.slide_out_left
        )
        startActivity(intent, options.toBundle())
    }
    private fun navigateToActivityLeft(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        val options = ActivityOptionsCompat.makeCustomAnimation(
            this, R.anim.slide_in_left, R.anim.slide_out_right
        )
        startActivity(intent, options.toBundle())
    }

    private fun checkAndRequestPermissions(): Boolean {
        val permissionsToRequest = mutableListOf<String>()

        // Check for ACTIVITY_RECOGNITION permission (Required from Android 10)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.ACTIVITY_RECOGNITION)
        }

        // Foreground Service permissions
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE)
            != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.FOREGROUND_SERVICE)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        // Request permissions if any are missing
        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_CODE_PERMISSIONS
            )
            return false // Permissions not fully granted yet
        }

        return true // All required permissions are granted
    }

    private fun startHealthNotificationService(weight: String, calories: String) {
        val intent = Intent(this, HealthNotifications::class.java)
        intent.putExtra("weight", weight)
        intent.putExtra("calories", calories)
        startService(intent)
    }

    private fun setupWork() {
        // Scheduled Notifications
        scheduleWork("scheduled", "Good Morning! It's 8 AM time to Log your breakfast.", "", 8, 0)
        scheduleWork("scheduled", "Time for a quick break! Add your lunch!", "", 12, 0)
        scheduleWork("scheduled", "It's 6 PM. Time to wrap up your day with a nice dinner!", "", 18, 0)
    }

    private fun scheduleWork(type: String, value1: String, value2: String, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        val now = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        if (calendar.before(now)) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        val delay = calendar.timeInMillis - System.currentTimeMillis()

        val inputData = when (type) {
            "health" -> workDataOf("type" to type, "weight" to value1, "calories" to value2)
            "scheduled" -> workDataOf("type" to type, "title" to value1, "content" to value2)
            else -> throw IllegalArgumentException("Invalid notification type")
        }

        val workRequest = OneTimeWorkRequestBuilder<Notifications>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .build()

        WorkManager.getInstance(this).enqueue(workRequest)
    }

    private fun startStepCounterService() {
        // Check and request permissions before starting the service
        val permissions = checkAndRequestPermissions()

        Log.d("Perms", permissions.toString())

        if (permissions) {
            val service = Intent(this, StepCounterService::class.java)
            startForegroundService(service)  // For Android O and above
        } else {
            // Handle the case where permissions are not granted yet
            Toast.makeText(this, "Permissions not granted. Service not started.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        networkMonitor.unregisterNetworkCallback(networkCallback)
    }

    private fun showPermissionExplanation(permission: String, requestCode: Int) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            // Show an explanation to the user why you need the permission
            AlertDialog.Builder(this)
                .setTitle("Permission Required")
                .setMessage("This app requires $permission for tracking steps.")
                .setPositiveButton("OK") { _, _ ->
                    ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
                }
                .setNegativeButton("Cancel", null)
                .show()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
    }


    // Handle the result of the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            val permissionsGranted = permissions.indices.all { grantResults[it] == PackageManager.PERMISSION_GRANTED }

            if (permissionsGranted) {
                // All required permissions were granted, now start the service
            }
        }
    }
}