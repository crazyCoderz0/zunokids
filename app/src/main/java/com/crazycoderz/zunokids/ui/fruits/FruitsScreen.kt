package com.crazycoderz.zunokids.ui.fruits

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crazycoderz.zunokids.data.FruitsContent
import com.crazycoderz.zunokids.data.TtsManager
import com.crazycoderz.zunokids.data.UserPreferencesRepo
import com.crazycoderz.zunokids.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

// ─── Fruit name maps (simple hardcoded translation lookup) ────────────────────

val fruitNames: Map<String, Map<String, String>> = mapOf(
    "fruit_apple"      to mapOf("en" to "Apple",      "es" to "Manzana",  "zh" to "苹果",    "hi" to "सेब",       "fr" to "Pomme"),
    "fruit_banana"     to mapOf("en" to "Banana",     "es" to "Plátano",  "zh" to "香蕉",    "hi" to "केला",       "fr" to "Banane"),
    "fruit_mango"      to mapOf("en" to "Mango",      "es" to "Mango",    "zh" to "芒果",    "hi" to "आम",        "fr" to "Mangue"),
    "fruit_orange"     to mapOf("en" to "Orange",     "es" to "Naranja",  "zh" to "橙子",    "hi" to "संतरा",      "fr" to "Orange"),
    "fruit_grape"      to mapOf("en" to "Grapes",     "es" to "Uvas",     "zh" to "葡萄",    "hi" to "अंगूर",      "fr" to "Raisins"),
    "fruit_strawberry" to mapOf("en" to "Strawberry", "es" to "Fresa",    "zh" to "草莓",    "hi" to "स्ट्रॉबेरी", "fr" to "Fraise"),
    "fruit_watermelon" to mapOf("en" to "Watermelon", "es" to "Sandía",   "zh" to "西瓜",    "hi" to "तरबूज",     "fr" to "Pastèque"),
    "fruit_pineapple"  to mapOf("en" to "Pineapple",  "es" to "Piña",     "zh" to "菠萝",    "hi" to "अनानास",     "fr" to "Ananas"),
    "fruit_cherry"     to mapOf("en" to "Cherry",     "es" to "Cereza",   "zh" to "樱桃",    "hi" to "चेरी",      "fr" to "Cerise"),
    "fruit_lemon"      to mapOf("en" to "Lemon",      "es" to "Limón",    "zh" to "柠檬",    "hi" to "नींबू",      "fr" to "Citron")
)

fun fruitName(key: String, lang: String) = fruitNames[key]?.get(lang) ?: fruitNames[key]?.get("en") ?: key

// ─── ViewModel ────────────────────────────────────────────────────────────────

@HiltViewModel
class FruitsViewModel @Inject constructor(
    private val prefsRepo: UserPreferencesRepo
) : ViewModel() {
    val items = FruitsContent.items
    val selectedLanguage: StateFlow<String> = prefsRepo.selectedLanguage
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "en")

    init {
        // Mark completed immediately since it's a grid (all visible at once)
        viewModelScope.launch { prefsRepo.markCompleted("fruits") }
    }
}

// ─── Screen ───────────────────────────────────────────────────────────────────

@Composable
fun FruitsScreen(
    onBack: () -> Unit,
    viewModel: FruitsViewModel = hiltViewModel()
) {
    val language by viewModel.selectedLanguage.collectAsState()
    val context = LocalContext.current
    val tts = remember { TtsManager(context) }
    DisposableEffect(Unit) { onDispose { tts.shutdown() } }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1A1A2E), Color(0xFF16213E))))
            .systemBarsPadding()
    ) {
        Column(Modifier.fillMaxSize()) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(52.dp)
                        .background(Color.White.copy(alpha = 0.15f), androidx.compose.foundation.shape.CircleShape)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(Modifier.weight(1f))
                Text(
                    "🍎 Fruits",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(Modifier.weight(1f))
                Spacer(Modifier.size(52.dp)) // balance
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(viewModel.items) { fruit ->
                    val name = fruitName(fruit.nameKey, language)
                    FruitCard(
                        emoji = fruit.emoji,
                        name = name,
                        color = fruit.color,
                        onClick = { tts.speak(name, language) }
                    )
                }
            }
        }
    }
}

@Composable
fun FruitCard(
    emoji: String,
    name: String,
    color: Color,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.12f else 1f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium),
        label = "fruit_scale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .scale(scale)
            .height(150.dp)
            .fillMaxWidth()
            .background(
                Brush.verticalGradient(listOf(color.copy(alpha = 0.4f), color.copy(alpha = 0.15f))),
                RoundedCornerShape(24.dp)
            )
            .clip(RoundedCornerShape(24.dp))
            .clickable(interactionSource = interactionSource, indication = null) { onClick() }
            .padding(12.dp)
    ) {
        Text(emoji, fontSize = 52.sp)
        Spacer(Modifier.height(8.dp))
        Text(
            text = name,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
        Spacer(Modifier.height(4.dp))
        Text("🔊", fontSize = 18.sp)
    }
}
