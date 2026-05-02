package com.crazycoderz.zunokids.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crazycoderz.zunokids.data.UserPreferencesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val selectedLanguage: String = "en",
    val completedModules: Set<String> = emptySet(),
    val stars: Int = 0,
    val streak: Int = 0
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val prefsRepo: UserPreferencesRepo
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = combine(
        prefsRepo.selectedLanguage,
        prefsRepo.completedModules,
        prefsRepo.userStats
    ) { lang, completed, stats ->
        HomeUiState(
            selectedLanguage = lang,
            completedModules = completed,
            stars = stats.stars,
            streak = stats.streakCount
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState()
    )

    fun setLanguage(code: String) {
        viewModelScope.launch { prefsRepo.setLanguage(code) }
    }
}
