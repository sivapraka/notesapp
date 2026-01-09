package com.notesapp.presentation.navigation


import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.notesapp.presentation.home.HomeScreen
import com.notesapp.presentation.home.MovieListScreen
import com.notesapp.presentation.home.movidedetails.MovieDetailScreen
import com.notesapp.presentation.profile.ProfileRoute
import com.notesapp.presentation.profile.ProfileScreen
import com.notesapp.presentation.seatselection.SeatSelectionScreen
import com.notesapp.presentation.theatre.TheatreScreen


@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Bookings.route,
        modifier = modifier
    ) {
        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.Theatre.route) { TheatreScreen() }
        composable(BottomNavItem.Bookings.route) { MovieListScreen(navController) }
        composable(BottomNavItem.Profile.route) { ProfileRoute() }

        // Movie Details Screen with movieId argument
        composable(
            route = "movie_details/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            MovieDetailScreen(movieId = movieId,navController)
        }

        // Seat Selection Screen
        composable(
            route = "seatSelection/{movieId}/{theatreId}/{showId}",
            arguments = listOf(
                navArgument("movieId") { type = NavType.IntType },
                navArgument("theatreId") { type = NavType.StringType },
                navArgument("showId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: 0
            val theatreId = backStackEntry.arguments?.getString("theatreId").orEmpty()
            val showId = backStackEntry.arguments?.getString("showId").orEmpty()

            Log.e("TAG", "NavigationGraph: SeatSelectionScreen called with movieId=$movieId, theatreId=$theatreId, showId=$showId")

            SeatSelectionScreen(
                movieId = movieId,
                theatreId = theatreId,
                showId = showId,
                onBuyTicketClick = {
                    // TODO: Handle post-booking logic (e.g., show success dialog or navigate to bookings)
                }
            )
        }

    }
}
