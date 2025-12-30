package com.example.memomiles.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * TravelEntry.kt
 * Data class representing a travel journal entry.
 * Each entry is saved to the Room database as a row in the TravelEntry table.
 */

@Entity
data class TravelEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val destination: String,
    val date: String,
    val activities: String,
    val food: String,
    val peopleMet: String,
    val rating: Int
)
