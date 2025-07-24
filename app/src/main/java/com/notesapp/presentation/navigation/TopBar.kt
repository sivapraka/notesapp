package com.notesapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.notesapp.presentation.components.LocationFetcher
import com.notesapp.presentation.components.NetworkStatusIndicator
import com.notesapp.presentation.language.LanguageViewModel
import com.notesapp.presentation.location.LocationViewModel
import com.notesapp.util.ApiResource


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun TopBar(
    viewModel: LanguageViewModel = hiltViewModel(),
    locationViewModel: LocationViewModel = hiltViewModel()) {
    val state by viewModel.languages.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    val isOnline by viewModel.isOnline.collectAsState()
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()
    // Observe place name
    val placeName by locationViewModel.placeName.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("TicketNew", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        //  NetworkStatusIndicator(Modifier, isOnline)
        // Box anchors the DropdownMenu to the IconButton
        Box {
            IconButton(onClick = { expanded = true }, modifier = Modifier.width(110.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Search, contentDescription = "Select language")
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(selectedLanguage, fontSize = 14.sp, overflow = TextOverflow.Ellipsis)
                }
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                when (val result = state) {
                    is ApiResource.Success -> {
                        result.data.forEach { language ->
                            DropdownMenuItem(
                                text = { Text(language.english_name ?: "Unknown") },
                                onClick = {
                                    viewModel.onCountrySelected(language.iso_639_1 ?: "Unknown")
                                    expanded = false
                                }
                            )
                        }
                    }

                    is ApiResource.Loading -> {
                        DropdownMenuItem(
                            text = { Text("Loading...") },
                            onClick = { /* no-op */ }
                        )
                    }

                    is ApiResource.Error -> {
                        DropdownMenuItem(
                            text = { Text("Error: ${result.message}") },
                            onClick = { /* no-op */ }
                        )
                    }
                }
            }
        }

        // Optional: separate location icon (if needed)
        Spacer(modifier = Modifier.width(12.dp))
        IconButton(onClick = { /* handle location */ }) {
            Icon(Icons.Default.LocationOn, contentDescription = "Location")
        }
    }
}


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun TopBar1(
    locationViewModel: LocationViewModel,
    languageViewModel: LanguageViewModel = hiltViewModel()
) {
    // Observe language name
    val selectedLanguage by languageViewModel.selectedLanguage.collectAsState()
    // Observe place name
    val placeName by locationViewModel.placeName.collectAsState()
    LaunchedEffect(Unit) {
        languageViewModel.languages()
    }
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("TicketNew", fontWeight = FontWeight.Bold, fontSize = 20.sp)

        // Middle section: show place and language selector
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Location display
            Icon(Icons.Default.LocationOn, contentDescription = "Location")
            Spacer(Modifier.width(4.dp))
            Text(
                text = placeName.ifBlank { "Locating..." },
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(Modifier.width(16.dp))

            // Language selector
            Box {
                IconButton(onClick = { expanded = true }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.Language, contentDescription = "Language")
                        Text(
                            text = selectedLanguage,
                            fontSize = 14.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Select language")
                    }
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    // You already have ApiResource<List<LanguageEntity>> in your ViewModel
                    when (val result = languageViewModel.languages.collectAsState().value) {
                        is ApiResource.Success -> {
                            result.data.forEach { lang ->
                                DropdownMenuItem(
                                    text = { Text(lang.english_name ?: "Unknown") },
                                    onClick = {
                                        languageViewModel.onCountrySelected(lang.iso_639_1)
                                        expanded = false
                                    }
                                )
                            }
                        }

                        is ApiResource.Loading -> {
                            DropdownMenuItem(text = { Text("Loading…") }, onClick = {})
                        }

                        is ApiResource.Error -> {
                            DropdownMenuItem(
                                text = { Text("Error: ${result.message}") },
                                onClick = {}
                            )
                        }
                    }
                }
            }
        }

        // Optional: network status indicator or other actions
        NetworkStatusIndicator(Modifier, languageViewModel.isOnline.collectAsState().value)
    }
}


