package com.example.jwtauthktorapp.ui

sealed class AuthUiEvent {
    data class SignUpUsernameChanged(val value: String): AuthUiEvent()
    data class SignUpPasswordChanged(val value: String): AuthUiEvent()
    data object SignUp: AuthUiEvent()

    data class SignInUsernameChanged(val value: String): AuthUiEvent()
    data class SignInPasswordChanged(val value: String): AuthUiEvent()
    data object SignIn: AuthUiEvent()
}