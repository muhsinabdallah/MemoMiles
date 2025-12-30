package com.example.memomiles.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * TravelEntryDao.kt
 * Data Access Object (DAO) for managing CRUD on travel entries.
 */

@Dao
interface TravelEntryDao {

    // Insert a new entry
    @Insert
    suspend fun insert(entry: TravelEntry)

    // Update an existing entry
    @Update
    suspend fun update(entry: TravelEntry)

    // Get all entries as Flow for UI updates
    @Query("SELECT * FROM TravelEntry ORDER BY id DESC")
    fun getAll(): Flow<List<TravelEntry>>

    // Get all entries once for background use
    @Query("SELECT * FROM TravelEntry ORDER BY id DESC")
    suspend fun getAllOnce(): List<TravelEntry>

    // Find entry by ID
    @Query("SELECT * FROM TravelEntry WHERE id = :id")
    suspend fun getById(id: Int): TravelEntry?

    // Delete an entry
    @Delete
    suspend fun delete(entry: TravelEntry)
}
