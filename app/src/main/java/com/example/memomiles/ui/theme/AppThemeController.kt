package com.example.memomiles.ui.theme

import androidx.compose.runtime.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeController {
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    fun setDarkTheme(enabled: Boolean) {
        _isDarkTheme.value = enabled
    }
}

val LocalThemeController = compositionLocalOf { ThemeController() }

object AppThemeController {
    val current: ThemeController
        @Composable get() = LocalThemeController.current
}
