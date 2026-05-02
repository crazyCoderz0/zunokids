package com.crazycoderz.zunokids.ui.objects

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.shape.CircleShape
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
import com.crazycoderz.zunokids.data.ObjectsContent
import com.crazycoderz.zunokids.data.TtsManager
import com.crazycoderz.zunokids.data.UserPreferencesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

val objectNames: Map<String, Map<String, String>> = mapOf(
    "object_ball"  to mapOf("en" to "Ball",  "es" to "Pelota", "zh" to "球",  "hi" to "गेंद",  "fr" to "Balle"),
    "object_book"  to mapOf("en" to "Book",  "es" to "Libro",  "zh" to "书",  "hi" to "किताब", "fr" to "Livre"),
    "object_chair" to mapOf("en" to "Chair", "es" to "Silla",  "zh" to "椅子", "hi" to "कुर्सी", "fr" to "Chaise"),
    "object_cup"   to mapOf("en" to "Cup",   "es" to "Taza",   "zh" to "杯子", "hi" to "कप",   "fr" to "Tasse"),
    "object_door"  to mapOf("en" to "Door",  "es" to "Puerta", "zh" to "门",  "hi" to "दरवाजा", "fr" to "Porte"),
    "object_tree"  to mapOf("en" to "Tree",  "es" to "Árbol",  "zh" to "树",  "hi" to "पेड़",   "fr" to "Arbre"),
    "object_car"   to mapOf("en" to "Car",   "es" to "Coche",  "zh" to "汽车", "hi" to "कार",   "fr" to "Voiture"),
    "object_sun"   to mapOf("en" to "Sun",   "es" to "Sol",    "zh" to "太阳", "hi" to "सूरज",  "fr" to "Soleil"),
    "object_star"  to mapOf("en" to "Star",  "es" to "Estrella","zh" to "星星", "hi" to "तारा",  "fr" to "Étoile"),
    "object_house" to mapOf("en" to "House", "es" to "Casa",   "zh" to "房子", "hi" to "घर",   "fr" to "Maison")
)
fun objectName(key: String, lang: String) = objectNames[key]?.get(lang) ?: objectNames[key]?.get("en") ?: key

@HiltViewModel
class ObjectsViewModel @Inject constructor(private val prefsRepo: UserPreferencesRepo) : ViewModel() {
    val items = ObjectsContent.items
    val selectedLanguage: StateFlow<String> = prefsRepo.selectedLanguage
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "en")
    init { viewModelScope.launch { prefsRepo.markCompleted("objects") } }
}

@Composable
fun ObjectsScreen(
    onBack: () -> Unit,
    viewModel: ObjectsViewModel = hiltViewModel()
) {
    val language by viewModel.selectedLanguage.collectAsState()
    val context = LocalContext.current
    val tts = remember { TtsManager(context) }
    DisposableEffect(Unit) { onDispose { tts.shutdown() } }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1A1A2E), Color(0xFF0F3460))))
            .systemBarsPadding()
    ) {
        Column(Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(52.dp)
                        .background(Color.White.copy(alpha = 0.15f), CircleShape)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(Modifier.weight(1f))
                Text(
                    "⭐ Objects",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(Modifier.weight(1f))
                Spacer(Modifier.size(52.dp))
            }

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(viewModel.items) { obj ->
                    val name = objectName(obj.nameKey, language)
                    ObjectCard(
                        emoji = obj.emoji,
                        name = name,
                        color = obj.color,
                        onClick = { tts.speak(name, language) }
                    )
                }
            }
        }
    }
}

@Composable
fun ObjectCard(emoji: String, name: String, color: Color, onClick: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.10f else 1f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessMedium),
        label = "obj_scale"
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
        Text(name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 22.sp,
            textAlign = TextAlign.Center, maxLines = 1)
        Spacer(Modifier.height(4.dp))
        Text("🔊", fontSize = 18.sp)
    }
}
