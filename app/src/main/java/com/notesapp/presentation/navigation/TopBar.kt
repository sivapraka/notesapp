package com.notesapp.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.notesapp.presentation.language.LanguageViewModel
import com.notesapp.util.ApiResource


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun TopBar(viewModel: LanguageViewModel) {
  //  val selectedCountry by viewModel.country.collectAsState()
    val state by viewModel.languages.collectAsState()
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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { expanded = true }
        ) {
            Icon(Icons.Default.Search, contentDescription = "Search")
            Spacer(modifier = Modifier.width(12.dp))
            Icon(Icons.Default.LocationOn, contentDescription = "Location")
            Spacer(modifier = Modifier.width(4.dp))
          //  Text(selectedCountry)
            Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
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
                        onClick = {}
                    )
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

