package com.example.memomiles.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.memomiles.data.JournalDatabase
import com.example.memomiles.data.PersonalEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.launch

/**
 * PersonalEntryViewModel.kt
 * ViewModel to manage personal journal data.
 * Provides methods to insert, update, and fetch entries from the Room DB.
 */

class PersonalEntryViewModel(application: Application) : AndroidViewModel(application) {

    // Initialize Room database
    private val db = Room.databaseBuilder(
        application,
        JournalDatabase::class.java,
        "journal.db"
    ).build()

    private val dao = db.personalEntryDao()

    /**
     * Adds a new personal journal entry to the database.
     */
    fun addEntry(title: String, body: String) {
        viewModelScope.launch {
            dao.insert(PersonalEntry(title = title, body = body))
        }
    }

    /**
     * Retrieves a personal entry by ID (used for editing).
     */
    suspend fun getPersonalEntryById(id: Int): PersonalEntry? {
        return withContext(Dispatchers.IO) {
            dao.getById(id)
        }
    }

    /**
     * Updates an existing personal journal entry.
     */
    fun updatePersonalEntry(id: Int, title: String, body: String) {
        viewModelScope.launch {
            val updated = PersonalEntry(id = id, title = title, body = body)
            dao.update(updated)
        }
    }
}
