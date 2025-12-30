package com.example.memomiles.data

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * JournalDatabase.kt
 * The central Room database class holding all DAOs and entities.
 */

@Database(entities = [PersonalEntry::class, TravelEntry::class], version = 1)
abstract class JournalDatabase : RoomDatabase() {
    abstract fun personalEntryDao(): PersonalEntryDao
    abstract fun travelEntryDao(): TravelEntryDao
}
