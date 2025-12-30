package com.example.memomiles.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.memomiles.screens.*
import com.example.memomiles.viewmodel.TravelEntryViewModel
import com.example.memomiles.viewmodel.PersonalEntryViewModel

/**
 * AppNavGraph.kt
 *
 * This file defines the navigation structure of the app.
 *
 * It uses Jetpack Navigation Compose to:
 * - Declare all app routes
 * - Define how users move between screens
 * - Pass data between screens
 *
 *
 */

@Composable
fun AppNavGraph(
    // Central navigation controller shared across the app
    navController: NavHostController,

    // ViewModels injected so screens can access app data
    travelViewModel: TravelEntryViewModel,
    personalViewModel: PersonalEntryViewModel,

    // Theme state passed down to Settings screen
    isDarkTheme: Boolean,
    onThemeToggle: () -> Unit,

    // Optional modifier for flexibility
    modifier: Modifier = Modifier
) {

    /**
     * NavHost is the container that hosts all destinations.
     * startDestination defines the first screen shown when the app launches.
     */
    NavHost(
        navController = navController,
        startDestination = "splash", // App opens with splash screen
        modifier = modifier
    ) {


        // Splash Screen
        // Temporary intro screen shown on app launch
        composable("splash") {
            SplashScreen(navController)
        }


        // Home Screen
        // Main entry point after splash screen
        composable("home") {
            HomeScreen(navController)
        }


        // Personal Journal Entry Screens

        // Create a new personal journal entry
        composable("personal_entry") {
            PersonalEntryScreen(navController, personalViewModel)
        }

        // Edit an existing personal entry
        // entryId is passed via the route
        composable("personal_edit/{entryId}") { backStackEntry ->

            // Safely extract entryId from navigation arguments
            val entryId = backStackEntry.arguments
                ?.getString("entryId")
                ?.toIntOrNull()

            // Only load screen if ID is valid
            if (entryId != null) {
                PersonalEditScreen(navController, entryId, personalViewModel)
            }
        }

        // View details of a personal entry
        composable("personal_details/{entryId}") { backStackEntry ->

            val entryId = backStackEntry.arguments
                ?.getString("entryId")
                ?.toIntOrNull()

            if (entryId != null) {
                PersonalDetailScreen(navController, entryId, personalViewModel)
            }
        }

        // Travel Journal Entry Screens

        // Create a new travel journal entry
        composable("travel_entry") {
            TravelEntryScreen(navController, travelViewModel)
        }

        // Edit an existing travel entry
        composable("travel_edit/{entryId}") { backStackEntry ->

            val entryId = backStackEntry.arguments
                ?.getString("entryId")
                ?.toIntOrNull()

            if (entryId != null) {
                TravelEditScreen(navController, entryId, travelViewModel)
            }
        }

        // View details of a travel entry
        composable("travel_details/{entryId}") { backStackEntry ->

            val entryId = backStackEntry.arguments
                ?.getString("entryId")
                ?.toIntOrNull()

            if (entryId != null) {
                TravelDetailScreen(navController, entryId, travelViewModel)
            }
        }


        // Archive Screen
        // Displays all saved personal and travel entries
        composable("archive") {
            ArchiveScreen(navController)
        }


        // Settings Screen
        // Allows user to toggle light/dark mode
        composable("settings") {
            SettingsScreen(
                navController = navController,
                isDarkTheme = isDarkTheme,
                onThemeToggle = onThemeToggle
            )
        }
    }
}