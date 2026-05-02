package com.crazycoderz.zunokids.ui.shared

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.crazycoderz.zunokids.ui.theme.*

/**
 * Reusable scaffold for all learning module screens.
 */
@Composable
fun ModuleScaffold(
    title: String,
    emoji: String,
    currentIndex: Int,
    total: Int,
    onBack: () -> Unit,
    onPrev: () -> Unit,
    onNext: () -> Unit,
    onSpeak: () -> Unit,
    accentColor: Color,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(listOf(Color(0xFF1A1A2E), Color(0xFF16213E)))
            )
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(52.dp)
                        .background(Color.White.copy(alpha = 0.15f), CircleShape)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White,
                        modifier = Modifier.size(28.dp)
                    )
                }
                Spacer(Modifier.weight(1f))
                Text(
                    text = "$emoji $title",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(Modifier.weight(1f))
                // Speaker button
                IconButton(
                    onClick = onSpeak,
                    modifier = Modifier
                        .size(52.dp)
                        .background(accentColor.copy(alpha = 0.3f), CircleShape)
                ) {
                    Text("🔊", fontSize = 24.sp)
                }
            }

            // Content area
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(16.dp),
                contentAlignment = Alignment.Center,
                content = content
            )

            // Bottom controls
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                // Progress dots
                ProgressDots(
                    count = total,
                    currentIndex = currentIndex,
                    activeColor = accentColor
                )

                Spacer(Modifier.height(20.dp))

                // Prev / Next arrows
                Row(
                    horizontalArrangement = Arrangement.spacedBy(32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    NavArrowButton(
                        icon = "◀",
                        enabled = currentIndex > 0,
                        onClick = onPrev,
                        color = accentColor
                    )
                    Text(
                        text = "${currentIndex + 1} / $total",
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.titleMedium
                    )
                    NavArrowButton(
                        icon = "▶",
                        enabled = currentIndex < total - 1,
                        onClick = onNext,
                        color = accentColor
                    )
                }
            }
        }
    }
}

@Composable
fun NavArrowButton(
    icon: String,
    enabled: Boolean,
    onClick: () -> Unit,
    color: Color
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.88f else 1f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 600f),
        label = "arrow_scale"
    )

    Box(
        modifier = Modifier
            .scale(scale)
            .size(72.dp)
            .background(
                if (enabled) color.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.05f),
                CircleShape
            )
            .clip(CircleShape)
            .then(
                if (enabled) Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { onClick() }
                else Modifier
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = icon,
            fontSize = 32.sp,
            color = if (enabled) color else Color.White.copy(alpha = 0.3f)
        )
    }
}

@Composable
fun ProgressDots(
    count: Int,
    currentIndex: Int,
    activeColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier
    ) {
        repeat(count) { index ->
            val isActive = index == currentIndex
            val size by animateDpAsState(
                targetValue = if (isActive) 16.dp else 10.dp,
                animationSpec = spring(dampingRatio = 0.6f),
                label = "dot_size"
            )
            Box(
                modifier = Modifier
                    .size(size)
                    .background(
                        if (isActive) activeColor else Color.White.copy(alpha = 0.3f),
                        CircleShape
                    )
            )
        }
    }
}

/**
 * Circular countdown ring drawn with Canvas.
 */
@Composable
fun CountdownRing(
    progressFraction: Float,   // 0f..1f, 1f = full ring
    color: Color,
    size: Dp = 48.dp,
    strokeWidth: Dp = 5.dp
) {
    Canvas(modifier = Modifier.size(size)) {
        val stroke = strokeWidth.toPx()
        val radius = (this.size.minDimension - stroke) / 2f
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        // Track
        drawCircle(
            color = color.copy(alpha = 0.2f),
            radius = radius,
            center = center,
            style = Stroke(width = stroke)
        )
        // Progress arc
        drawArc(
            color = color,
            startAngle = -90f,
            sweepAngle = 360f * progressFraction,
            useCenter = false,
            topLeft = Offset(center.x - radius, center.y - radius),
            size = Size(radius * 2, radius * 2),
            style = Stroke(width = stroke, cap = StrokeCap.Round)
        )
    }
}

/**
 * A bouncy learning card with scale spring on press.
 */
@Composable
fun BouncyCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF0F3460),
    cornerRadius: Int = 28,
    onClick: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 1.08f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "card_scale"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .scale(scale)
            .background(backgroundColor, RoundedCornerShape(cornerRadius.dp))
            .clip(RoundedCornerShape(cornerRadius.dp))
            .clickable(interactionSource = interactionSource, indication = null) { onClick() }
            .padding(16.dp),
        content = content
    )
}
