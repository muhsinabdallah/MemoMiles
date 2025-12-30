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
import com.example.memomiles.viewmodel.TravelEntryViewModel

/**
 * TravelEntryScreen.kt
 *
 * Screen that allows users to create a new travel journal entry.
 * Includes:
 * - Back navigation
 * - Input validation
 * - Optional media attachment
 * - Data persistence via TravelEntryViewModel
 */

@Composable
fun TravelEntryScreen(
    navController: NavHostController,
    vm: TravelEntryViewModel = viewModel()
) {

    // Form input state variables

    var destination by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var activities by remember { mutableStateOf("") }
    var food by remember { mutableStateOf("") }
    var peopleMet by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("1") }


    // Validation flags

    var destinationError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }


    // Media picker state (optional)
    var mediaUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher for selecting an image or video
    val mediaPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri -> mediaUri = uri }
    )


    // Screen layout

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Travel Entry") },

                // Back arrow — navigates to previous screen
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },

                // App bar styling
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4E342E), // Chocolate brown
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


            // Destination (required)

            OutlinedTextField(
                value = destination,
                onValueChange = {
                    destination = it
                    destinationError = false
                },
                label = { Text("Destination") },
                isError = destinationError,
                modifier = Modifier.fillMaxWidth()
            )

            if (destinationError) {
                Text(
                    "Destination is required",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }


            // Date (required)

            OutlinedTextField(
                value = date,
                onValueChange = {
                    date = it
                    dateError = false
                },
                label = { Text("Date of Travel") },
                isError = dateError,
                modifier = Modifier.fillMaxWidth()
            )

            if (dateError) {
                Text(
                    "Date is required",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodySmall
                )
            }


            // Optional fields

            OutlinedTextField(
                value = activities,
                onValueChange = { activities = it },
                label = { Text("Activities") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = food,
                onValueChange = { food = it },
                label = { Text("Food Tried") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = peopleMet,
                onValueChange = { peopleMet = it },
                label = { Text("People Met") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = rating,
                onValueChange = { rating = it },
                label = { Text("Rating (1–5)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )


            // Media attachment (optional)

            OutlinedButton(
                onClick = { mediaPickerLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Attach Photo/Video (optional)")
            }


            // Save button
            Button(
                onClick = {
                    destinationError = destination.isBlank()
                    dateError = date.isBlank()

                    if (!destinationError && !dateError) {
                        vm.addEntry(
                            destination = destination,
                            date = date,
                            activities = activities,
                            food = food,
                            peopleMet = peopleMet,
                            rating = rating.toIntOrNull() ?: 1
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4E342E),
                    contentColor = Color.White
                )
            ) {
                Text("Save Travel Entry")
            }
        }
    }
}