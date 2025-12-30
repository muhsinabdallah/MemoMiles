package com.example.memomiles.viewmodel

data class FakeTravelEntry(
    val id: Int,
    var destination: String,
    var date: String,
    var activities: String,
    var food: String,
    var peopleMet: String,
    var rating: Int
)

class TravelEntryViewModelFake {

    private var idCounter = 1
    val entries = mutableListOf<FakeTravelEntry>()

    fun addEntry(
        destination: String,
        date: String,
        activities: String,
        food: String,
        peopleMet: String,
        rating: Int
    ) {
        entries.add(
            FakeTravelEntry(
                id = idCounter++,
                destination = destination,
                date = date,
                activities = activities,
                food = food,
                peopleMet = peopleMet,
                rating = rating
            )
        )
    }

    fun updateEntry(
        id: Int,
        destination: String,
        date: String,
        activities: String,
        food: String,
        peopleMet: String,
        rating: Int
    ) {
        val entry = entries.find { it.id == id }
        entry?.apply {
            this.destination = destination
            this.date = date
            this.activities = activities
            this.food = food
            this.peopleMet = peopleMet
            this.rating = rating
        }
    }
}
