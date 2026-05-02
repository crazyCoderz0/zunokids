package com.crazycoderz.zunokids.ui.parent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crazycoderz.zunokids.data.UserPreferencesRepo
import com.crazycoderz.zunokids.data.UserStats
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ParentUiState(
    val stats: UserStats = UserStats(0, 0, "", 0),
    val completedModules: Set<String> = emptySet(),
    val isAuthorized: Boolean = false,
    val parentPin: String = "1234"
)

@HiltViewModel
class ParentViewModel @Inject constructor(
    private val prefsRepo: UserPreferencesRepo
) : ViewModel() {

    val uiState: StateFlow<ParentUiState> = combine(
        prefsRepo.userStats,
        prefsRepo.completedModules,
        prefsRepo.parentPin
    ) { stats, completed, pin ->
        ParentUiState(stats = stats, completedModules = completed, parentPin = pin)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ParentUiState()
    )

    private val _isAuthorized = MutableStateFlow(false)
    val isAuthorized = _isAuthorized.asStateFlow()

    fun authorize(pin: String) {
        if (pin == uiState.value.parentPin) {
            _isAuthorized.value = true
        }
    }

    fun resetStats() {
        // Logic to reset stats if needed
    }
}
