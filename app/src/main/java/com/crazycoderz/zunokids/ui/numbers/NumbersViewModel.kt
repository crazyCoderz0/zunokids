package com.crazycoderz.zunokids.ui.numbers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crazycoderz.zunokids.data.NumbersContent
import com.crazycoderz.zunokids.data.UserPreferencesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NumbersViewModel @Inject constructor(
    private val prefsRepo: UserPreferencesRepo
) : ViewModel() {

    val items = NumbersContent.items

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    val selectedLanguage: StateFlow<String> = prefsRepo.selectedLanguage
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "en")

    fun next() {
        val next = (_currentIndex.value + 1).coerceAtMost(items.lastIndex)
        _currentIndex.value = next
        if (next == items.lastIndex) {
            viewModelScope.launch { prefsRepo.markCompleted("numbers") }
        }
    }

    fun prev() {
        _currentIndex.value = (_currentIndex.value - 1).coerceAtLeast(0)
    }

    fun goTo(index: Int) {
        _currentIndex.value = index.coerceIn(0, items.lastIndex)
    }
}
