package com.crazycoderz.zunokids.ui.games.newgames

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crazycoderz.zunokids.ui.games.GameOverView
import com.crazycoderz.zunokids.ui.games.viewmodel.CategoryViewModel
import com.crazycoderz.zunokids.ui.shared.ConfettiAnimation

data class CategoryQuestion(
    val itemEmoji: String,
    val itemName: String,
    val options: List<String>,
    val correctCategory: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryGameScreen(
    onBack: () -> Unit,
    viewModel: CategoryViewModel = hiltViewModel()
) {
    val questions = viewModel.questions
    val currentIndex = viewModel.currentIndex
    val score = viewModel.score
    val isGameOver = viewModel.isGameOver
    
    val currentQuestion = if (!isGameOver) questions[currentIndex] else questions.last()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("What group is this?", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (isGameOver) {
                GameOverView(
                    score = score / 10,
                    total = questions.size,
                    onRestart = { viewModel.restartGame() },
                    onBack = onBack
                )
            } else {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color(0xFFFFFDE7), Color(0xFFFFF9C4))
                            )
                        )
                ) {
                    val isTablet = maxWidth > 600.dp
                    
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Score: $score", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        Card(
                            shape = RoundedCornerShape(32.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            modifier = Modifier.size(if (isTablet) 300.dp else 200.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(currentQuestion.itemEmoji, fontSize = if (isTablet) 120.sp else 80.sp)
                                    Text(currentQuestion.itemName, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(48.dp))
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            currentQuestion.options.forEach { option ->
                                CategoryButton(
                                    text = option,
                                    isTablet = isTablet,
                                    onClick = { viewModel.onOptionSelected(option) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryButton(text: String, isTablet: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF7043)),
        modifier = Modifier
            .height(if (isTablet) 100.dp else 80.dp)
            .width(if (isTablet) 250.dp else 160.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
    ) {
        Text(text, fontSize = if (isTablet) 28.sp else 20.sp, fontWeight = FontWeight.ExtraBold)
    }
}
