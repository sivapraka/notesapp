package com.notesapp.data.repository

import com.notesapp.domain.model.Seat
import com.notesapp.domain.model.SeatCategory
import com.notesapp.domain.model.SeatLayout
import com.notesapp.domain.model.SeatStatus
import com.notesapp.domain.repository.SeatRepository
import javax.inject.Inject

class SeatRepositoryImpl @Inject constructor() : SeatRepository {
    private val seatLayouts = mutableMapOf<Pair<String, String>, SeatLayout>()

    override suspend fun getSeatLayout(theatreId: String, showId: String): SeatLayout {
        val key = theatreId to showId
        return seatLayouts.getOrPut(key) { generateSeatLayout() }
    }

    override suspend fun updateSeatSelection(
        theatreId: String,
        showId: String,
        seatId: String,
        maxSelection: Int
    ): SeatLayout {
        val key = theatreId to showId
        val layout = getSeatLayout(theatreId, showId)
        val selectedSeatsCount = layout.seats.count { it.status == SeatStatus.SELECTED }
        val updatedSeats = layout.seats.map { seat ->
            if (seat.id == seatId && seat.status != SeatStatus.RESERVED && seat.status != SeatStatus.BOOKED) {
                val newStatus = when (seat.status) {
                    SeatStatus.AVAILABLE -> {
                        if (selectedSeatsCount < maxSelection) SeatStatus.SELECTED else seat.status
                    }
                    SeatStatus.SELECTED -> SeatStatus.AVAILABLE
                    else -> seat.status
                }
                seat.copy(status = newStatus)
            } else seat
        }
        val updatedLayout = layout.copy(seats = updatedSeats)
        seatLayouts[key] = updatedLayout
        // saveSeatLayout(theatreId, showId, updatedLayout)
        return updatedLayout
    }

    private fun generateSeatLayout(): SeatLayout {
        val rows = 5
        val columns = 10
        val seats = mutableListOf<Seat>()
        for (row in 0 until rows) {
            for (col in 0 until columns) {
                if (col != 4 && col != 5) {
                    seats.add(
                        Seat(
                            id = "$row-$col",
                            row = row,
                            column = col,
                            status = SeatStatus.AVAILABLE,
                            category = SeatCategory.STANDARD,
                            price = 220.0f
                        )
                    )
                }
            }
        }
        return SeatLayout(rows, columns, seats, aislePositions = listOf(4, 5))
    }

}