package com.example.doorshieldactuator.navigation


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.doorshieldactuator.ui.components.DoorShieldBottomBar
import com.example.doorshieldactuator.ui.screens.HistoryScreen
import com.example.doorshieldactuator.ui.screens.HomeScreen
import com.example.doorshieldactuator.ui.screens.ProfileScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    Scaffold(

        containerColor =
            MaterialTheme.colorScheme.background,

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

            composable(
                route = AppDestination.Home.route
            ) {

                HomeScreen()
            }

            composable(
                route = AppDestination.History.route
            ) {

                HistoryScreen()
            }

            composable(
                route = AppDestination.Profile.route
            ) {

                ProfileScreen()
            }
        }
    }
}