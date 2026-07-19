package com.example.doorshieldactuator.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person

sealed class AppDestination(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    data object Home : AppDestination(
        route = "home",
        title = "Home",
        icon = Icons.Default.Home
    )

    data object History : AppDestination(
        route = "history",
        title = "History",
        icon = Icons.Default.History
    )

    data object Profile : AppDestination(
        route = "profile",
        title = "Profile",
        icon = Icons.Default.Person
    )

    companion object {

        val bottomNavigationItems = listOf(
            Home,
            History,
            Profile
        )
    }
}