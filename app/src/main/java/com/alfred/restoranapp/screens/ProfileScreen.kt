package com.alfred.restoranapp.screens
import com.alfred.restoranapp.ui.theme.*

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.alfred.restoranapp.storage.RestaurantPreferences

@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current

    // 1. DIPERBAIKI: Membungkus inisialisasi preference dengan remember agar hemat memori
    val preferences = remember { RestaurantPreferences(context) }

    var restaurantName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var openHours by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        restaurantName = preferences.getRestaurantName()
        address = preferences.getAddress()
        description = preferences.getDescription()
        openHours = preferences.getOpenHours()
    }

    // 2. DIPERBAIKI: Mengambil warna adaptif dari MaterialTheme sistem
    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val surfaceColor = MaterialTheme.colorScheme.surface

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        // Dekorasi lingkaran estetik di sudut kanan atas
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = 120.dp, y = (-60).dp)
                .clip(CircleShape)
                .background(primaryColor.copy(alpha = 0.1f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Restaurant Profile",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = primaryColor
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- KARTU PROFIL UTAMA ---
            Card(
                shape = RoundedCornerShape(30.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = surfaceColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(primaryColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Restaurant,
                            contentDescription = null,
                            tint = primaryColor,
                            modifier = Modifier.size(44.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        text = restaurantName.ifEmpty { "Memuat..." },
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Surface(
                        shape = RoundedCornerShape(50.dp),
                        color = primaryColor.copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "✨ Premium Restaurant",
                            modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp),
                            color = primaryColor,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- SEKSI DETAIL INFORMASI ---
            ModernInfoCard(
                title = "Alamat",
                value = address.ifEmpty { "Belum diatur" },
                emoji = "📍",
                primaryColor = primaryColor,
                surfaceColor = surfaceColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernInfoCard(
                title = "Deskripsi",
                value = description.ifEmpty { "Belum diatur" },
                emoji = "📝",
                primaryColor = primaryColor,
                surfaceColor = surfaceColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernInfoCard(
                title = "Jam Operasional",
                value = openHours.ifEmpty { "Belum diatur" },
                emoji = "🕒",
                primaryColor = primaryColor,
                surfaceColor = surfaceColor
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- TOMBOL EDIT PROFILE ---
            Button(
                onClick = { navController.navigate("editProfile") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                Text(
                    text = "Edit Profile",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ModernInfoCard(
    title: String,
    value: String,
    emoji: String,
    primaryColor: Color,
    surfaceColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = surfaceColor) // Menggunakan surface adaptif
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "$emoji $title",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                color = primaryColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}