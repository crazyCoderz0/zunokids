package com.crazycoderz.zunokids.ui.alphabet

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crazycoderz.zunokids.data.AlphabetContent
import com.crazycoderz.zunokids.data.TtsManager
import com.crazycoderz.zunokids.data.UserPreferencesRepo
import com.crazycoderz.zunokids.ui.shared.*
import com.crazycoderz.zunokids.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlphabetViewModel @Inject constructor(
    private val prefsRepo: UserPreferencesRepo
) : ViewModel() {
    val items = AlphabetContent.items
    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()
    val selectedLanguage: StateFlow<String> = prefsRepo.selectedLanguage
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "en")

    fun next() {
        val next = (_currentIndex.value + 1).coerceAtMost(items.lastIndex)
        _currentIndex.value = next
        if (next == items.lastIndex) viewModelScope.launch { prefsRepo.markCompleted("alphabet") }
    }
    fun prev() { _currentIndex.value = (_currentIndex.value - 1).coerceAtLeast(0) }
}

@Composable
fun AlphabetScreen(
    onBack: () -> Unit,
    viewModel: AlphabetViewModel = hiltViewModel()
) {
    val currentIndex by viewModel.currentIndex.collectAsState()
    val language by viewModel.selectedLanguage.collectAsState()
    val item = viewModel.items[currentIndex]

    val context = LocalContext.current
    val tts = remember { TtsManager(context) }
    DisposableEffect(Unit) { onDispose { tts.shutdown() } }

    // Auto-advance
    var timerProgress by remember { mutableFloatStateOf(1f) }
    LaunchedEffect(currentIndex) {
        timerProgress = 1f
        repeat(60) { delay(100); timerProgress -= 1f / 60f }
        if (currentIndex < viewModel.items.lastIndex) viewModel.next()
    }

    // Card appears with spring
    var cardVisible by remember(currentIndex) { mutableStateOf(false) }
    LaunchedEffect(currentIndex) {
        cardVisible = false
        delay(50)
        cardVisible = true
    }

    ModuleScaffold(
        title = "Alphabet",
        emoji = "🔤",
        currentIndex = currentIndex,
        total = viewModel.items.size,
        onBack = onBack,
        onPrev = viewModel::prev,
        onNext = viewModel::next,
        onSpeak = { tts.speak("${item.letter} is for ${item.word}", language) },
        accentColor = item.color
    ) {
        AnimatedVisibility(
            visible = cardVisible,
            enter = slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium)
            ) + fadeIn(),
            exit = fadeOut()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(item.color.copy(alpha = 0.4f), item.color.copy(alpha = 0.15f))
                        ),
                        RoundedCornerShape(36.dp)
                    )
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Giant letter
                    Text(
                        text = item.letter.toString(),
                        fontSize = 140.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = item.color,
                        lineHeight = 150.sp
                    )

                    // Lowercase
                    Text(
                        text = item.letter.lowercaseChar().toString(),
                        fontSize = 80.sp,
                        fontWeight = FontWeight.Bold,
                        color = item.color.copy(alpha = 0.6f),
                        lineHeight = 90.sp
                    )

                    Spacer(Modifier.height(16.dp))

                    // Example word
                    Text(
                        text = "${item.letter} is for ${item.word}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(16.dp))

                    // Emoji
                    Text(text = item.emoji, fontSize = 72.sp)
                }
            }
        }

        // Countdown ring overlay
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Spacer(Modifier.height(8.dp))
            CountdownRing(progressFraction = timerProgress, color = item.color, size = 52.dp)
        }
    }
}
