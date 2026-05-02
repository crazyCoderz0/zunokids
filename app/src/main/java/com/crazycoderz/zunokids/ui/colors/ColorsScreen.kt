package com.crazycoderz.zunokids.ui.colors

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.crazycoderz.zunokids.data.ColorsContent
import com.crazycoderz.zunokids.data.TtsManager
import com.crazycoderz.zunokids.data.UserPreferencesRepo
import com.crazycoderz.zunokids.ui.shared.CountdownRing
import com.crazycoderz.zunokids.ui.shared.ProgressDots
import com.crazycoderz.zunokids.ui.theme.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

val colorNames: Map<String, Map<String, String>> = mapOf(
    "color_red"    to mapOf("en" to "Red",    "es" to "Rojo",     "zh" to "红色", "hi" to "लाल",    "fr" to "Rouge"),
    "color_blue"   to mapOf("en" to "Blue",   "es" to "Azul",     "zh" to "蓝色", "hi" to "नीला",   "fr" to "Bleu"),
    "color_yellow" to mapOf("en" to "Yellow", "es" to "Amarillo", "zh" to "黄色", "hi" to "पीला",   "fr" to "Jaune"),
    "color_green"  to mapOf("en" to "Green",  "es" to "Verde",    "zh" to "绿色", "hi" to "हरा",    "fr" to "Vert"),
    "color_orange" to mapOf("en" to "Orange", "es" to "Naranja",  "zh" to "橙色", "hi" to "नारंगी", "fr" to "Orange"),
    "color_purple" to mapOf("en" to "Purple", "es" to "Morado",   "zh" to "紫色", "hi" to "बैंगनी", "fr" to "Violet"),
    "color_pink"   to mapOf("en" to "Pink",   "es" to "Rosa",     "zh" to "粉色", "hi" to "गुलाबी", "fr" to "Rose"),
    "color_white"  to mapOf("en" to "White",  "es" to "Blanco",   "zh" to "白色", "hi" to "सफेद",   "fr" to "Blanc"),
    "color_black"  to mapOf("en" to "Black",  "es" to "Negro",    "zh" to "黑色", "hi" to "काला",   "fr" to "Noir"),
    "color_brown"  to mapOf("en" to "Brown",  "es" to "Marrón",   "zh" to "棕色", "hi" to "भूरा",   "fr" to "Marron")
)
fun colorName(key: String, lang: String) = colorNames[key]?.get(lang) ?: colorNames[key]?.get("en") ?: key

@HiltViewModel
class ColorsViewModel @Inject constructor(private val prefsRepo: UserPreferencesRepo) : ViewModel() {
    val items = ColorsContent.items
    val selectedLanguage: StateFlow<String> = prefsRepo.selectedLanguage
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), "en")
    init { viewModelScope.launch { prefsRepo.markCompleted("colors") } }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColorsScreen(
    onBack: () -> Unit,
    viewModel: ColorsViewModel = hiltViewModel()
) {
    val language by viewModel.selectedLanguage.collectAsState()
    val context = LocalContext.current
    val tts = remember { TtsManager(context) }
    DisposableEffect(Unit) { onDispose { tts.shutdown() } }

    val pagerState = rememberPagerState { viewModel.items.size }
    val currentItem = viewModel.items[pagerState.currentPage]

    val bgColor by animateColorAsState(
        targetValue = currentItem.color,
        animationSpec = tween(500),
        label = "bg_color"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(bgColor)
            .systemBarsPadding()
    ) {
        Column(Modifier.fillMaxSize()) {
            // Top bar
            Row(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(52.dp)
                        .background(Color.Black.copy(alpha = 0.25f), CircleShape)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back",
                        tint = currentItem.textColor)
                }
                Spacer(Modifier.weight(1f))
                Text(
                    "🌈 Colors",
                    style = MaterialTheme.typography.headlineMedium,
                    color = currentItem.textColor,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(Modifier.weight(1f))
                IconButton(
                    onClick = { tts.speak(colorName(currentItem.nameKey, language), language) },
                    modifier = Modifier
                        .size(52.dp)
                        .background(Color.Black.copy(alpha = 0.25f), CircleShape)
                ) {
                    Text("🔊", fontSize = 24.sp)
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                val item = viewModel.items[page]
                val name = colorName(item.nameKey, language)
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Color swatch circle
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .background(item.color, CircleShape)
                                .then(
                                    if (item.nameKey == "color_white")
                                        Modifier.clip(CircleShape)
                                    else Modifier
                                )
                        )

                        Spacer(Modifier.height(32.dp))

                        Text(
                            text = name,
                            fontSize = 60.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = currentItem.textColor
                        )
                    }
                }
            }

            // Bottom
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                ProgressDots(
                    count = viewModel.items.size,
                    currentIndex = pagerState.currentPage,
                    activeColor = currentItem.textColor
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "← Swipe →",
                    color = currentItem.textColor.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
