package com.crazycoderz.zunokids.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.crazycoderz.zunokids.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToDashboard: () -> Unit
) {
    LaunchedEffect(Unit) {
        delay(2000)
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            onNavigateToDashboard()
        } else {
            onNavigateToLogin()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_img),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}
