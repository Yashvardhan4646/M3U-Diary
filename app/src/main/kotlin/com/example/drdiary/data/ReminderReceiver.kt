package com.example.drdiary.data

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.drdiary.MainActivity
import com.example.drdiary.R

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val action = intent?.action
        if (action == Intent.ACTION_BOOT_COMPLETED) {
            // Reschedule after reboot
            val repo = PreferencesRepository(context)
            val prefs = repo.preferences.value
            if (prefs.reminderEnabled) {
                ReminderScheduler.scheduleReminder(context, prefs.reminderHour, prefs.reminderMinute)
            }
            return
        }

        // Action daily reminder triggered
        sendReminderNotification(context)

        // Reschedule for next day
        val repo = PreferencesRepository(context)
        val prefs = repo.preferences.value
        if (prefs.reminderEnabled) {
            ReminderScheduler.scheduleReminder(context, prefs.reminderHour, prefs.reminderMinute)
        }
    }

    companion object {
        const val CHANNEL_ID = "m3diary_daily_reminders"
        const val NOTIFICATION_ID = 1001

        fun sendReminderNotification(context: Context) {
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    "Daily Reflection Reminders",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "Daily notification reminding you to journal and record your mood"
                    enableVibration(true)
                }
                notificationManager.createNotificationChannel(channel)
            }

            val launchIntent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                launchIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val notification = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Time for your daily reflection ✍️")
                .setContentText("How was your day? Take a quiet moment to record your thoughts & mood in M3Diary.")
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText("How was your day? Take a quiet moment to record your thoughts, feelings, and memories in M3Diary.")
                )
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build()

            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }
}
