package com.crazycoderz.zunokids.ui.games

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crazycoderz.zunokids.ui.shared.KidButton
import com.crazycoderz.zunokids.ui.shared.ConfettiAnimation
import com.crazycoderz.zunokids.ui.theme.*
import com.airbnb.lottie.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    onBack: () -> Unit,
    viewModel: QuizViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Quiz Time!", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = DeepNavy,
                    titleContentColor = SunshineYellow
                )
            )
        },
        containerColor = DeepNavy
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (uiState.isGameOver) {
                GameOverView(
                    score = uiState.score,
                    total = uiState.totalQuestions,
                    onRestart = { viewModel.restart() },
                    onBack = onBack
                )
            } else {
                uiState.currentQuestion?.let { question ->
                    QuizContent(
                        question = question,
                        questionNumber = uiState.questionNumber,
                        totalQuestions = uiState.totalQuestions,
                        onAnswer = { viewModel.onAnswerSelected(it) }
                    )
                }
            }
        }
    }
}

@Composable
fun QuizContent(
    question: QuizQuestion,
    questionNumber: Int,
    totalQuestions: Int,
    onAnswer: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Progress Indicator
        LinearProgressIndicator(
            progress = { questionNumber.toFloat() / totalQuestions },
            modifier = Modifier
                .fillMaxWidth()
                .height(12.dp)
                .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(6.dp)),
            color = SunshineYellow,
            trackColor = Color.Transparent,
        )
        
        Spacer(Modifier.height(32.dp))

        // Question Emoji/Image Placeholder
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(Color.White.copy(alpha = 0.1f), RoundedCornerShape(32.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = question.emoji ?: "❓",
                fontSize = if ((question.emoji?.length ?: 0) > 2) 40.sp else 80.sp,
                textAlign = TextAlign.Center
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = question.question,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.weight(1f))

        // Options Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(question.options) { option ->
                KidButton(
                    text = option,
                    color = SkyBlue,
                    onClick = { onAnswer(option) }
                )
            }
        }
        
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun GameOverView(
    score: Int,
    total: Int,
    onRestart: () -> Unit,
    onBack: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        ConfettiAnimation()
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Great Job! 🎉",
                style = MaterialTheme.typography.displayMedium,
                color = SunshineYellow,
                fontWeight = FontWeight.ExtraBold
            )
            
            Spacer(Modifier.height(16.dp))
            
            Text(
                text = "You got $score out of $total correct!",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            
            Spacer(Modifier.height(16.dp))
            
            Text(
                text = "You earned ${score * 10} stars! ⭐",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFFFFD700)
            )

            Spacer(Modifier.height(48.dp))

            KidButton(
                text = "Play Again",
                color = KidsGreen,
                onClick = onRestart
            )
            
            Spacer(Modifier.height(16.dp))
            
            TextButton(onClick = onBack) {
                Text("Go to Home", color = Color.White.copy(alpha = 0.7f), fontSize = 18.sp)
            }
        }
    }
}
