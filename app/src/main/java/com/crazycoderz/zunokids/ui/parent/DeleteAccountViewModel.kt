package com.crazycoderz.zunokids.ui.parent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crazycoderz.zunokids.data.UserPreferencesRepo
import com.crazycoderz.zunokids.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DeleteAccountState {
    object Idle : DeleteAccountState()
    object Loading : DeleteAccountState()
    object Success : DeleteAccountState()
    data class Error(val message: String) : DeleteAccountState()
}

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userPreferencesRepo: UserPreferencesRepo
) : ViewModel() {

    private val _uiState = MutableStateFlow<DeleteAccountState>(DeleteAccountState.Idle)
    val uiState = _uiState.asStateFlow()

    fun deleteAccount(password: String) {
        if (password.isBlank()) {
            _uiState.value = DeleteAccountState.Error("Please enter your password")
            return
        }

        viewModelScope.launch {
            _uiState.value = DeleteAccountState.Loading
            
            // 1. Re-authenticate
            authRepository.reauthenticate(password).onSuccess {
                // 2. Delete account (Firestore + Auth)
                authRepository.deleteAccount().onSuccess {
                    // 3. Clear local data
                    userPreferencesRepo.clearAllData()
                    _uiState.value = DeleteAccountState.Success
                }.onFailure {
                    _uiState.value = DeleteAccountState.Error(it.message ?: "Failed to delete account")
                }
            }.onFailure {
                _uiState.value = DeleteAccountState.Error("Invalid credentials. Please check your password.")
            }
        }
    }
    
    fun resetState() {
        _uiState.value = DeleteAccountState.Idle
    }
}
