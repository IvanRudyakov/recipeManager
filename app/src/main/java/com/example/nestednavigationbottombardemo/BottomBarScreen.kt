package com.example.nestednavigationbottombardemo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "HOME",
        title = "HOME",
        icon = Icons.Default.Home
    )

    object Users : BottomBarScreen(
        route = "USERS",
        title = "USERS",
        icon = Icons.Default.Person
    )

    object Create : BottomBarScreen(
        route = "CREATE",
        title = "CREATE",
        icon = Icons.Default.Add
    )

    object Profile : BottomBarScreen(
        route = "PROFILE",
        title = "PROFILE",
        icon = Icons.Default.AccountCircle
    )

}
