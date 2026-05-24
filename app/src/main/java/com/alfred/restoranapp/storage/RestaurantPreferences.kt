package com.alfred.restoranapp.storage

import android.content.Context

class RestaurantPreferences(context: Context) {

    private val sharedPreferences = context.getSharedPreferences(
        "restaurant_data",
        Context.MODE_PRIVATE
    )

    fun saveRestaurantProfile(
        name: String,
        address: String,
        description: String,
        openHours: String
    ) {
        sharedPreferences.edit().apply {
            putString("name", name)
            putString("address", address)
            putString("description", description)
            putString("openHours", openHours)
            apply()
        }
    }

    fun getRestaurantName(): String {
        return sharedPreferences.getString("name", "Restoran Nusantara") ?: "Restoran Nusantara"
    }

    fun getAddress(): String {
        return sharedPreferences.getString("address", "Alamat belum diisi") ?: "Alamat belum diisi"
    }

    fun getDescription(): String {
        return sharedPreferences.getString("description", "Deskripsi belum diisi") ?: "Deskripsi belum diisi"
    }

    fun getOpenHours(): String {
        return sharedPreferences.getString("openHours", "Jam buka belum diisi") ?: "Jam buka belum diisi"
    }

    // SUDAH DIPINDAH KE DALAM CLASS (Sekarang aman dari error)
    fun saveDarkMode(isDark: Boolean) {
        sharedPreferences.edit()
            .putBoolean("dark_mode", isDark)
            .apply()
    }

    // SUDAH DIPINDAH KE DALAM CLASS
    fun isDarkMode(): Boolean {
        return sharedPreferences.getBoolean(
            "dark_mode",
            false
        )
    }
} // <- Penutup class RestaurantPreferences harus berada di paling bawah file