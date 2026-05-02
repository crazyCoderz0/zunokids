package com.crazycoderz.zunokids.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight

// Brand Colors
val CoralRed = Color(0xFFFF6B6B)
val SkyBlue = Color(0xFF4ECDC4)
val SunshineYellow = Color(0xFFFFE66D)
val MintGreen = Color(0xFF95E1D3)
val Lavender = Color(0xFFA8DADC)
val DeepNavy = Color(0xFF1A1A2E)
val NavyLight = Color(0xFF16213E)
val CardSurface = Color(0xFF0F3460)
val CardWhite = Color(0xFFF8F9FA)
val PurpleAccent = Color(0xFFE94560)
val OrangePop = Color(0xFFFF9F43)
val GreenPop = Color(0xFF1DD1A1)

// New Gamified Colors
val KidsYellow = Color(0xFFFFE66D)
val KidsBlue   = Color(0xFF4ECDC4)
val KidsRed    = Color(0xFFFF6B6B)
val KidsGreen  = Color(0xFF00F5D4)
val KidsPurple = Color(0xFF9B5DE5)

private val KidsDarkColorScheme = darkColorScheme(
    primary = CoralRed,
    onPrimary = Color.White,
    secondary = SkyBlue,
    onSecondary = DeepNavy,
    tertiary = SunshineYellow,
    onTertiary = DeepNavy,
    background = DeepNavy,
    onBackground = Color.White,
    surface = NavyLight,
    onSurface = Color.White,
    surfaceVariant = CardSurface,
    onSurfaceVariant = Color.White,
    error = PurpleAccent,
    onError = Color.White
)

val KidsFontFamily = FontFamily.Default // Replace with Nunito when font file added

@Composable
fun ZunoKidsTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = KidsDarkColorScheme,
        typography = KidsTypography,
        content = content
    )
}
