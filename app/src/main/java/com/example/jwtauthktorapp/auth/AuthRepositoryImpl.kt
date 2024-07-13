package com.example.jwtauthktorapp.auth

import com.example.jwtauthktorapp.TokenPreferencesRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class AuthRepositoryImpl(
    private val httpClient: HttpClient, private val pref: TokenPreferencesRepository
) : AuthRepository {
    override suspend fun signUp(username: String, password: String): AuthResult<Unit> {
        return try {
            val response = httpClient.post("/signup"){
                contentType(ContentType.Application.Json)
                setBody(AuthRequest(
                    username = username, password = password
                ))
            }
            if(response.status!=HttpStatusCode.Conflict){
                signIn(username, password)
            }else{
                AuthResult.UnKnownError()
            }
        } catch(e:ClientRequestException) {
            when (e.response.status) {
                HttpStatusCode.Unauthorized -> {
                    AuthResult.Unauthorized()
                }
                HttpStatusCode.Conflict -> {
                    AuthResult.UnKnownError()
                }
                else -> {
                    AuthResult.UnKnownError()
                }
            }
        } catch (e: Exception) {
            AuthResult.UnKnownError()
        }

    }

    override suspend fun signIn(username: String, password: String): AuthResult<Unit> {
        return try {
            val response = httpClient.post("/signIn"){
                contentType(ContentType.Application.Json)
                setBody(AuthRequest(
                    username = username, password = password
                ))
            }.body<TokenResponse>()
            pref.saveToken(token = response.token)

            AuthResult.Authorized()
        } catch(e:ClientRequestException) {
            when (e.response.status) {
                HttpStatusCode.Unauthorized -> {
                    AuthResult.Unauthorized()
                }
                HttpStatusCode.Conflict -> {
                    AuthResult.UnKnownError()
                }
                else -> {
                    AuthResult.UnKnownError()
                }
            }
        } catch (e: Exception) {
            AuthResult.UnKnownError()
        }
    }

    override suspend fun authenticate(): AuthResult<Unit> {
        return try {
            val token = pref.getTokenValue()

            if(token == ""){
                return AuthResult.Unauthorized()
            }
            httpClient.get("/authenticate") {
                header("Authorization", token)
            }
            AuthResult.Authorized()
        }catch(e:ClientRequestException) {
            when (e.response.status) {
                HttpStatusCode.Unauthorized -> {
                    AuthResult.Unauthorized()
                }
                HttpStatusCode.Conflict -> {
                    AuthResult.UnKnownError()
                }
                else -> {
                    AuthResult.UnKnownError()
                }
            }
        } catch (e: Exception) {
            AuthResult.UnKnownError()
        }
    }
}