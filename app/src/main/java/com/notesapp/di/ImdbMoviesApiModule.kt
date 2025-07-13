package com.notesapp.di

import android.annotation.SuppressLint
import com.notesapp.BuildConfig
import com.notesapp.data.remote.ImdbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
@InstallIn(SingletonComponent::class)
object ImdbMoviesApiModule {

    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val Token = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMWYyNGUzZGMzNDQ0YjM4ZTUzNzk5OWY3NzFiNTViNiIsIm5iZiI6MTYwODE5NjU4Ny4zNzYsInN1YiI6IjVmZGIyMWViZWRhNGI3MDAzZTQwNWU3OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Zqy4ZYdGOvT26tomFRkMKTAqEQfBmx_DU0hJBkLzDh8"
    private const val API_KEY = "21f24e3dc3444b38e537999f771b55b6" // Replace this with your real key

    @Provides
    @Singleton
    fun provideApiKey(): String = BuildConfig.TMDB_API_KEY
    @Provides
    @Singleton
    @AuthClient
    fun OkHttpClient(apiKey: String): OkHttpClient {
        // Trust all certificates
        val trustAllCerts = arrayOf<TrustManager>(
            object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            }
        )

        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())
        val sslSocketFactory = sslContext.socketFactory

        // Logging interceptor
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val apiKeyInterceptor = Interceptor { chain ->
            val original = chain.request()
            val originalUrl = original.url

            val newUrl = originalUrl.newBuilder()
                .addQueryParameter("api_key", apiKey)
                .build()

            val newRequest = original.newBuilder()
                .url(newUrl)
                .build()

            chain.proceed(newRequest)
        }


        val headerInterceptor = Interceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("Authorization", "Bearer $Token")
                .header("Accept", "application/json")
                .method(original.method, original.body)
                .build()
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            .hostnameVerifier(object : HostnameVerifier {
                @SuppressLint("BadHostnameVerifier")
                override fun verify(hostname: String?, session: SSLSession?): Boolean {
                    return true
                }
            })
            .addInterceptor(headerInterceptor)
           // .addInterceptor(apiKeyInterceptor)
            .addInterceptor(logging)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    @AuthClient
    fun provideRetrofit(@AuthClient client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideImdbApi(@AuthClient retrofit: Retrofit): ImdbApi {
        return retrofit.create(ImdbApi::class.java)
    }
}