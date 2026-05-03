package com.crazycoderz.zunokids.ui.parent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crazycoderz.zunokids.ui.theme.DeepNavy
import com.crazycoderz.zunokids.ui.theme.SunshineYellow

@Composable
fun ParentDashboardScreen(
    onBack: () -> Unit,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit,
    viewModel: ParentViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val isAuthorized by viewModel.isAuthorized.collectAsState()
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Logout") },
            text = { Text("Are you sure you want to logout?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        onLogout()
                    }
                ) {
                    Text("Yes", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("No")
                }
            }
        )
    }

    if (!isAuthorized) {
        ParentLockScreen(
            correctPin = uiState.parentPin,
            onSuccess = { viewModel.authorize(uiState.parentPin) },
            onBack = onBack
        )
    } else {
        DashboardContent(
            uiState = uiState,
            onBack = onBack,
            onLogout = { showLogoutDialog = true },
            onDeleteAccount = onDeleteAccount
        )
    }
}

@Composable
fun ParentLockScreen(
    correctPin: String,
    onSuccess: () -> Unit,
    onBack: () -> Unit
) {
    var input by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DeepNavy)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "Parents Only",
            style = MaterialTheme.typography.headlineLarge,
            color = SunshineYellow,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            "Enter PIN to access dashboard",
            color = Color.White.copy(alpha = 0.7f)
        )
        
        Spacer(Modifier.height(32.dp))
        
        // PIN Display
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            repeat(4) { i ->
                val char = input.getOrNull(i)
                Surface(
                    modifier = Modifier.size(20.dp),
                    shape = RoundedCornerShape(10.dp),
                    color = if (char != null) SunshineYellow else Color.White.copy(alpha = 0.2f)
                ) {}
            }
        }
        
        Spacer(Modifier.height(32.dp))
        
        // Numeric Keypad
        val numbers = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "C", "0", "←")
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.width(240.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(numbers.size) { index ->
                val label = numbers[index]
                Button(
                    onClick = {
                        when (label) {
                            "C" -> input = ""
                            "←" -> if (input.isNotEmpty()) input = input.dropLast(1)
                            else -> if (input.length < 4) {
                                input += label
                                if (input == correctPin) onSuccess()
                            }
                        }
                    },
                    modifier = Modifier.size(64.dp),
                    shape = RoundedCornerShape(32.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.1f))
                ) {
                    Text(label, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
        
        Spacer(Modifier.height(32.dp))
        
        TextButton(onClick = onBack) {
            Text("Go Back", color = SunshineYellow)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardContent(
    uiState: ParentUiState,
    onBack: () -> Unit,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Parent Dashboard") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Logout", color = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DeepNavy,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = DeepNavy
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatsCard("Total Stars", "⭐ ${uiState.stats.stars}", SunshineYellow)
            StatsCard("Daily Streak", "🔥 ${uiState.stats.streakCount} Days", Color(0xFFFF6B6B))
            StatsCard("Learning Time", "⏱️ ${uiState.stats.totalTimeSpent} Minutes", Color(0xFF4ECDC4))
            
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onDeleteAccount,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF4D4D)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Delete Profile", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Text(
                "Completed Modules",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp)
            )
            
            uiState.completedModules.forEach { module ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.1f))
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(module.replaceFirstChar { it.uppercase() }, color = Color.White)
                        Spacer(Modifier.weight(1f))
                        Text("✅", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun StatsCard(title: String, value: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.2f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, style = MaterialTheme.typography.labelMedium, color = color)
            Text(value, style = MaterialTheme.typography.headlineMedium, color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}
