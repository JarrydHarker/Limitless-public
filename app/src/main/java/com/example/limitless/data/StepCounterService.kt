package com.example.limitless.data

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.limitless.MainActivity
import com.example.limitless.R
import com.example.limitless.activityViewModel
import java.util.Calendar

class StepCounterService : Service() {

    private lateinit var pedometer: Pedometer
    private lateinit var notificationManager: NotificationManager
    private val CHANNEL_ID = "step_counter_channel"

    companion object {
        const val NOTIFICATION_ID = 1
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Ensure foreground service is running
        startForeground(NOTIFICATION_ID, buildNotification("${activityViewModel.steps} Steps"))

        // Start listening for step count updates
        startTrackingSteps()

        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create the notification channel
        createNotificationChannel()

        // Initialize the pedometer and notification manager
        pedometer = Pedometer(this)

        // Schedule the midnight reset
        scheduleMidnightReset()

        // Start the foreground service with the initial notification
        val initialNotification = buildNotification("${activityViewModel.steps} Steps")
        startForeground(NOTIFICATION_ID, initialNotification)
    }

    private fun startTrackingSteps() {
        pedometer.startListening { currentSteps ->
            updateNotification(currentSteps)
        }
    }

    private fun scheduleMidnightReset() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, StepResetReceiver::class.java) // This is your BroadcastReceiver
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Calculate the time until midnight
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.add(Calendar.DAY_OF_YEAR, 1) // Schedule for the next midnight

        // Set a repeating alarm for every day at midnight
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun calculateStepsForTheDay(stepsSinceReboot: Long): Long {
        // Logic to calculate steps for the day. This can be saved and compared using shared preferences or a database.
        return stepsSinceReboot // Example, customize this for your needs.
    }

    private fun updateNotification(stepCount: Long) {
        val notification = buildNotification("$stepCount Steps")
        notificationManager.notify(NOTIFICATION_ID, notification) // Update the notification
    }

    private fun buildNotification(contentText: String): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Live Step Counter")
            .setContentText(contentText) // Update the content with the step count
            .setSmallIcon(R.drawable.logo_bg_white)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)
            .setOngoing(true)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle())
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .build()
    }

    private fun createNotificationChannel() {
        val name = "Step Counter Channel"
        val descriptionText = "Channel for live step counter"
        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
            setSound(null, null)
        }
        notificationManager.createNotificationChannel(channel)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}

