package com.example.doorshieldactuator.data.repository.notification


import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.doorshieldactuator.R

object NotificationHelper {

    private const val CHANNEL_ID = "door_motion_channel"
    private const val CHANNEL_NAME = "DoorShield Alerts"

    fun createNotificationChannel(
        context: Context
    ) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            context
                .getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }
    }

    fun showMotionNotification(
        context: Context
    ) {

        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notification = NotificationCompat.Builder(
            context,
            CHANNEL_ID
        )
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("DoorShield")
            .setContentText("Motion detected at the door.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat
            .from(context)
            .notify(
                1001,
                notification
            )
    }
}