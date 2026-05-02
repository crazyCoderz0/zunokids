package com.crazycoderz.zunokids.ui.games.newgames

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crazycoderz.zunokids.ui.games.GameOverView
import com.crazycoderz.zunokids.ui.games.viewmodel.DragMatchViewModel
import com.crazycoderz.zunokids.ui.shared.ConfettiAnimation
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DragMatchGame(
    onBack: () -> Unit,
    viewModel: DragMatchViewModel = hiltViewModel()
) {
    val score by viewModel.score
    val items = viewModel.items
    val matchedItems = viewModel.matchedItems
    val isGameOver = matchedItems.size == items.size

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Match the Fruit!", fontWeight = FontWeight.Bold) },
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
                    score = score / 10,
                    total = items.size,
                    onRestart = { viewModel.resetGame() },
                    onBack = onBack
                )
            } else {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFE0F7FA))
                ) {
                    val isTablet = maxWidth > 600.dp
                    val itemSize = if (isTablet) 120.dp else 80.dp
                    val targetSize = if (isTablet) 140.dp else 100.dp

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Text("Score: $score", fontSize = if (isTablet) 32.sp else 24.sp, fontWeight = FontWeight.Bold)
                        
                        // Draggable Items
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            items.forEach { item ->
                                if (matchedItems[item.id] != true) {
                                    DraggableFruit(
                                        label = item.label,
                                        color = item.color,
                                        size = itemSize,
                                        onMatch = { viewModel.onMatch(item.id) }
                                    )
                                } else {
                                    Spacer(modifier = Modifier.size(itemSize))
                                }
                            }
                        }
                        
                        // Targets
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            items.forEach { item ->
                                Box(
                                    modifier = Modifier
                                        .size(targetSize)
                                        .background(item.color.copy(alpha = 0.2f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = item.label, 
                                        fontSize = if (isTablet) 60.sp else 40.sp, 
                                        modifier = Modifier.alpha(0.5f)
                                    )
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
fun DraggableFruit(
    label: String,
    color: Color,
    size: androidx.compose.ui.unit.Dp,
    onMatch: () -> Unit
) {
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
            .size(size)
            .background(color, CircleShape)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {
                        // Simple logic: if dropped near targets area (bottom half)
                        if (offset.y > 150f) {
                            onMatch()
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
        Text(label, fontSize = (size.value * 0.5).sp)
    }
}
