package com.example.jwtauthktorapp.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jwtauthktorapp.auth.AuthRepository
import com.example.jwtauthktorapp.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var state by mutableStateOf(AuthState())

    // for storing ui events use channel instead of flow / live data because it wont trigger again when configuration changes
    private val resultChannel = Channel<AuthResult<Unit>> { }
    val authResults = resultChannel.receiveAsFlow()

    init {
        authenticate()
    }
    fun onEvent(event: AuthUiEvent) {
        when (event) {
            AuthUiEvent.SignIn -> {
                signIn()
            }

            is AuthUiEvent.SignInPasswordChanged -> {
                state = state.copy(signInPassword = event.value)
            }

            is AuthUiEvent.SignInUsernameChanged -> {
                state = state.copy(signInUsername = event.value)
            }

            AuthUiEvent.SignUp -> {
                signUp()
            }

            is AuthUiEvent.SignUpPasswordChanged -> {
                state = state.copy(signUpPassword = event.value)
            }

            is AuthUiEvent.SignUpUsernameChanged -> {
                state = state.copy(signUpUsername = event.value)
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = authRepository.signUp(state.signUpUsername, state.signUpPassword)
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun signIn() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = authRepository.signIn(state.signInUsername, state.signInPassword)
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }

    private fun authenticate() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val result = authRepository.authenticate()
            resultChannel.send(result)
            state = state.copy(isLoading = false)
        }
    }
}