package com.notesapp.presentation.seatselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.domain.model.SeatLayout
import com.notesapp.domain.model.SeatStatus
import com.notesapp.domain.usecase.GetSeatLayoutUseCase
import com.notesapp.domain.usecase.ToggleSeatSelectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeatSelectionViewModel @Inject constructor(
    private val getSeatsUseCase: GetSeatLayoutUseCase,
    private val toggleSeatSelectionUseCase: ToggleSeatSelectionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeatUiState())
    val uiState: StateFlow<SeatUiState> = _uiState.asStateFlow()
    private val MAX_SELECTION_LIMIT = 6
    private var theatreId: String = ""
    private var showId: String = ""
    private var fullSeatLayout: SeatLayout? = null

    fun loadSeats(tId: String, sId: String) {
        theatreId = tId
        showId = sId
        viewModelScope.launch {
            fullSeatLayout = getSeatsUseCase(theatreId, showId)
            _uiState.value = buildUiState(fullSeatLayout!!)
        }
    }

    fun onSeatClick(seatId: String) {
        viewModelScope.launch {
            val seat = fullSeatLayout?.seats?.find { it.id == seatId } ?: return@launch
            val selectedSeatsCount = fullSeatLayout?.seats?.count { it.status == SeatStatus.SELECTED } ?: 0

            if (seat.status == SeatStatus.AVAILABLE && selectedSeatsCount >= MAX_SELECTION_LIMIT) {
                // Limit reached — maybe show a toast/snackbar via side effect or emit error
                return@launch
            }

            fullSeatLayout = toggleSeatSelectionUseCase(theatreId, showId, seatId)
            _uiState.value = buildUiState(fullSeatLayout!!)
        }
    }

    private fun buildUiState(layout: SeatLayout): SeatUiState {
        val selectedSeats = layout.seats.filter { it.status == SeatStatus.SELECTED }
        val pricePerSeat = 120.0 // You can customize this
        return SeatUiState(
            seats = layout.seats,
            selectedSeats = selectedSeats,
            totalPrice = selectedSeats.size * pricePerSeat
        )
    }
}
