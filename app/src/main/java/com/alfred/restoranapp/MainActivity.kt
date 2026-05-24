package com.alfred.restoranapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alfred.restoranapp.screens.DetailMenuScreen
import com.alfred.restoranapp.screens.EditProfileScreen
import com.alfred.restoranapp.screens.HomeScreen
import com.alfred.restoranapp.screens.MenuScreen
import com.alfred.restoranapp.screens.ProfileScreen
import com.alfred.restoranapp.screens.SplashScreen
import com.alfred.restoranapp.storage.RestaurantPreferences // <- DIPERBAIKI: Import ini ditambahkan agar tidak Unresolved Reference

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val preferences = remember { RestaurantPreferences(context) }

            var isDarkMode by remember {
                mutableStateOf(preferences.isDarkMode())
            }

            val colors = if (isDarkMode) {
                darkColorScheme()
            } else {
                lightColorScheme()
            }

            MaterialTheme(
                colorScheme = colors
            ) {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    floatingActionButton = {
                        // Kondisi: Hanya tampilkan tombol di halaman utama, menu, dan profil
                        if (currentRoute == "home" || currentRoute == "menu" || currentRoute == "profile") {
                            FloatingActionButton(
                                onClick = {
                                    Toast.makeText(
                                        context,
                                        "Keranjang masih kosong",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Fastfood,
                                    contentDescription = "Cart"
                                )
                            }
                        }
                    },
                    bottomBar = {
                        // Sembunyikan bottomBar saat berada di halaman Splash Screen
                        if (currentRoute != "splash") {
                            NavigationBar {
                                NavigationBarItem(
                                    selected = currentRoute == "home",
                                    onClick = {
                                        if (currentRoute != "home") {
                                            navController.navigate("home") {
                                                popUpTo("home") { inclusive = true }
                                            }
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.Home,
                                            contentDescription = "Home"
                                        )
                                    }
                                )

                                NavigationBarItem(
                                    selected = currentRoute == "menu",
                                    onClick = {
                                        if (currentRoute != "menu") {
                                            navController.navigate("menu")
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = "Menu"
                                        )
                                    }
                                )

                                NavigationBarItem(
                                    selected = currentRoute == "profile",
                                    onClick = {
                                        if (currentRoute != "profile") {
                                            navController.navigate("profile")
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = "Profile"
                                        )
                                    }
                                )
                            }
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "splash",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("splash") {
                            SplashScreen(navController = navController)
                        }

                        composable("home") {
                            HomeScreen(
                                navController = navController,
                                onThemeChanged = { diperbarui ->
                                    isDarkMode = diperbarui
                                }
                            )
                        }

                        composable("menu") {
                            MenuScreen(navController = navController)
                        }

                        composable("profile") {
                            ProfileScreen(navController = navController)
                        }

                        composable(
                            route = "detail/{menuId}",
                            arguments = listOf(
                                navArgument("menuId") {
                                    type = NavType.IntType
                                }
                            )
                        ) { backStackEntry ->
                            val menuId = backStackEntry.arguments?.getInt("menuId") ?: 0

                            DetailMenuScreen(
                                navController = navController,
                                menuId = menuId
                            )
                        }

                        composable("editProfile") {
                            EditProfileScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}