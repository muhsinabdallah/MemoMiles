package com.example.memomiles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.memomiles.navigation.AppNavGraph
import com.example.memomiles.ui.theme.MemoMilesTheme
import com.example.memomiles.viewmodel.PersonalEntryViewModel
import com.example.memomiles.viewmodel.TravelEntryViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Allow dark mode toggle using rememberSaveable (can persist via ViewModel or DataStore)
            var isDarkTheme by remember { mutableStateOf(false) }

            MemoMilesTheme(darkTheme = isDarkTheme) {
                MemoMilesApp(isDarkTheme = isDarkTheme, onThemeToggle = { isDarkTheme = !isDarkTheme })
            }
        }
    }
}

@Composable
fun MemoMilesApp(
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit
) {
    val navController = rememberNavController()
    val travelViewModel: TravelEntryViewModel = viewModel()
    val personalViewModel: PersonalEntryViewModel = viewModel()

    AppNavGraph(
        navController = navController,
        travelViewModel = travelViewModel,
        personalViewModel = personalViewModel,
        isDarkTheme = isDarkTheme,
        onThemeToggle = onThemeToggle
    )
}
