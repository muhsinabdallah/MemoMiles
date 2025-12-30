package com.example.memomiles.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * PersonalEntryDao.kt
 * This DAO provides functions to insert, update, fetch, and delete personal journal entries.
 */

@Dao
interface PersonalEntryDao {

    /**
     * Inserts a new personal entry.
     */
    @Insert
    suspend fun insert(entry: PersonalEntry)

    /**
     * Updates an existing personal entry.
     */
    @Update
    suspend fun update(entry: PersonalEntry)

    /**
     * Deletes a personal entry.
     */
    @Delete
    suspend fun delete(entry: PersonalEntry)

    /**
     * Retrieves all entries ordered by newest first .
     */
    @Query("SELECT * FROM PersonalEntry ORDER BY date DESC")
    fun getAll(): Flow<List<PersonalEntry>>

    /**
     * Retrieves all entries ordered by newest first (One-time fetch).
     */
    @Query("SELECT * FROM PersonalEntry ORDER BY date DESC")
    suspend fun getAllOnce(): List<PersonalEntry>

    /**
     * Gets one entry by ID for editing.
     */
    @Query("SELECT * FROM PersonalEntry WHERE id = :id")
    suspend fun getById(id: Int): PersonalEntry?
}
