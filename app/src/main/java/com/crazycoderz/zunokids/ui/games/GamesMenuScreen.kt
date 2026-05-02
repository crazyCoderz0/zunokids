package com.crazycoderz.zunokids.ui.games

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crazycoderz.zunokids.navigation.Screen

data class GameMenuItem(
    val title: String,
    val emoji: String,
    val route: String,
    val color: Color
)

val gameItems = listOf(
    GameMenuItem("Quiz Time", "❓", Screen.Quiz.route, Color(0xFF6A11CB)),
    GameMenuItem("Match Cards", "🧩", Screen.MatchCards.route, Color(0xFFF093FB)),
    GameMenuItem("Drag Match", "🎯", Screen.DragMatch.route, Color(0xFFFF6B6B)),
    GameMenuItem("Shape Match", "shape", Screen.ShapeMatch.route, Color(0xFF4ECDC4)),
    GameMenuItem("Category", "📂", Screen.CategoryGame.route, Color(0xFFF9D423)),
    GameMenuItem("Missing Letter", "📝", Screen.MissingLetter.route, Color(0xFFA29BFE))
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesMenuScreen(
    onBack: () -> Unit,
    onNavigate: (String) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Fun Games", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFE082), Color(0xFFFFF9C4))
                    )
                )
                .padding(padding)
        ) {
            val columns = if (maxWidth > 600.dp) 3 else 2
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(columns),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(gameItems) { game ->
                    GameCard(
                        title = game.title,
                        emoji = game.emoji,
                        color = game.color,
                        onClick = { onNavigate(game.route) }
                    )
                }
            }
        }
    }
}

@Composable
fun GameCard(
    title: String,
    emoji: String,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        modifier = Modifier
            .fillMaxWidth()
            .height(160.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = emoji, fontSize = 48.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}
