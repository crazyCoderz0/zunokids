package com.crazycoderz.zunokids.ui.games.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crazycoderz.zunokids.data.TtsManager
import com.crazycoderz.zunokids.data.UserPreferencesRepo
import com.crazycoderz.zunokids.ui.games.newgames.MissingLetterQuestion
import com.crazycoderz.zunokids.util.SoundManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MissingLetterViewModel @Inject constructor(
    private val prefsRepo: UserPreferencesRepo,
    private val soundManager: SoundManager,
    private val ttsManager: TtsManager
) : ViewModel() {
    val questions = listOf(
        MissingLetterQuestion("APPLE", listOf(1, 3), listOf('P', 'L', 'A', 'E', 'B', 'M')),
        MissingLetterQuestion("CAT", listOf(1), listOf('A', 'E', 'O', 'I', 'U')),
        MissingLetterQuestion("BIRD", listOf(1, 2), listOf('I', 'R', 'E', 'A', 'D', 'L')),
        MissingLetterQuestion("DOG", listOf(1), listOf('O', 'U', 'I', 'A', 'E')),
        MissingLetterQuestion("BALL", listOf(1, 2), listOf('A', 'L', 'O', 'I', 'B'))
    )

    var currentIndex by mutableIntStateOf(0)
    var score by mutableIntStateOf(0)
    var isGameOver by mutableStateOf(false)

    val userAnswers = mutableStateMapOf<Int, Char?>()

    init {
        loadQuestion()
    }

    private fun loadQuestion() {
        userAnswers.clear()
        questions[currentIndex].missingIndices.forEach { userAnswers[it] = null }
        ttsManager.speak("Fill in the missing letters for " + questions[currentIndex].word.replace("", " ").trim(), "en")
    }

    fun onLetterSelected(char: Char) {
        val currentQuestion = questions[currentIndex]
        val firstEmptyIndex = currentQuestion.missingIndices.firstOrNull { userAnswers[it] == null }
        
        if (firstEmptyIndex != null) {
            userAnswers[firstEmptyIndex] = char
            
            // Check if word is complete
            if (currentQuestion.missingIndices.all { userAnswers[it] != null }) {
                val isCorrect = currentQuestion.missingIndices.all { idx ->
                    userAnswers[idx] == currentQuestion.word[idx]
                }
                
                if (isCorrect) {
                    score += 20
                    soundManager.play(SoundManager.SoundType.SUCCESS)
                    ttsManager.speak("Great job! ${currentQuestion.word}", "en")
                    viewModelScope.launch {
                        prefsRepo.addStars(20)
                    }
                    
                    if (currentIndex < questions.size - 1) {
                        currentIndex++
                        loadQuestion()
                    } else {
                        isGameOver = true
                    }
                } else {
                    soundManager.play(SoundManager.SoundType.ERROR)
                    ttsManager.speak("Not quite, let's try again", "en")
                    // Clear only if wrong
                    currentQuestion.missingIndices.forEach { userAnswers[it] = null }
                }
            }
        }
    }

    fun restartGame() {
        currentIndex = 0
        score = 0
        isGameOver = false
        loadQuestion()
    }
}
