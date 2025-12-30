package com.example.memomiles.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

/**
 * SplashScreen.kt
 *
 * Stylish animated splash screen.
 * Fades in the app title and slogan, waits, then navigates to the Home screen.
 * Uses the MemoMiles color palette and smooth animation.
 */

@Composable
fun SplashScreen(navController: NavHostController) {
    var startAnimation by remember { mutableStateOf(false) }

    // Animate opacity from 0f to 1f
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1500),
        label = "FadeIn"
    )

    // Trigger animation and navigate after delay
    LaunchedEffect(Unit) {
        startAnimation = true
        delay(2500)
        navController.navigate("home") {
            popUpTo("splash") { inclusive = true }
        }
    }

    // Splash UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF4E342E)), // Chocolate brown background
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.alpha(alphaAnim)
        ) {
            Text(
                text = "MemoMiles",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFBE9E7) // Light paper color
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Capture Moments. Cherish Miles.",
                fontSize = 16.sp,
                color = Color(0xFFFBE9E7)
            )
        }
    }
}
