package com.notesapp.di.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.notesapp.domain.usecase.GetSeatLayoutUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class SeatSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val getSeatLayoutUseCase: GetSeatLayoutUseCase
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val theaterId = inputData.getString("theaterId") ?: return Result.failure()
            val showTimeId = inputData.getString("showTimeId") ?: return Result.failure()

            val seatLayout = getSeatLayoutUseCase(theaterId, showTimeId)
            // Save to Room cache or update repository
            // seatDao.insert(seatLayout.toEntity())
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
