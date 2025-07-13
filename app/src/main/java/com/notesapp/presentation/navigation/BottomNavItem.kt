package com.notesapp.presentation.navigation


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val title: String
) {
    object Home : BottomNavItem("home", Icons.Default.Home, "Movies")
    object Theatre : BottomNavItem("theatre", Icons.Default.LocationOn, "Theatres")
    object Bookings : BottomNavItem("bookings", Icons.Default.Build, "Bookings")
    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
}