package com.example.jwtauthktorapp.auth

import kotlinx.serialization.Serializable

@Serializable
data class TokenResponse(
    val token: String
)