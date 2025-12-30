@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.memomiles.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.memomiles.viewmodel.TravelEntryViewModel
import kotlinx.coroutines.launch

/**
 * TravelEditScreen.kt
 *
 * This screen allows users to **edit** a previously saved travel journal entry.
 * The entry is loaded from Room database using its ID,
 * displayed in editable form fields, and updated on submission.
 */

@Composable
fun TravelEditScreen(
    navController: NavHostController,
    entryId: Int,
    vm: TravelEntryViewModel = viewModel()
) {
    // State to track loading
    var isLoading by remember { mutableStateOf(true) }

    // Form field states
    var destination by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var activities by remember { mutableStateOf("") }
    var food by remember { mutableStateOf("") }
    var peopleMet by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("1") }

    // Error states
    var destinationError by remember { mutableStateOf(false) }
    var dateError by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    // Load the entry when the screen is first composed
    LaunchedEffect(entryId) {
        val entry = vm.getTravelEntryById(entryId)
        entry?.let {
            destination = it.destination
            date = it.date
            activities = it.activities
            food = it.food
            peopleMet = it.peopleMet
            rating = it.rating.toString()
        }
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Travel Entry") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->

        // Show loading indicator while fetching entry
        if (isLoading) {
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Editable Form
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Destination Field
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
                        text = "Destination cannot be empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // Date Field
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
                        text = "Date cannot be empty",
                        color = MaterialTheme.colorScheme.error
                    )
                }

                // Other Details
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

                // Save Button
                Button(
                    onClick = {
                        destinationError = destination.isBlank()
                        dateError = date.isBlank()

                        if (!destinationError && !dateError) {
                            coroutineScope.launch {
                                vm.updateTravelEntry(
                                    id = entryId,
                                    destination = destination,
                                    date = date,
                                    activities = activities,
                                    food = food,
                                    peopleMet = peopleMet,
                                    rating = rating.toIntOrNull() ?: 1
                                )
                                navController.popBackStack()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Save Changes")
                }
            }
        }
    }
}
