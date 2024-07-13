package com.example.jwtauthktorapp.di

import android.content.Context
import com.example.jwtauthktorapp.TokenPreferencesRepository
import com.example.jwtauthktorapp.TokenPreferencesRepositoryImpl
import com.example.jwtauthktorapp.auth.AuthRepository
import com.example.jwtauthktorapp.auth.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            defaultRequest {
                url("http://192.168.133.198:8081")
            }
            install(Logging) {
                logger = Logger.SIMPLE

            }
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                        isLenient=true
                    }
                )
            }
        }
    }

    @Provides
    @Singleton
    fun provideAuthRepo(httpClient: HttpClient, pref: TokenPreferencesRepository): AuthRepository {
        return AuthRepositoryImpl(httpClient, pref)
    }

    @Provides
    @Singleton
    fun provideTokenPref(@ApplicationContext context: Context): TokenPreferencesRepository {
        return TokenPreferencesRepositoryImpl(context)
    }
}
