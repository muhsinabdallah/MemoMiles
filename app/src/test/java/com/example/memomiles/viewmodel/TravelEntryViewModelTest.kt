package com.example.memomiles.viewmodel

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TravelEntryViewModelTest {

    private lateinit var viewModel: TravelEntryViewModelFake

    @Before
    fun setup() {
        viewModel = TravelEntryViewModelFake()
    }

    @Test
    fun addEntry_increasesListSize() {
        val initialCount = viewModel.entries.size
        viewModel.addEntry("Paris", "2025-01-01", "Sightseeing", "Food", "People", 5)
        assertEquals(initialCount + 1, viewModel.entries.size)
    }

    @Test
    fun updateEntry_correctlyUpdatesTheEntry() {
        viewModel.addEntry("Old", "2020", "Old activities", "Old food", "Old people", 2)
        val id = viewModel.entries[0].id

        viewModel.updateEntry(id, "New", "2025", "New activities", "New food", "New people", 4)

        val updated = viewModel.entries[0]
        assertEquals("New", updated.destination)
        assertEquals("2025", updated.date)
        assertEquals("New activities", updated.activities)
        assertEquals("New food", updated.food)
        assertEquals("New people", updated.peopleMet)
        assertEquals(4, updated.rating)
    }
}
