@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.memomiles.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.room.Room
import com.example.memomiles.data.JournalDatabase
import com.example.memomiles.data.PersonalEntry
import com.example.memomiles.data.TravelEntry
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * ArchiveScreen.kt
 * Loads all personal and travel journal entries and displays them in a list.
 * Users can tap to view details of each entry.
 */

@Composable
fun ArchiveScreen(navController: NavHostController) {
    val context = LocalContext.current

    // Setup database instance
    val db = remember {
        Room.databaseBuilder(
            context,
            JournalDatabase::class.java,
            "journal.db"
        ).build()
    }

    // Local state
    var personalEntries by remember { mutableStateOf(listOf<PersonalEntry>()) }
    var travelEntries by remember { mutableStateOf(listOf<TravelEntry>()) }

    // Fetch entries on launch
    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            personalEntries = db.personalEntryDao().getAllOnce()
            travelEntries = db.travelEntryDao().getAllOnce()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Journal Archive") },

                // Back navigation arrow
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4E342E),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Personal Entries
            item {
                Text("📘 Personal Journal Entries", style = MaterialTheme.typography.titleMedium)
            }

            items(personalEntries) { entry ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("personal_details/${entry.id}") },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)) // light green
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(entry.title, style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = if (entry.body.length > 50)
                                entry.body.take(50) + "..."
                            else entry.body,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(24.dp)) }

            // Travel Entries
            item {
                Text("🗺 Travel Journal Entries", style = MaterialTheme.typography.titleMedium)
            }

            items(travelEntries) { entry ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("travel_details/${entry.id}") },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)) // light blue
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(entry.destination, style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = if (entry.activities.length > 50)
                                entry.activities.take(50) + "..."
                            else entry.activities,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}
