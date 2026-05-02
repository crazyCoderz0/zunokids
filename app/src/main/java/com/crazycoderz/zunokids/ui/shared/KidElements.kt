package com.crazycoderz.zunokids.ui.shared

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.*

@Composable
fun KidButton(
    text: String,
    color: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 500f),
        label = "kid_button_scale"
    )

    Surface(
        onClick = onClick,
        enabled = enabled,
        shape = RoundedCornerShape(24.dp),
        color = if (enabled) color else Color.Gray.copy(alpha = 0.3f),
        modifier = modifier
            .scale(scale)
            .height(80.dp)
            .shadow(if (enabled) 8.dp else 0.dp, RoundedCornerShape(24.dp)),
        interactionSource = interactionSource
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
            Text(
                text = text,
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
        }
    }
}

@Composable
fun FunMascot(
    animationRes: Int,
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationRes))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.size(200.dp)
    )
}

@Composable
fun ConfettiAnimation(
    modifier: Modifier = Modifier,
    onAnimationFinished: () -> Unit = {}
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(com.crazycoderz.zunokids.R.raw.confetti))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )

    if (progress == 1f) {
        LaunchedEffect(Unit) {
            onAnimationFinished()
        }
    }

    LottieAnimation(
        composition = composition,
        progress = { progress },
        modifier = modifier.fillMaxSize()
    )
}

@Composable
private fun MutableInteractionSource.collectIsPressedAsState(): State<Boolean> {
    val isPressed = remember { mutableStateOf(false) }
    LaunchedEffect(this) {
        interactions.collect { interaction ->
            when (interaction) {
                is androidx.compose.foundation.interaction.PressInteraction.Press -> isPressed.value = true
                is androidx.compose.foundation.interaction.PressInteraction.Release -> isPressed.value = false
                is androidx.compose.foundation.interaction.PressInteraction.Cancel -> isPressed.value = false
            }
        }
    }
    return isPressed
}
