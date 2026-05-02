package com.crazycoderz.zunokids.ui.games.newgames

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crazycoderz.zunokids.data.TtsManager
import com.crazycoderz.zunokids.ui.games.GameOverView
import com.crazycoderz.zunokids.ui.games.viewmodel.ShapeMatchViewModel
import com.crazycoderz.zunokids.ui.shared.ConfettiAnimation
import com.crazycoderz.zunokids.util.SoundManager
import kotlin.math.roundToInt

enum class ShapeType { CIRCLE, SQUARE, TRIANGLE }

data class ShapeTarget(val type: ShapeType, val color: Color)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShapeMatchGame(
    onBack: () -> Unit,
    viewModel: ShapeMatchViewModel = hiltViewModel()
) {
    val shapes = viewModel.shapes
    val matchedCount by viewModel.matchedCount
    val isGameOver = matchedCount == shapes.size

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Match the Shapes", fontWeight = FontWeight.Bold) },
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
                    score = matchedCount,
                    total = shapes.size,
                    onRestart = { viewModel.restartGame() },
                    onBack = onBack
                )
            } else {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFDFCF0))
                ) {
                    val isTablet = maxWidth > 600.dp
                    
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        // Slots (Empty slots on the left for tablet, top for phone)
                        if (isTablet) {
                            Row(modifier = Modifier.fillMaxSize()) {
                                Column(
                                    modifier = Modifier.weight(1f).fillMaxHeight(),
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    shapes.forEach { shape ->
                                        ShapeSlot(shape)
                                    }
                                }
                                Column(
                                    modifier = Modifier.weight(1f).fillMaxHeight(),
                                    verticalArrangement = Arrangement.SpaceEvenly,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    shapes.shuffled().forEach { shape ->
                                        DraggableShape(shape) {
                                            viewModel.onMatch(shape.type)
                                        }
                                    }
                                }
                            }
                        } else {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.SpaceEvenly,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                                    shapes.forEach { ShapeSlot(it) }
                                }
                                Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                                    shapes.shuffled().forEach { shape ->
                                        DraggableShape(shape) {
                                            viewModel.onMatch(shape.type)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShapeSlot(shape: ShapeTarget) {
    Box(
        modifier = Modifier
            .size(100.dp)
            .background(Color.LightGray.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
        contentAlignment = Alignment.Center
    ) {
        DrawShape(shape.type, Color.Gray.copy(alpha = 0.5f))
    }
}

@Composable
fun DraggableShape(shape: ShapeTarget, onMatched: () -> Unit) {
    var offset by remember { mutableStateOf(Offset.Zero) }
    var isMatched by remember { mutableStateOf(false) }

    if (!isMatched) {
        Box(
            modifier = Modifier
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .size(100.dp)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = {
                            // Simple logic for matching
                            if (offset.x < -200f || offset.y < -200f) { // Arbitrary check for demonstration
                                isMatched = true
                                onMatched()
                            } else {
                                offset = Offset.Zero
                            }
                        },
                        onDrag = { change, dragAmount ->
                            change.consume()
                            offset += dragAmount
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            DrawShape(shape.type, shape.color)
        }
    } else {
        Spacer(modifier = Modifier.size(100.dp))
    }
}

@Composable
fun DrawShape(type: ShapeType, color: Color) {
    Canvas(modifier = Modifier.size(60.dp)) {
        when (type) {
            ShapeType.CIRCLE -> drawCircle(color)
            ShapeType.SQUARE -> drawRect(color)
            ShapeType.TRIANGLE -> {
                val path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(size.width / 2, 0f)
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                }
                drawPath(path, color)
            }
        }
    }
}
