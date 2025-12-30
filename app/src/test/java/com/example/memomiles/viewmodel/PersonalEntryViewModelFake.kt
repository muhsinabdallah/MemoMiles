package com.example.memomiles.viewmodel

import com.example.memomiles.data.PersonalEntry

/**
 * Fake ViewModel for unit testing PersonalEntry logic
 * without Android or Room dependencies.
 */
class PersonalEntryViewModelFake(
    private val dao: FakePersonalEntryDao
) {

    fun addEntry(title: String, body: String) {
        dao.insert(PersonalEntry(title = title, body = body))
    }

    fun getPersonalEntryById(id: Int): PersonalEntry? {
        return dao.getById(id)
    }

    fun updatePersonalEntry(id: Int, title: String, body: String) {
        dao.update(
            PersonalEntry(
                id = id,
                title = title,
                body = body
            )
        )
    }

    fun getAllEntries(): List<PersonalEntry> {
        return dao.getAll()
    }
}
