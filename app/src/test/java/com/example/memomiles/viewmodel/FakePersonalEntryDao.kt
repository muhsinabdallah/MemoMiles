package com.example.memomiles.viewmodel

import com.example.memomiles.data.PersonalEntry

/**
 * Fake DAO used for unit testing PersonalEntryViewModel logic
 * without interacting with a real Room database.
 */
class FakePersonalEntryDao {

    private val entries = mutableListOf<PersonalEntry>()
    private var nextId = 1

    fun insert(entry: PersonalEntry) {
        val entryWithId = entry.copy(id = nextId++)
        entries.add(entryWithId)
    }

    fun getById(id: Int): PersonalEntry? {
        return entries.find { it.id == id }
    }

    fun update(entry: PersonalEntry) {
        val index = entries.indexOfFirst { it.id == entry.id }
        if (index != -1) {
            entries[index] = entry
        }
    }

    fun getAll(): List<PersonalEntry> = entries
}
