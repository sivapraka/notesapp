package com.notesapp.presentation.startup

import android.util.Log
import com.notesapp.domain.usecase.GetImdbImageConfigUseCase
import com.notesapp.util.ImageUrlProviders
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StartupConfigInitializer @Inject constructor(
    private val getAppConfigUseCase: GetImdbImageConfigUseCase
) {
    suspend fun init() {
        try {
            getAppConfigUseCase().collect { imageConfig ->
                ImageUrlProviders.basePosterUrl =
                    "${imageConfig.secureBaseUrl}${imageConfig.posterSizes.first()}"

                ImageUrlProviders.backdropUrl =
                    "${imageConfig.secureBaseUrl}${imageConfig.backdropSizes.first()}"
                Log.e("TAG", "init: $imageConfig")
                Log.d("Startup", "TMDB BackdropUrl " + ImageUrlProviders.backdropUrl)
            }
        } catch (e: Exception) {
            Log.e("Startup", "Failed to fetch TMDB config: ${e.message}")
        }
    }
}

