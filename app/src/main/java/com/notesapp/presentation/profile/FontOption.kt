package com.notesapp.presentation.profile

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import com.notesapp.R


enum class FontOption(val displayName: String) {
    DEFAULT("Default"),
    SERIF("Serif"),
    MONOSPACE("Monospace"),
    CUSTOM("Custom");

    fun toFontFamily(): FontFamily {
        return when (this) {
            DEFAULT -> FontFamily.Default
            SERIF -> FontFamily.Serif
            MONOSPACE -> FontFamily.Monospace
            CUSTOM -> FontFamily(Font(R.font.roboto_regular)) // Add in res/font
        }
    }
}