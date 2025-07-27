package com.notesapp.domain.usecase

import com.notesapp.domain.model.SeatLayout
import com.notesapp.domain.repository.SeatRepository
import javax.inject.Inject

class ToggleSeatSelectionUseCase @Inject constructor(private val repository: SeatRepository) {
    suspend operator fun invoke(theatreId: String, showId: String, seatId: String , maxSelection: Int = 6): SeatLayout {

        return repository.updateSeatSelection(theatreId, showId, seatId,maxSelection)
    }
}