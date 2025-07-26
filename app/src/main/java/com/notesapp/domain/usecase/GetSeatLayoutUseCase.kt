package com.notesapp.domain.usecase

import com.notesapp.domain.model.SeatLayout
import com.notesapp.domain.repository.SeatRepository
import javax.inject.Inject

class GetSeatLayoutUseCase @Inject constructor(private val seatRepository: SeatRepository)
{
    suspend operator fun invoke(theaterId: String, showTimeId: String): SeatLayout {
        return seatRepository.getSeatLayout(theaterId, showTimeId)
    }
}