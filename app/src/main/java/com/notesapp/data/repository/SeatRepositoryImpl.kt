package com.notesapp.data.repository

import com.notesapp.domain.model.Seat
import com.notesapp.domain.model.SeatCategory
import com.notesapp.domain.model.SeatLayout
import com.notesapp.domain.model.SeatStatus
import com.notesapp.domain.repository.SeatRepository
import javax.inject.Inject

class SeatRepositoryImpl @Inject constructor(): SeatRepository {
    private val seatLayouts = mutableMapOf<Pair<String, String>, SeatLayout>()

    override suspend fun getSeatLayout(theatreId: String, showId: String): SeatLayout {
        val key = theatreId to showId
        return seatLayouts.getOrPut(key) { generateSeatLayout() }
    }
    override suspend fun updateSeatSelection(theatreId: String, showId: String, seatId: String): SeatLayout {
        val key = theatreId to showId
        val layout = seatLayouts[key] ?: generateSeatLayout()
        val updatedSeats = layout.seats.map {
            if (it.id == seatId && it.status != SeatStatus.BOOKED) {
                it.copy(status = if (it.status == SeatStatus.SELECTED) SeatStatus.AVAILABLE else SeatStatus.SELECTED)
            } else it
        }
        val updatedLayout = layout.copy(seats = updatedSeats)
        seatLayouts[key] = updatedLayout
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