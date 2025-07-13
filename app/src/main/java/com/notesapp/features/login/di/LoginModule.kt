package com.notesapp.features.login.di


import android.content.Context
import com.notesapp.features.login.data.local.EncryptedPrefs
import com.notesapp.features.login.data.remote.AuthService
import com.notesapp.features.login.data.repository.LoginRepositoryImpl
import com.notesapp.features.login.domain.repository.LoginRepository
import com.notesapp.features.login.domain.usecase.LoginUseCase
import com.notesapp.features.login.domain.usecase.LogoutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    // 🔌 Retrofit setup for AuthService
    @Provides
    @Singleton
    fun provideAuthService(): AuthService {
        return Retrofit.Builder()
            .baseUrl("https://your-api-url.com/") // Replace with your base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthService::class.java)
    }

    // 🔐 EncryptedPrefs
    @Provides
    @Singleton
    fun provideEncryptedPrefs( @ApplicationContext context: Context): EncryptedPrefs {
        return EncryptedPrefs(context)
    }

    // 🏗️ LoginRepositoryImpl -> LoginRepository
    @Provides
    @Singleton
    fun provideLoginRepository(
        authService: AuthService,
        encryptedPrefs: EncryptedPrefs
    ): LoginRepository {
        return LoginRepositoryImpl(authService, encryptedPrefs)
    }

    // 📦 LoginUseCase
    @Provides
    fun provideLoginUseCase(
        repository: LoginRepository
    ): LoginUseCase {
        return LoginUseCase(repository)
    }

    // 📦 LogoutUseCase
    @Provides
    fun provideLogoutUseCase(
        repository: LoginRepository
    ): LogoutUseCase {
        return LogoutUseCase(repository)
    }
}
