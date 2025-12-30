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
import com.example.memomiles.viewmodel.PersonalEntryViewModel
import kotlinx.coroutines.launch

/**
 * PersonalDetailScreen.kt
 *
 * Displays full content of a personal journal entry based on its ID.
 * Includes:
 * - Chocolate brown top app bar
 * - Title and body styling
 * - Loading indicator
 * - FAB to edit the entry
 */

@Composable
fun PersonalDetailScreen(
    navController: NavHostController,
    entryId: Int,
    vm: PersonalEntryViewModel
) {
    // Entry state
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()

    // Load the entry from DB
    LaunchedEffect(entryId) {
        scope.launch {
            val entry = vm.getPersonalEntryById(entryId)
            entry?.let {
                title = it.title
                body = it.body
            }
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Personal Entry Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4E342E),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },

        // Floating Action Button to edit this entry
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("personal_edit/$entryId") },
                containerColor = Color(0xFFA1887F)
            ) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Entry", tint = Color.White)
            }
        }
    ) { padding ->
        if (isLoading) {
            // Show spinner while data is loading
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Display entry content
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color(0xFF4E342E)
                )

                Divider(thickness = 1.dp, color = Color(0xFFBCAAA4))

                Text(
                    text = body,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

