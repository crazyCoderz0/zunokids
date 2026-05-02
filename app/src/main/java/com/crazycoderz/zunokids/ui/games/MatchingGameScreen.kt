package com.crazycoderz.zunokids.ui.games

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crazycoderz.zunokids.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchingGameScreen(
    onBack: () -> Unit,
    viewModel: MatchingGameViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Match Them!", fontWeight = FontWeight.ExtraBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back", tint = Color(0xFF2C3E50))
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
                        colors = listOf(Color(0xFFE0F7FA), Color(0xFFFFF9C4))
                    )
                )
        ) {
            BoxWithConstraints(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                val isLandscape = maxWidth > maxHeight
                val columns = if (isLandscape) 4 else 2
                
                if (uiState.isGameOver) {
                    GameOverView(
                        score = 5,
                        total = 5,
                        onRestart = { viewModel.startNewGame() },
                        onBack = onBack
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Tap an emoji, then its name!",
                            color = Color(0xFF2C3E50).copy(alpha = 0.8f),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 12.dp),
                            textAlign = TextAlign.Center
                        )

                        val shuffledNames = remember(uiState.items) { uiState.items.shuffled() }

                        LazyVerticalGrid(
                            columns = GridCells.Fixed(columns),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(bottom = 24.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            // Emojis Section
                            items(uiState.items) { item ->
                                MatchingCard(
                                    text = item.emoji,
                                    isSelected = uiState.selectedId == item.id,
                                    isMatched = item.isMatched,
                                    onClick = { if (!item.isMatched) viewModel.onEmojiSelected(item.id) }
                                )
                            }

                            // Names Section
                            items(shuffledNames) { item ->
                                val matchedItem = uiState.items.find { it.id == item.id }
                                MatchingCard(
                                    text = item.text.replace("fruit_", "").replaceFirstChar { it.uppercase() },
                                    isSelected = false,
                                    isMatched = matchedItem?.isMatched ?: false,
                                    onClick = { if (matchedItem?.isMatched == false) viewModel.onTextSelected(item.id) },
                                    color = KidsYellow
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
fun MatchingCard(
    text: String,
    isSelected: Boolean,
    isMatched: Boolean,
    onClick: () -> Unit,
    color: Color = SkyBlue
) {
    val bgColor by animateColorAsState(
        targetValue = when {
            isMatched -> KidsGreen.copy(alpha = 0.4f)
            isSelected -> SunshineYellow
            else -> color.copy(alpha = 0.2f)
        },
        label = "bg_color"
    )

    val contentColor by animateColorAsState(
        targetValue = when {
            isMatched -> Color(0xFF1B5E20)
            isSelected -> DeepNavy
            else -> Color(0xFF2C3E50)
        },
        label = "content_color"
    )

    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 8.dp else 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 80.dp, max = 120.dp)
            .aspectRatio(if (text.length > 2) 1.5f else 1f)
            .clickable(enabled = !isMatched) { onClick() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (isMatched) {
                Text("✅", fontSize = 40.sp)
            } else {
                Text(
                    text = text,
                    fontSize = if (text.length > 2) 18.sp else 40.sp,
                    color = contentColor,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
