package com.example.memomiles.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * PersonalEntry.kt
 * This data class defines the schema for the Personal Journal table in Room.
 * Each object represents one journal entry.
 */

@Entity
data class PersonalEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // unique ID
    val title: String,   // entry title
    val body: String,    // entry body text
    val date: Long = System.currentTimeMillis() // auto-filled timestamp
)
