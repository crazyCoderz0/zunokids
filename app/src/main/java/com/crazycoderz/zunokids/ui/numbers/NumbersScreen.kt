package com.crazycoderz.zunokids.ui.numbers

import android.content.Context
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crazycoderz.zunokids.data.TtsManager
import com.crazycoderz.zunokids.ui.shared.*
import com.crazycoderz.zunokids.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun NumbersScreen(
    onBack: () -> Unit,
    viewModel: NumbersViewModel = hiltViewModel()
) {
    val currentIndex by viewModel.currentIndex.collectAsState()
    val language by viewModel.selectedLanguage.collectAsState()
    val item = viewModel.items[currentIndex]

    // Use shared Hilt-provided TtsManager via ViewModel or direct injection if possible
    // For now, continuing with direct instantiation but noting the plan.
    val context = LocalContext.current
    val tts = remember { TtsManager(context) }

    DisposableEffect(Unit) {
        onDispose { tts.shutdown() }
    }

    // Auto-advance timer
    var timerProgress by remember { mutableFloatStateOf(1f) }
    LaunchedEffect(currentIndex) {
        timerProgress = 1f
        repeat(50) {
            delay(100)
            timerProgress -= 0.02f
        }
        if (currentIndex < viewModel.items.lastIndex) viewModel.next()
    }

    // Animated number display
    val animatedNumber by animateIntAsState(
        targetValue = item.number,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "number_anim"
    )

    ModuleScaffold(
        title = "Numbers",
        emoji = "🔢",
        currentIndex = currentIndex,
        total = viewModel.items.size,
        onBack = onBack,
        onPrev = viewModel::prev,
        onNext = viewModel::next,
        onSpeak = { tts.speak(item.word, language) },
        accentColor = item.color
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            // Main number card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            listOf(item.color.copy(alpha = 0.3f), item.color.copy(alpha = 0.1f))
                        ),
                        RoundedCornerShape(32.dp)
                    )
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Giant animated number
                    Text(
                        text = animatedNumber.toString(),
                        fontSize = 120.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = item.color,
                        lineHeight = 130.sp
                    )

                    // Word
                    Text(
                        text = item.word.uppercase(),
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 4.sp
                    )

                    Spacer(Modifier.height(24.dp))

                    // Emoji objects (repeating N times)
                    val emojis = (1..item.number).joinToString(" ") { item.emoji }
                    Text(
                        text = emojis,
                        fontSize = 36.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 48.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Countdown ring
            CountdownRing(
                progressFraction = timerProgress,
                color = item.color,
                size = 52.dp,
                strokeWidth = 6.dp
            )
        }
    }
}
