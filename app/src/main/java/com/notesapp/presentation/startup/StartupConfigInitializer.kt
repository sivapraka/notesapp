package com.notesapp.presentation.startup

import com.notesapp.domain.usecase.GetImdbImageConfigUseCase
import com.notesapp.util.ApiResource
import com.notesapp.util.ImageUrlProviders
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class StartupConfigInitializer @Inject constructor(
    private val getAppConfigUseCase: GetImdbImageConfigUseCase
) {
    suspend fun init() {
        try {
            getAppConfigUseCase().collect { data ->
                when (val st = data) {
                    is ApiResource.Loading -> {}
                    is ApiResource.Error -> {}
                    is ApiResource.Success -> {
                        val imageConfig = st.data
                        ImageUrlProviders.basePosterUrl =
                            "${imageConfig?.secureBaseUrl}${imageConfig?.posterSizes?.first()}"
                        ImageUrlProviders.backdropUrl =
                            "${imageConfig?.secureBaseUrl}${imageConfig?.backdropSizes?.first()}"
                        Timber.log(0, "BackdropUrl " + ImageUrlProviders.backdropUrl)
                        Timber.log(0, "basePosterUrl " + ImageUrlProviders.basePosterUrl)
                    }
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
    }
}

