package com.example.doorshieldactuator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.doorshieldactuator.navigation.AppNavigation
import com.example.doorshieldactuator.ui.theme.DoorShieldActuatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DoorShieldActuatorTheme {
                AppNavigation()
            }
        }
    }
}
