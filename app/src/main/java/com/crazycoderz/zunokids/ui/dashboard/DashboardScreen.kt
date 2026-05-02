package com.crazycoderz.zunokids.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DashboardScreen(
    onNavigateToLearn: () -> Unit,
    onNavigateToGames: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF87CEEB), Color(0xFFE0F7FA))
                )
            )
    ) {
        val isTablet = maxWidth > 600.dp
        
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "ZunoKids",
                fontSize = if (isTablet) 64.sp else 48.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF2C3E50)
            )
            
            Spacer(modifier = Modifier.height(if (isTablet) 60.dp else 40.dp))
            
            if (isTablet) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(32.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    DashboardButton(
                        text = "Learn 📚",
                        color = Color(0xFFFF6B6B),
                        onClick = onNavigateToLearn,
                        modifier = Modifier.weight(1f).aspectRatio(1.2f).padding(16.dp)
                    )
                    DashboardButton(
                        text = "Games 🎮",
                        color = Color(0xFF4ECDC4),
                        onClick = onNavigateToGames,
                        modifier = Modifier.weight(1f).aspectRatio(1.2f).padding(16.dp)
                    )
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DashboardButton(
                        text = "Learn 📚",
                        color = Color(0xFFFF6B6B),
                        onClick = onNavigateToLearn,
                        modifier = Modifier.fillMaxWidth().height(180.dp).padding(vertical = 12.dp)
                    )
                    DashboardButton(
                        text = "Games 🎮",
                        color = Color(0xFF4ECDC4),
                        onClick = onNavigateToGames,
                        modifier = Modifier.fillMaxWidth().height(180.dp).padding(vertical = 12.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardButton(
    text: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}
