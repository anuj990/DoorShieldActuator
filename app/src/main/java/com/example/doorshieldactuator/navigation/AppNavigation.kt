package com.example.doorshieldactuator.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.doorshieldactuator.ui.components.DoorShieldBottomBar
import com.example.doorshieldactuator.ui.components.GlassBackground
import com.example.doorshieldactuator.ui.screens.HistoryScreen
import com.example.doorshieldactuator.ui.screens.HomeScreen
import com.example.doorshieldactuator.ui.screens.ProfileScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    GlassBackground {

        Scaffold(
            containerColor = Color.Transparent,
            bottomBar = {
                DoorShieldBottomBar(
                    navController = navController
                )
            }
        ) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = AppDestination.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {

                composable(AppDestination.Home.route) {
                    HomeScreen()
                }

                composable(AppDestination.History.route) {
                    HistoryScreen()
                }

                composable(AppDestination.Profile.route) {
                    ProfileScreen()
                }
            }
        }
    }
}