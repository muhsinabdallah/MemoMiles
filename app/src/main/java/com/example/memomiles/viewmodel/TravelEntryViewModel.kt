package com.example.memomiles.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.memomiles.data.JournalDatabase
import com.example.memomiles.data.TravelEntry
import kotlinx.coroutines.launch

/**
 * TravelEntryViewModel.kt
 * This ViewModel handles logic for saving, editing, and retrieving travel journal entries.
 * It interacts with the TravelEntryDao in the Room database.
 */

class TravelEntryViewModel(application: Application) : AndroidViewModel(application) {

    // Initialize the database and DAO
    private val db = Room.databaseBuilder(
        application,
        JournalDatabase::class.java,
        "journal.db"
    ).build()

    private val dao = db.travelEntryDao()

    /**
     * Insert a new travel journal entry into the database
     */
    fun addEntry(
        destination: String,
        date: String,
        activities: String,
        food: String,
        peopleMet: String,
        rating: Int
    ) {
        viewModelScope.launch {
            dao.insert(
                TravelEntry(
                    destination = destination,
                    date = date,
                    activities = activities,
                    food = food,
                    peopleMet = peopleMet,
                    rating = rating
                )
            )
        }
    }

    /**
     * Retrieve a travel entry by ID (used for editing)
     */
    suspend fun getTravelEntryById(id: Int): TravelEntry? {
        return dao.getById(id)
    }

    /**
     * Update an existing travel entry with new data
     */
    fun updateTravelEntry(
        id: Int,
        destination: String,
        date: String,
        activities: String,
        food: String,
        peopleMet: String,
        rating: Int
    ) {
        viewModelScope.launch {
            val updatedEntry = TravelEntry(
                id = id,
                destination = destination,
                date = date,
                activities = activities,
                food = food,
                peopleMet = peopleMet,
                rating = rating
            )
            dao.insert(updatedEntry)  // Room replaces entry if primary key matches
        }
    }
}
