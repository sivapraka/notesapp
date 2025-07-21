package com.notesapp.presentation.navigation


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.notesapp.presentation.bookings.BookingScreen
import com.notesapp.presentation.home.HomeScreen
import com.notesapp.presentation.profile.MovieListScreen
import com.notesapp.presentation.profile.ProfileScreen
import com.notesapp.presentation.theatre.TheatreScreen


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
        composable(BottomNavItem.Profile.route) { MovieListScreen() }
    }
}
