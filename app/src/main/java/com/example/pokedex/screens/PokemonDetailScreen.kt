package com.example.pokedex.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import android.util.Base64

@Composable
fun PokemonDetailScreen(
    encryptedPokemonUrl: String
) {
    val decryptedUrl = decryptUrl(encryptedPokemonUrl)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "URL: $decryptedUrl",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

fun decryptUrl(encryptedUrl: String): String {
    return String(Base64.decode(encryptedUrl, Base64.DEFAULT))
}