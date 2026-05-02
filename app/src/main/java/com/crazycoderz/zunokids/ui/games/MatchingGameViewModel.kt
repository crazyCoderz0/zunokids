package com.crazycoderz.zunokids.ui.games

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crazycoderz.zunokids.data.*
import com.crazycoderz.zunokids.data.UserPreferencesRepo
import com.crazycoderz.zunokids.data.TtsManager
import com.crazycoderz.zunokids.util.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MatchingItem(
    val id: Int,
    val text: String,
    val emoji: String,
    var isMatched: Boolean = false,
    var isSelected: Boolean = false
)

data class MatchingUiState(
    val items: List<MatchingItem> = emptyList(),
    val selectedId: Int? = null,
    val matchesFound: Int = 0,
    val isGameOver: Boolean = false
)

@HiltViewModel
class MatchingGameViewModel @Inject constructor(
    private val prefsRepo: UserPreferencesRepo,
    private val soundManager: SoundManager,
    private val ttsManager: TtsManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatchingUiState())
    val uiState = _uiState.asStateFlow()
    
    private var currentLanguage = "en"

    init {
        viewModelScope.launch {
            prefsRepo.selectedLanguage.collect { currentLanguage = it }
        }
        startNewGame()
    }

    fun startNewGame() {
        // Pick 4 random fruits for the matching game
        val selectedFruits = FruitsContent.items.shuffled().take(4)
        val items = selectedFruits.mapIndexed { index, fruit ->
            MatchingItem(id = index, text = fruit.nameKey, emoji = fruit.emoji)
        }
        _uiState.value = MatchingUiState(items = items)
    }

    fun onEmojiSelected(id: Int) {
        val currentState = _uiState.value
        if (currentState.selectedId == id) {
             _uiState.value = currentState.copy(selectedId = null)
        } else {
             _uiState.value = currentState.copy(selectedId = id)
             soundManager.play(SoundManager.SoundType.CLICK)
             
             // Speak emoji name
             currentState.items.find { it.id == id }?.let { item ->
                 ttsManager.speak(item.text.replace("fruit_", ""), currentLanguage)
             }
        }
    }

    fun onTextSelected(id: Int) {
        val currentState = _uiState.value
        if (currentState.selectedId == id) {
            // Match found!
            val updatedItems = currentState.items.map {
                if (it.id == id) it.copy(isMatched = true) else it
            }
            val newMatches = currentState.matchesFound + 1
            val isOver = newMatches == currentState.items.size
            
            _uiState.value = currentState.copy(
                items = updatedItems,
                selectedId = null,
                matchesFound = newMatches,
                isGameOver = isOver
            )
            
            soundManager.play(SoundManager.SoundType.SUCCESS)
            if (isOver) {
                viewModelScope.launch { prefsRepo.addStars(50) }
            }
        } else {
            soundManager.play(SoundManager.SoundType.ERROR)
        }
    }
}
