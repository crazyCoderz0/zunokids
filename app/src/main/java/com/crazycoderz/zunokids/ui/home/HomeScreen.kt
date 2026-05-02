package com.crazycoderz.zunokids.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crazycoderz.zunokids.R
import com.crazycoderz.zunokids.data.SupportedLanguages
import com.crazycoderz.zunokids.navigation.Screen
import com.crazycoderz.zunokids.ui.theme.*

data class ModuleInfo(
    val route: String,
    val emoji: String,
    val titleEn: String,
    val gradient: List<Color>
)

val modules = listOf(
    ModuleInfo(Screen.Numbers.route,  "🔢", "Numbers",  listOf(Color(0xFFFF6B6B), Color(0xFFFF8E53))),
    ModuleInfo(Screen.Alphabet.route, "🔤", "Alphabet", listOf(Color(0xFF4ECDC4), Color(0xFF44A08D))),
    ModuleInfo(Screen.Fruits.route,   "🍎", "Fruits",   listOf(Color(0xFFFFE66D), Color(0xFFF9D423))),
    ModuleInfo(Screen.Colors.route,   "🌈", "Colors",   listOf(Color(0xFFA8DADC), Color(0xFF457B9D))),
    ModuleInfo(Screen.Objects.route,  "⭐", "Objects",  listOf(Color(0xFF6C5CE7), Color(0xFFa29bfe)))
)

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit,
    onNavigateToParent: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Kids background sky color
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF87CEEB), Color(0xFFE0F7FA))
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Bar with Stats and Parent Access
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Stars & Streaks
                Surface(
                    color = Color.White.copy(alpha = 0.95f),
                    shape = RoundedCornerShape(24.dp),
                    shadowElevation = 6.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("⭐", fontSize = 20.sp)
                        Spacer(Modifier.width(4.dp))
                        Text("${uiState.stars}", color = Color(0xFFFBC02D), fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                        Spacer(Modifier.width(16.dp))
                        Text("🔥", fontSize = 20.sp)
                        Spacer(Modifier.width(4.dp))
                        Text("${uiState.streak}", color = Color(0xFFE64A19), fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                    }
                }

                // Parent/Settings Button
                IconButton(
                    onClick = onNavigateToParent,
                    modifier = Modifier
                        .background(Color.White.copy(alpha = 0.95f), CircleShape)
                        .size(48.dp)
                        .shadow(4.dp, CircleShape)
                ) {
                    Text("⚙️", fontSize = 24.sp)
                }
            }

            // Welcome Message & Mascot Placeholder
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Hi, Zuno! 👋",
                    style = MaterialTheme.typography.displaySmall,
                    color = Color(0xFF2C3E50),
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(Modifier.height(8.dp))

            // Language Selector
            LanguageSelector(
                selectedCode = uiState.selectedLanguage,
                onLanguageSelected = viewModel::setLanguage
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "What do you want to learn?",
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF34495E),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))

            // Module Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                contentPadding = PaddingValues(
                    start = 20.dp, 
                    end = 20.dp, 
                    top = 16.dp, 
                    bottom = 120.dp // Added significant padding to avoid navigation buttons
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                // Learning Modules Header
                item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                    Text(
                        "Learning Modules",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF2C3E50),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                items(modules) { module ->
                    ModuleCard(
                        module = module,
                        isCompleted = module.route in uiState.completedModules,
                        onClick = { onNavigate(module.route) }
                    )
                }

                // Mini Games Header
                item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(2) }) {
                    Text(
                        "Mini Games",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF2C3E50),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                item {
                    GameCard(
                        title = "Quiz Time",
                        emoji = "❓",
                        gradient = listOf(Color(0xFF6A11CB), Color(0xFF2575FC)),
                        onClick = { onNavigate(Screen.Quiz.route) }
                    )
                }

                item {
                    GameCard(
                        title = "Match Cards",
                        emoji = "🧩",
                        gradient = listOf(Color(0xFFF093FB), Color(0xFFF5576C)),
                        onClick = { onNavigate(Screen.MatchCards.route) }
                    )
                }

                item {
                    GameCard(
                        title = "Drag Match",
                        emoji = "🤝",
                        gradient = listOf(Color(0xFF4FACFE), Color(0xFF00F2FE)),
                        onClick = { onNavigate(Screen.DragMatch.route) }
                    )
                }

                item {
                    GameCard(
                        title = "Shape Match",
                        emoji = "📐",
                        gradient = listOf(Color(0xFF43E97B), Color(0xFF38F9D7)),
                        onClick = { onNavigate(Screen.ShapeMatch.route) }
                    )
                }

                item {
                    GameCard(
                        title = "Categories",
                        emoji = "📁",
                        gradient = listOf(Color(0xFFFA709A), Color(0xFFFEE140)),
                        onClick = { onNavigate(Screen.CategoryGame.route) }
                    )
                }

                item {
                    GameCard(
                        title = "Missing Letter",
                        emoji = "📝",
                        gradient = listOf(Color(0xFF667EEA), Color(0xFF764BA2)),
                        onClick = { onNavigate(Screen.MissingLetter.route) }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun GameCard(
    title: String,
    emoji: String,
    gradient: List<Color>,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(120.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.verticalGradient(gradient))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = emoji, fontSize = 32.sp)
            Text(text = title, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun LanguageSelector(
    selectedCode: String,
    onLanguageSelected: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(
                Color.White.copy(alpha = 0.1f),
                RoundedCornerShape(50.dp)
            )
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        SupportedLanguages.list.forEach { lang ->
            val isSelected = lang.code == selectedCode
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(if (isSelected) 52.dp else 44.dp)
                    .background(
                        if (isSelected) SunshineYellow.copy(alpha = 0.3f) else Color.Transparent,
                        CircleShape
                    )
                    .clip(CircleShape)
                    .clickable { onLanguageSelected(lang.code) }
                    .padding(4.dp)
            ) {
                Text(
                    text = lang.flag,
                    fontSize = if (isSelected) 28.sp else 22.sp
                )
            }
        }
    }
}

@Composable
fun ModuleCard(
    module: ModuleInfo,
    isCompleted: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.93f else 1f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 500f),
        label = "card_scale"
    )

    Box(
        modifier = Modifier
            .scale(scale)
            .height(160.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Brush.verticalGradient(module.gradient))
            .clickable(interactionSource = interactionSource, indication = null) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = module.emoji,
                fontSize = 52.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = module.titleEn,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )
        }

        // Completed badge
        if (isCompleted) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .size(32.dp)
                    .background(Color(0xFF1DD1A1), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text("✅", fontSize = 18.sp)
            }
        }
    }
}
