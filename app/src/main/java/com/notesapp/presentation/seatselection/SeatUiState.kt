package com.notesapp.presentation.seatselection

import com.notesapp.domain.model.Seat


data class SeatUiState(
    val seats: List<Seat> = emptyList(),
    val selectedSeats: List<Seat> = emptyList(),
    val totalPrice: Double = 0.0
)