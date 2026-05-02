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

data class QuizQuestion(
    val question: String,
    val correctAnswer: String,
    val options: List<String>,
    val emoji: String? = null
)

data class QuizUiState(
    val currentQuestion: QuizQuestion? = null,
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val totalQuestions: Int = 5,
    val questionNumber: Int = 0
)

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val prefsRepo: UserPreferencesRepo,
    private val soundManager: SoundManager,
    private val ttsManager: TtsManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState = _uiState.asStateFlow()
    
    private var currentLanguage = "en"

    init {
        viewModelScope.launch {
            prefsRepo.selectedLanguage.collect { currentLanguage = it }
        }
        generateNewQuestion()
    }

    fun generateNewQuestion() {
        if (_uiState.value.questionNumber >= _uiState.value.totalQuestions) {
            _uiState.value = _uiState.value.copy(isGameOver = true)
            return
        }

        // Randomly pick a category for the question
        val category = (0..2).random()
        val question = when (category) {
            0 -> generateAlphabetQuestion()
            1 -> generateNumberQuestion()
            else -> generateFruitQuestion()
        }

        _uiState.value = _uiState.value.copy(
            currentQuestion = question,
            questionNumber = _uiState.value.questionNumber + 1
        )
        
        speakQuestion(question)
    }

    private fun speakQuestion(question: QuizQuestion) {
        viewModelScope.launch {
            ttsManager.speak(question.question, currentLanguage)
        }
    }

    private fun generateAlphabetQuestion(): QuizQuestion {
        val correct = AlphabetContent.items.random()
        val others = AlphabetContent.items.filter { it.letter != correct.letter }.shuffled().take(3)
        val options = (others + correct).map { it.letter.toString() }.shuffled()
        return QuizQuestion(
            question = "Which letter is for ${correct.word}?",
            correctAnswer = correct.letter.toString(),
            options = options,
            emoji = correct.emoji
        )
    }

    private fun generateNumberQuestion(): QuizQuestion {
        val correct = NumbersContent.items.random()
        val others = NumbersContent.items.filter { it.number != correct.number }.shuffled().take(3)
        val options = (others + correct).map { it.number.toString() }.shuffled()
        return QuizQuestion(
            question = "How many ${correct.emoji} do you see?",
            correctAnswer = correct.number.toString(),
            options = options,
            emoji = List(correct.number) { correct.emoji }.joinToString(" ")
        )
    }

    private fun generateFruitQuestion(): QuizQuestion {
        val correct = FruitsContent.items.random()
        val others = FruitsContent.items.filter { it.emoji != correct.emoji }.shuffled().take(3)
        val options = (others + correct).map { it.emoji }.shuffled()
        return QuizQuestion(
            question = "Which one is the fruit?",
            correctAnswer = correct.emoji,
            options = options
        )
    }

    fun onAnswerSelected(answer: String) {
        val isCorrect = answer == _uiState.value.currentQuestion?.correctAnswer
        if (isCorrect) {
            soundManager.play(SoundManager.SoundType.SUCCESS)
            _uiState.value = _uiState.value.copy(score = _uiState.value.score + 1)
            viewModelScope.launch {
                prefsRepo.addStars(10)
            }
        } else {
            soundManager.play(SoundManager.SoundType.ERROR)
        }
        generateNewQuestion()
    }

    fun restart() {
        _uiState.value = QuizUiState()
        generateNewQuestion()
    }
}
