@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.memomiles.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.memomiles.R
import kotlinx.coroutines.delay
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings

/**
 * HomeScreen.kt
 * Serves as the app's main menu with a warm welcome message, a themed teddy image,
 * it contains navigation buttons to create or view journal entries.
 */

@Composable
fun HomeScreen(navController: NavHostController) {
    // Controls the fade-in of the welcome text
    var showWelcome by remember { mutableStateOf(false) }

    // This triggers a small delay then reveal the welcome message
    LaunchedEffect(Unit) {
        delay(400)
        showWelcome = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MemoMiles") },

                // Right-side toolbar which opens the Settings screen
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                    }
                },

                // App-wide brand color for the top bar
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4E342E),
                    titleContentColor = Color.White
                )
            )
        }
    ) { padding ->
        // Main column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Mascot Image
            Image(
                painter = painterResource(id = R.drawable.teddy_with_pencil),
                contentDescription = "Teddy mascot holding a pencil",
                modifier = Modifier
                    .size(150.dp)
                    .padding(top = 12.dp)
            )

            // Welcome headline
            AnimatedVisibility(visible = showWelcome, enter = fadeIn()) {
                Text(
                    text = "Welcome to MemoMiles!",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF4E342E)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))



            // Start a new Personal journal entry
            Button(
                onClick = { navController.navigate("personal_entry") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6D4C41))
            ) {
                Text("✏️ New Personal Entry", color = Color.White)
            }

            // Start a new Travel journal entry
            Button(
                onClick = { navController.navigate("travel_entry") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5D4037))
            ) {
                Text("🌍 New Travel Entry", color = Color.White)
            }

            // View all saved entries (both Personal + Travel)
            Button(
                onClick = { navController.navigate("archive") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3E2723))
            ) {
                Text("📚 View Archive", color = Color.White)
            }
        }
    }
}
