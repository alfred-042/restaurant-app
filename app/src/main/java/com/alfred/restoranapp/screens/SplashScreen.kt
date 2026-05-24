package com.alfred.restoranapp.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alfred.restoranapp.ui.theme.CardWhite
import com.alfred.restoranapp.ui.theme.PrimaryOrange
import com.alfred.restoranapp.ui.theme.SoftOrange
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val scale = remember { Animatable(0.7f) }

    // DIPERBAIKI: Menggunakan Unit sebagai key standar LaunchedEffect untuk operasi sekali jalan (one-time)
    LaunchedEffect(Unit) {
        // Menjalankan animasi pembesaran logo (0.7f ke 1.0f) selama 1.2 detik
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1200,
                easing = FastOutSlowInEasing
            )
        )

        // Menahan layar splash sejenak (total durasi layar berdiri sekitar 3 detik)
        delay(1800)

        // Berpindah ke halaman utama dan menghapus splash dari tumpukan backstack
        navController.navigate("home") {
            popUpTo("splash") {
                inclusive = true
            }
        }
    }

    // DIPERBAIKI: Menggunakan gradasi warna latar belakang sesuai tema Orange & Cream
    val isLight = !isSystemInDarkTheme()
    val gradientColors = if (isLight) {
        listOf(PrimaryOrange, SoftOrange) // Gradasi Orange saat Light Mode
    } else {
        listOf(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.background)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.verticalGradient(colors = gradientColors)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.scale(scale.value)
        ) {
            // --- KOTAK BULAT LOGO ---
            Box(
                modifier = Modifier
                    .size(130.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Restaurant,
                    contentDescription = null,
                    tint = if (isLight) PrimaryOrange else MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(70.dp)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // --- TEKS BRANDING ---
            Text(
                text = "Restoran Nusantara",
                style = MaterialTheme.typography.headlineMedium,
                color = if (isLight) CardWhite else MaterialTheme.colorScheme.onPrimaryContainer,
                fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Premium Food Experience",
                color = if (isLight) CardWhite.copy(alpha = 0.8f) else MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}
