package com.crazycoderz.zunokids.ui.games.newgames

import android.annotation.SuppressLint
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crazycoderz.zunokids.ui.games.GameOverView
import com.crazycoderz.zunokids.ui.games.viewmodel.MissingLetterViewModel
import com.crazycoderz.zunokids.ui.theme.*

data class MissingLetterQuestion(
    val word: String,
    val missingIndices: List<Int>,
    val options: List<Char>
)

@SuppressLint("UnusedBoxWithConstraintsScope")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MissingLetterGame(
    onBack: () -> Unit,
    viewModel: MissingLetterViewModel = hiltViewModel()
) {
    val currentIndex = viewModel.currentIndex
    val score = viewModel.score
    val isGameOver = viewModel.isGameOver
    val currentQuestion = if (currentIndex < viewModel.questions.size) viewModel.questions[currentIndex] else null
    val userAnswers = viewModel.userAnswers

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Fill the Blanks", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF2C3E50))
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color(0xFF2C3E50)
                )
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFE8F5E9), Color(0xFFC8E6C9))
                    )
                )
        ) {
            if (isGameOver) {
                GameOverView(
                    score = score / 20,
                    total = viewModel.questions.size,
                    onRestart = { viewModel.restartGame() },
                    onBack = onBack
                )
            } else if (currentQuestion != null) {
                BoxWithConstraints(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                ) {
                    val screenWidth = maxWidth
                    val screenHeight = maxHeight
                    val isLandscape = screenWidth > screenHeight
                    
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = if (isLandscape) Arrangement.Top else Arrangement.Center
                    ) {
                        // Score Badge
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = SunshineYellow,
                            modifier = Modifier.padding(bottom = 24.dp)
                        ) {
                            Text(
                                "Score: $score",
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = DeepNavy
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Word Display - Responsive
                        androidx.compose.foundation.layout.FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                        ) {
                            currentQuestion.word.forEachIndexed { index, char ->
                                if (index in currentQuestion.missingIndices) {
                                    BlankBox(userAnswers[index])
                                } else {
                                    LetterBox(char)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(if (isLandscape) 32.dp else 48.dp))

                        // Hint Section
                        Text(
                            "Pick the missing letter!",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32),
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(24.dp))

                        // Options Section
                        androidx.compose.foundation.layout.FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            currentQuestion.options.forEach { option ->
                                OptionButton(option) {
                                    viewModel.onLetterSelected(option)
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun LetterBox(char: Char) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.size(width = 56.dp, height = 64.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                char.toString().uppercase(),
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF2C3E50)
            )
        }
    }
}

@Composable
fun BlankBox(char: Char?) {
    val infiniteTransition = rememberInfiniteTransition(label = "blink")
    val borderColor by infiniteTransition.animateColor(
        initialValue = Color(0xFF42A5F5),
        targetValue = Color(0xFF1976D2),
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "borderColor"
    )

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (char == null) Color(0xFFE3F2FD) else Color(0xFFBBDEFB)
        ),
        border = androidx.compose.foundation.BorderStroke(3.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.size(width = 56.dp, height = 64.dp)
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                char?.toString()?.uppercase() ?: "?",
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = if (char == null) Color(0xFF42A5F5) else Color(0xFF0D47A1)
            )
        }
    }
}

@Composable
fun OptionButton(char: Char, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF42A5F5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .size(70.dp)
            .clickable { onClick() }
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                char.toString().uppercase(),
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
    }
}
