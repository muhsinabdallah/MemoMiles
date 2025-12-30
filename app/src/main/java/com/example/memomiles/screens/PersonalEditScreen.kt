@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.memomiles.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.memomiles.viewmodel.PersonalEntryViewModel
import kotlinx.coroutines.launch

/**
 * PersonalEditScreen.kt
 * Screen for editing an existing personal journal entry.
 * Loads the entry from DB by ID, displays it, and allows saving updates.
 */

@Composable
fun PersonalEditScreen(
    navController: NavHostController,
    entryId: Int,
    vm: PersonalEntryViewModel = viewModel()
) {
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }

    var titleError by remember { mutableStateOf(false) }
    var bodyError by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(entryId) {
        val entry = vm.getPersonalEntryById(entryId)
        entry?.let {
            title = it.title
            body = it.body
        }
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Personal Entry") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Go Back")
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
        if (isLoading) {
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
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
                        titleError = false
                    },
                    label = { Text("Title") },
                    isError = titleError,
                    modifier = Modifier.fillMaxWidth()
                )
                if (titleError) {
                    Text("Title cannot be empty", color = Color.Red)
                }

                OutlinedTextField(
                    value = body,
                    onValueChange = {
                        body = it
                        bodyError = false
                    },
                    label = { Text("Body") },
                    isError = bodyError,
                    maxLines = 10,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )
                if (bodyError) {
                    Text("Body cannot be empty", color = Color.Red)
                }

                Button(
                    onClick = {
                        titleError = title.isBlank()
                        bodyError = body.isBlank()

                        if (!titleError && !bodyError) {
                            scope.launch {
                                vm.updatePersonalEntry(entryId, title, body)
                                navController.popBackStack()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4E342E),
                        contentColor = Color.White
                    )
                ) {
                    Text("Save Changes")
                }
            }
        }
    }
}
