package com.crazycoderz.zunokids.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.crazycoderz.zunokids.R
import com.crazycoderz.zunokids.ui.shared.KidButton
import com.crazycoderz.zunokids.ui.theme.DeepNavy
import com.crazycoderz.zunokids.ui.theme.KidsBlue
import com.crazycoderz.zunokids.ui.theme.SunshineYellow

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignup: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState) {
        if (uiState is AuthState.Authenticated) {
            onLoginSuccess()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Kids-style background gradient
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF87CEEB), Color(0xFFE0F7FA), Color(0xFFFFF9C4))
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome Back! 👋",
                style = MaterialTheme.typography.displaySmall,
                color = Color(0xFF2C3E50),
                fontWeight = FontWeight.ExtraBold
            )
            
            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it.replace(" ", "") },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                enabled = uiState !is AuthState.Loading,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                    focusedBorderColor = KidsBlue,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                enabled = uiState !is AuthState.Loading,
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f),
                    focusedBorderColor = KidsBlue,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black
                )
            )

            Spacer(Modifier.height(32.dp))

            if (uiState is AuthState.Loading) {
                CircularProgressIndicator(color = KidsBlue)
            } else {
                KidButton(
                    text = "Login",
                    color = KidsBlue,
                    onClick = { viewModel.login(email.trim(), password) }
                )
            }

            if (uiState is AuthState.Error) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = (uiState as AuthState.Error).message,
                        color = Color.Red,
                        modifier = Modifier.padding(8.dp),
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            TextButton(
                onClick = onNavigateToSignup,
                enabled = uiState !is AuthState.Loading
            ) {
                Text("Don't have an account? Sign Up", color = Color(0xFF2C3E50))
            }
        }
    }
}
