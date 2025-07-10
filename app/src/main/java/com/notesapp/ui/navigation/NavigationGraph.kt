package com.notesapp.ui.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.notesapp.ui.bookings.BookingScreen
import com.notesapp.ui.home.HomeScreen
import com.notesapp.ui.profile.ProfileScreen
import com.notesapp.ui.theatre.TheatreScreen


@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.Theatre.route) { TheatreScreen() }
        composable(BottomNavItem.Bookings.route) { BookingScreen() }
        composable(BottomNavItem.Profile.route) { ProfileScreen() }
    }
}
