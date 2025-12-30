package com.example.memomiles.viewmodel

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for PersonalEntryViewModelFake.
 * Ensures add, update, and fetch logic works correctly.
 */
class PersonalEntryViewModelTest {

    private lateinit var dao: FakePersonalEntryDao
    private lateinit var viewModel: PersonalEntryViewModelFake

    @Before
    fun setup() {
        dao = FakePersonalEntryDao()
        viewModel = PersonalEntryViewModelFake(dao)
    }

    @Test
    fun addEntry_increasesListSize() {
        val initialSize = viewModel.getAllEntries().size

        viewModel.addEntry("My Day", "It was great!")

        val newSize = viewModel.getAllEntries().size
        assertEquals(initialSize + 1, newSize)
    }

    @Test
    fun updateEntry_changesItsContent() {
        viewModel.addEntry("Old Title", "Old Body")
        val addedEntry = viewModel.getAllEntries()[0]

        viewModel.updatePersonalEntry(
            id = addedEntry.id,
            title = "Updated Title",
            body = "Updated Body"
        )

        val updated = viewModel.getPersonalEntryById(addedEntry.id)

        assertEquals("Updated Title", updated?.title)
        assertEquals("Updated Body", updated?.body)
    }

    @Test
    fun getPersonalEntryById_returnsCorrectEntry() {
        viewModel.addEntry("Entry A", "Body A")
        val entry = viewModel.getAllEntries()[0]

        val fetched = viewModel.getPersonalEntryById(entry.id)

        assertNotNull(fetched)
        assertEquals(entry.id, fetched?.id)
    }
}
