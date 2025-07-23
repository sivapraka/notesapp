package com.notesapp.presentation.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.notesapp.presentation.language.LanguageViewModel
import com.notesapp.presentation.location.LocationViewModel
import com.notesapp.presentation.navigation.BottomNavigationBar
import com.notesapp.presentation.navigation.NavigationGraph
import com.notesapp.presentation.navigation.TopBar
import com.notesapp.presentation.navigation.TopBar1
import com.notesapp.presentation.profile.ProfileViewModel
import com.notesapp.presentation.profile.isDarkTheme
import com.notesapp.presentation.theme.AppTheme
import com.notesapp.presentation.timezone.TimezoneViewModel


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
@Composable
fun MainScreen() {
    val timezoneViewModel: TimezoneViewModel = hiltViewModel()
    val profileViewModel: ProfileViewModel = hiltViewModel()
    // Collect Profile state (theme & font)
    val profileUiState by profileViewModel.uiState.collectAsState()
    val locationViewModel:LocationViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        timezoneViewModel.getTimezone()
        locationViewModel.fetchLocationAndPlace()
    }

    val navController = rememberNavController()

    AppTheme(
        darkTheme = profileUiState.selectedTheme.isDarkTheme(),
        fontFamily = profileUiState.selectedFont.toFontFamily(),
        content = {
            Scaffold(
                topBar = { TopBar1(locationViewModel) },
                bottomBar = { BottomNavigationBar(navController) }
            ) { innerPadding ->
                NavigationGraph(navController, modifier = Modifier.padding(innerPadding))
            }
        }
    )
}
