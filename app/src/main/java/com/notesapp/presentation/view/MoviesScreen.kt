package com.notesapp.presentation.view


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.notesapp.presentation.language.LanguageViewModel
import com.notesapp.presentation.navigation.BottomNavigationBar
import com.notesapp.presentation.navigation.NavigationGraph
import com.notesapp.presentation.navigation.TopBar
import com.notesapp.presentation.profile.ProfileViewModel
import com.notesapp.presentation.timezone.TimezoneViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.ExperimentalTextApi
import com.notesapp.presentation.profile.isDarkTheme
import com.notesapp.presentation.theme.AppTheme

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MoviesScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }


    @SuppressLint("StateFlowValueCalledInComposition", "CoroutineCreationDuringComposition")
    @Composable
    fun MainScreen() {
        val timezoneViewModel: TimezoneViewModel = hiltViewModel()
        val profileViewModel: ProfileViewModel = hiltViewModel()
        // Collect Profile state (theme & font)
        val profileUiState by profileViewModel.uiState.collectAsState()
        lifecycleScope.launch {
            timezoneViewModel.getTimezone()
        }

        val viewModel: LanguageViewModel = hiltViewModel()
        val navController = rememberNavController()
        viewModel.languages.value.toString()
        AppTheme(
            darkTheme = profileUiState.selectedTheme.isDarkTheme(),
            fontFamily = profileUiState.selectedFont.toFontFamily(),
            content = {
                Scaffold(
                    topBar = { TopBar(viewModel) },
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavigationGraph(navController, modifier = Modifier.padding(innerPadding))
                }
            }
        )
    }

}