package com.crazycoderz.zunokids.ui.auth

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crazycoderz.zunokids.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Authenticated : AuthState()
    data class Error(val message: String) : AuthState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AuthState>(AuthState.Idle)
    val uiState = _uiState.asStateFlow()

    fun login(email: String, pass: String) {
        val trimmedEmail = email.trim()
        val trimmedPass = pass.trim()
        if (!validateInputs(trimmedEmail, trimmedPass)) return

        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            authRepository.login(trimmedEmail, trimmedPass)
                .onSuccess { 
                    _uiState.value = AuthState.Authenticated 
                }
                .onFailure { 
                    _uiState.value = AuthState.Error(it.message ?: "Login failed") 
                }
        }
    }

    fun signUp(email: String, pass: String, confirmPass: String) {
        val trimmedEmail = email.trim()
        val trimmedPass = pass.trim()
        if (!validateInputs(trimmedEmail, trimmedPass)) return
        
        if (trimmedPass != confirmPass.trim()) {
            _uiState.value = AuthState.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {
            _uiState.value = AuthState.Loading
            authRepository.signUp(trimmedEmail, trimmedPass)
                .onSuccess { 
                    _uiState.value = AuthState.Authenticated 
                }
                .onFailure { 
                    _uiState.value = AuthState.Error(it.message ?: "Signup failed") 
                }
        }
    }

    private fun validateInputs(email: String, pass: String): Boolean {
        if (email.isBlank()) {
            _uiState.value = AuthState.Error("Email cannot be empty")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _uiState.value = AuthState.Error("Please enter a valid email address")
            return false
        }
        if (pass.isBlank()) {
            _uiState.value = AuthState.Error("Password cannot be empty")
            return false
        }
        if (pass.length < 6) {
            _uiState.value = AuthState.Error("Password must be at least 6 characters")
            return false
        }
        return true
    }
    
    fun resetState() {
        _uiState.value = AuthState.Idle
    }
}
