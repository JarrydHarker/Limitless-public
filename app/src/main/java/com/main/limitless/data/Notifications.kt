package com.main.limitless.data

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.main.limitless.MainActivity
import com.main.limitless.R
import java.util.Calendar

class Notifications : Service() {

    private lateinit var notificationManager: NotificationManager
    private val CHANNEL_ID = "notification_channel"

    companion object {
        const val NOTIFICATION_ID = 1
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Start foreground service
        startForeground(NOTIFICATION_ID, buildNotification("Service Started"))

        // Schedule the notifications
        scheduleNotification(10, 0, "Good Morning! It's 10 AM.")
        scheduleNotification(13, 10, "It's 12:30 PM. Time for a quick break!")
        scheduleNotification(17, 0, "It's 5 PM. Time to wrap up your day.")

        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannel()
    }

    private fun scheduleNotification(hour: Int, minute: Int, message: String) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlarmReceiver::class.java).apply {
            putExtra("notification_message", message)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            this, hour * 100 + minute, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
        }

        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1)
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
    }

    private fun buildNotification(contentText: String): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Scheduled Notification")
            .setContentText(contentText)
            .setSmallIcon(R.drawable.logo_bg_white)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(false)
            .setOngoing(true)
            .setStyle(NotificationCompat.BigTextStyle())
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .build()
    }

    private fun createNotificationChannel() {
        val name = "Notification Channel"
        val descriptionText = "Channel for scheduled notifications"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        notificationManager.createNotificationChannel(channel)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val message = intent.getStringExtra("notification_message")
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannelId = "notification_channel"

        val notification = NotificationCompat.Builder(context, notificationChannelId)
            .setContentTitle("Scheduled Notification")
            .setContentText(message)
            .setSmallIcon(R.drawable.logo_bg_white)
            .build()

        notificationManager.notify((System.currentTimeMillis() / 1000).toInt(), notification)
    }
}






