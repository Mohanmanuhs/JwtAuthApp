package com.example.jwtauthktorapp.auth

interface AuthRepository {
    suspend fun signUp(username: String, password: String):AuthResult<Unit>
    suspend fun signIn(username: String, password: String):AuthResult<Unit>
    suspend fun authenticate():AuthResult<Unit>


}