package com.notesapp.domain.repository

import com.notesapp.domain.model.SeatLayout

interface SeatRepository {
    suspend fun getSeatLayout(theatreId: String, showId: String): SeatLayout
    suspend fun updateSeatSelection(theatreId: String, showId: String, seatId: String): SeatLayout
}