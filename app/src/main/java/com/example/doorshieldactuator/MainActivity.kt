package com.example.doorshieldactuator

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import com.example.doorshieldactuator.data.repository.notification.NotificationHelper
import com.example.doorshieldactuator.navigation.AppNavigation
import com.example.doorshieldactuator.ui.theme.DoorShieldActuatorTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        NotificationHelper.createNotificationChannel(this)

        requestNotificationPermission()

        setContent {
            DoorShieldActuatorTheme {
                AppNavigation()
            }
        }
    }

    private fun requestNotificationPermission() {

        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.POST_NOTIFICATIONS
                ),
                100
            )
        }
    }
}