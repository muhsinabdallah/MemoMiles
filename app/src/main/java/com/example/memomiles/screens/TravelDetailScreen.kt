@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.memomiles.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.memomiles.viewmodel.TravelEntryViewModel
import kotlinx.coroutines.launch

/**
 * TravelDetailScreen.kt
 *
 * Displays full details of a selected travel journal entry.
 * Features:
 * - Chocolate brown top app bar
 * - All saved fields shown (destination, date, food, people met, rating, activities)
 * - Edit FAB for quick access to edit screen
 */

@Composable
fun TravelDetailScreen(
    navController: NavHostController,
    entryId: Int,
    vm: TravelEntryViewModel
) {
    // UI state variables
    var destination by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var food by remember { mutableStateOf("") }
    var peopleMet by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(1) }
    var activities by remember { mutableStateOf("") }

    var isLoading by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    // Load entry when screen opens
    LaunchedEffect(entryId) {
        scope.launch {
            val entry = vm.getTravelEntryById(entryId)
            entry?.let {
                destination = it.destination
                date = it.date
                food = it.food
                peopleMet = it.peopleMet
                rating = it.rating
                activities = it.activities
            }
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Travel Entry Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4E342E), // Chocolate Brown
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },

        // FAB to edit entry
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("travel_edit/$entryId") },
                containerColor = Color(0xFFA1887F)
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color.White)
            }
        }
    ) { padding ->
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text("📍 Destination: $destination", style = MaterialTheme.typography.titleLarge)
                Text("📅 Date: $date", style = MaterialTheme.typography.bodyMedium)
                Text("🍽 Food Tried: $food", style = MaterialTheme.typography.bodyMedium)
                Text("👥 People Met: $peopleMet", style = MaterialTheme.typography.bodyMedium)
                Text("⭐ Rating: $rating/5", style = MaterialTheme.typography.bodyMedium)

                Divider(thickness = 1.dp, color = Color(0xFFBCAAA4))

                Text("🎯 Activities", style = MaterialTheme.typography.titleMedium)
                Text(activities, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
