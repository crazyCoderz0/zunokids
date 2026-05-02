package com.crazycoderz.zunokids.ui.games.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crazycoderz.zunokids.data.TtsManager
import com.crazycoderz.zunokids.data.UserPreferencesRepo
import com.crazycoderz.zunokids.ui.games.newgames.CategoryQuestion
import com.crazycoderz.zunokids.util.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val prefsRepo: UserPreferencesRepo,
    private val soundManager: SoundManager,
    private val ttsManager: TtsManager
) : ViewModel() {
    val questions = listOf(
        CategoryQuestion("🍆", "Eggplant", listOf("Fruits", "Vegetables"), "Vegetables"),
        CategoryQuestion("🦁", "Lion", listOf("Animals", "Plants"), "Animals"),
        CategoryQuestion("🍎", "Apple", listOf("Fruits", "Furniture"), "Fruits"),
        CategoryQuestion("✈️", "Airplane", listOf("Animals", "Vehicles"), "Vehicles"),
        CategoryQuestion("🥦", "Broccoli", listOf("Fruits", "Vegetables"), "Vegetables"),
        CategoryQuestion("🐘", "Elephant", listOf("Animals", "Plants"), "Animals")
    )

    var currentIndex by mutableIntStateOf(0)
    var score by mutableIntStateOf(0)
    var isGameOver by mutableStateOf(false)

    fun onOptionSelected(option: String) {
        val currentQuestion = questions[currentIndex]
        if (option == currentQuestion.correctCategory) {
            score += 10
            soundManager.play(SoundManager.SoundType.SUCCESS)
            ttsManager.speak("Correct! That's a ${currentQuestion.itemName}", "en")
            viewModelScope.launch {
                prefsRepo.addStars(10)
            }
        } else {
            soundManager.play(SoundManager.SoundType.ERROR)
            ttsManager.speak("Oops! Try again.", "en")
        }

        if (currentIndex < questions.size - 1) {
            currentIndex++
        } else {
            isGameOver = true
        }
    }

    fun restartGame() {
        currentIndex = 0
        score = 0
        isGameOver = false
    }
}
