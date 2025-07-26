package com.notesapp.domain.model


enum class SeatStatus { CLOSED, AVAILABLE, RESERVED, SELECTED,BOOKED }

data class Seat(
    val id: String,
    val row: Int,
    val column: Int,
    val status: SeatStatus,
    val category: SeatCategory = SeatCategory.STANDARD,
    val price: Float
)

data class SeatLayout(
    val rows: Int,
    val columns: Int,
    val seats: List<Seat>,
    val aislePositions: List<Int> = emptyList()
)
data class SeatLayout1(
    val rows: Int,
    val columns: Int,
    val seats: List<List<Seat>>
)

enum class SeatCategory {
    STANDARD, BALCONY, RECLINER, PRIME
}