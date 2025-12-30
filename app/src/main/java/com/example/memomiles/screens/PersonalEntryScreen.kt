@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.memomiles.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.memomiles.viewmodel.PersonalEntryViewModel

/**
 * PersonalEntryScreen.kt
 * Lets the user write a personal journal entry with title/body + optional media.
 * Saves to Room database using ViewModel.
 */

@Composable
fun PersonalEntryScreen(
    navController: NavHostController,
    vm: PersonalEntryViewModel = viewModel()
) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    // Error state for validation
    var showError by remember { mutableStateOf(false) }

    // Media file URI (optional)
    var selectedMediaUri by remember { mutableStateOf<Uri?>(null) }

    // Media picker launcher
    val mediaPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedMediaUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Personal Entry") },
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
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = {
                    title = it
                    if (it.isNotBlank()) showError = false
                },
                label = { Text("Title") },
                isError = showError && title.isBlank(),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = body,
                onValueChange = {
                    body = it
                    if (it.isNotBlank()) showError = false
                },
                label = { Text("Body") },
                isError = showError && body.isBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp),
                maxLines = 10
            )

            if (showError && (title.isBlank() || body.isBlank())) {
                Text(
                    text = "Please fill in all required fields",
                    color = Color(0xFFD32F2F),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Upload Button
            Button(
                onClick = { mediaPickerLauncher.launch("*/*") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFA1887F) // Secondary color
                )
            ) {
                Text("Upload Photo/Video")
            }

            // Save Button
            Button(
                onClick = {
                    if (title.isBlank() || body.isBlank()) {
                        showError = true
                    } else {
                        vm.addEntry(title, body)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4E342E))
            ) {
                Text("Save Entry", color = Color.White)
            }
        }
    }
}
