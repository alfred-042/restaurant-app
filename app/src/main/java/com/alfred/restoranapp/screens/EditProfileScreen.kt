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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun EditProfileScreen(navController: NavController) {
    val context = LocalContext.current

    // 1. DIPERBAIKI: Membungkus inisialisasi preference dengan remember agar tidak re-init terus menerus
    val preferences = remember { RestaurantPreferences(context) }

    var name by remember { mutableStateOf(preferences.getRestaurantName()) }
    var address by remember { mutableStateOf(preferences.getAddress()) }
    var description by remember { mutableStateOf(preferences.getDescription()) }
    var openHours by remember { mutableStateOf(preferences.getOpenHours()) }

    // Mengambil skema warna aktif dari MaterialTheme (Light/Dark Mode)
    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    val surfaceColor = MaterialTheme.colorScheme.surface

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor) // Menggunakan background adaptif dari tema
    ) {
        // Dekorasi lingkaran di sudut kanan atas dengan opasitas tipis mengikuti warna tema utama
        Box(
            modifier = Modifier
                .size(180.dp)
                .offset(x = 110.dp, y = (-40).dp)
                .clip(CircleShape)
                .background(primaryColor.copy(alpha = 0.1f))
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "🍽 Edit Restaurant",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = primaryColor // 2. DIPERBAIKI: Mengikuti warna tema
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Atur profil restoranmu agar terlihat lebih menarik",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- HEADER CARD PREVIEW ---
            Card(
                shape = RoundedCornerShape(28.dp),
                colors = CardDefaults.cardColors(
                    containerColor = surfaceColor // 3. DIPERBAIKI: Card berubah jadi gelap saat dark mode
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(primaryColor.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Restaurant,
                            contentDescription = null,
                            tint = primaryColor,
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = name.ifEmpty { "Nama Restoran" },
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "✨ Premium Restaurant Profile",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- FORM INPUTS ---
            ModernTextField(
                value = name,
                label = "Nama Restoran",
                primaryColor = primaryColor,
                onValueChange = { name = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = address,
                label = "Alamat",
                primaryColor = primaryColor,
                onValueChange = { address = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = description,
                label = "Deskripsi",
                primaryColor = primaryColor,
                onValueChange = { description = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernTextField(
                value = openHours,
                label = "Jam Buka",
                primaryColor = primaryColor,
                onValueChange = { openHours = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- TOMBOL SAVE & CANCEL ---
            Button(
                onClick = {
                    preferences.saveRestaurantProfile(
                        name = name,
                        address = address,
                        description = description,
                        openHours = openHours
                    )
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
            ) {
                Text(
                    text = "Save Profile",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Text(
                    text = "Cancel",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ModernTextField(
    value: String,
    label: String,
    primaryColor: Color,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = primaryColor, // Border saat dipilih dinamis ikuti tema
            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant, // Border default pasif adaptif
            focusedLabelColor = primaryColor
        )
    )
}