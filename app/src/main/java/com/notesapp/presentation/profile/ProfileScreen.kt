package com.notesapp.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onThemeSelected: (ThemeOption) -> Unit = {},
    onFontSelected: (FontOption) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Text(text = "Theme", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        ThemeOption.entries.forEach { themeOption ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = uiState.selectedTheme == themeOption,
                    onClick = { onThemeSelected(themeOption) }
                )
                Text(text = themeOption.name.lowercase().replaceFirstChar { it.uppercase() })
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "Font Style", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        FontOption.entries.forEach { fontOption ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = uiState.selectedFont == fontOption,
                    onClick = { onFontSelected(fontOption) }
                )
                Text(
                    text = fontOption.displayName,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontFamily = fontOption.toFontFamily()
                    )
                )
            }
        }
    }
}
