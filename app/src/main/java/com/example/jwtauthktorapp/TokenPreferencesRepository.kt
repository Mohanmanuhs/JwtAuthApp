package com.example.jwtauthktorapp


interface TokenPreferencesRepository{
    suspend fun saveToken(token: String)
    suspend fun getTokenValue(): String
}
