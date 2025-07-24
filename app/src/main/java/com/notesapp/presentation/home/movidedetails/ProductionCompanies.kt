package com.notesapp.presentation.home.movidedetails

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import coil.compose.*
import com.notesapp.data.local.entity.ImdbProductionCompanies

@Composable
fun ProductionCompanies(companies: List<ImdbProductionCompanies>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Production Companies", color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        companies.forEach { company ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (!company.logo_path.isNullOrEmpty()) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w92${company.logo_path}",
                        contentDescription = company.name,
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(company.name, color = Color.LightGray)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}