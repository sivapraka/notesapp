package com.notesapp.ui.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.notesapp.features.login.presentation.event.LoginEvent
import com.notesapp.features.login.presentation.ui.LoginScreen
import com.notesapp.features.login.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity:ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp() // Your app theme wrapper if needed
        }
    }


    @Composable
    fun MyApp() {
        val navController = rememberNavController()
        val viewModel: LoginViewModel = hiltViewModel()
       // val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            val token = viewModel.getSavedToken()
            if (!token.isNullOrBlank()) {
                viewModel.onEvent(LoginEvent.OnEmailChanged("cached@user.com")) // Optional
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }

        NavHost(
            navController = navController,
            startDestination = "login"
        ) {
            composable("login") {
                LoginScreen(viewModel = viewModel)
            }
            composable("home") {
                MoviesScreen()
            }
        }
    }

}