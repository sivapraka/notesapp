package com.notesapp.domain.usecase

import com.notesapp.domain.model.SeatLayout
import com.notesapp.domain.model.SeatStatus
import com.notesapp.domain.repository.SeatRepository
import javax.inject.Inject

class ToggleSeatSelectionUseCase @Inject constructor(private val repository: SeatRepository) {
    suspend operator fun invoke(theatreId: String, showId: String, seatId: String,
                                maxSelection: Int = 5 ): SeatLayout {
       // default max
        val layout = repository.getSeatLayout(theatreId, showId)
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
            } else {
                seat
            }
        }

        return layout.copy(seats = updatedSeats)
    }
}