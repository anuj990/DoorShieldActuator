package com.example.doorshieldactuator.ui.components


import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.doorshieldactuator.navigation.AppDestination

@Composable
fun DoorShieldBottomBar(
    navController: NavHostController
) {

    val navBackStackEntry =
        navController.currentBackStackEntryAsState().value

    val currentDestination =
        navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface
    ) {

        AppDestination.bottomNavigationItems.forEach { destination ->

            val isSelected =
                currentDestination?.hierarchy?.any {
                    it.route == destination.route
                } == true

            NavigationBarItem(

                selected = isSelected,

                onClick = {

                    navController.navigate(destination.route) {

                        popUpTo(
                            navController.graph.startDestinationId
                        ) {
                            saveState = true
                        }

                        launchSingleTop = true

                        restoreState = true
                    }
                },

                icon = {

                    Icon(
                        imageVector = destination.icon,
                        contentDescription = destination.title
                    )
                },

                label = {

                    Text(
                        text = destination.title
                    )
                },

                colors = NavigationBarItemDefaults.colors(

                    selectedIconColor =
                        MaterialTheme.colorScheme.primary,

                    selectedTextColor =
                        MaterialTheme.colorScheme.primary,

                    indicatorColor =
                        MaterialTheme.colorScheme.primaryContainer,

                    unselectedIconColor =
                        MaterialTheme.colorScheme.onSurfaceVariant,

                    unselectedTextColor =
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}