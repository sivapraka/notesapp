package com.notesapp.presentation.seatselection

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.notesapp.di.worker.SeatSyncWorker
import com.notesapp.domain.model.SeatLayout
import com.notesapp.domain.model.SeatStatus
import com.notesapp.domain.usecase.GetSeatLayoutUseCase
import com.notesapp.domain.usecase.ToggleSeatSelectionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SeatSelectionViewModel @Inject constructor(
    private val getSeatsUseCase: GetSeatLayoutUseCase,
    private val toggleSeatSelectionUseCase: ToggleSeatSelectionUseCase,
    private val appContext: Application
) : ViewModel() {

    private val _uiState = MutableStateFlow(SeatUiState())
    val uiState: StateFlow<SeatUiState> = _uiState.asStateFlow()
    val maxSeatSelection=5
    private var theatreId: String = ""
    private var showId: String = ""
    private var fullSeatLayout: SeatLayout? = null

    fun scheduleSeatSync(theaterId: String, showTimeId: String) {
        val workRequest = PeriodicWorkRequestBuilder<SeatSyncWorker>(
            15, TimeUnit.MINUTES
        ).setInputData(
            workDataOf(
                "theaterId" to theaterId,
                "showTimeId" to showTimeId
            )
        ).build()
        WorkManager.getInstance(appContext)
            .enqueueUniquePeriodicWork(
                "SeatSync_$theaterId$showTimeId",
                ExistingPeriodicWorkPolicy.UPDATE,
                workRequest
            )
    }

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
            fullSeatLayout = toggleSeatSelectionUseCase(theatreId, showId, seatId, maxSeatSelection)
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
