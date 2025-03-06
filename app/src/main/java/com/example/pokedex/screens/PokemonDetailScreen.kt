package com.example.pokedex.screens

import android.util.Base64
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun PokemonDetailScreen(pokemonName: String, viewModel: PokemonDetailViewModel = hiltViewModel()) {
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()

    // Cargar detalles del Pokémon
    LaunchedEffect(pokemonName) {
        viewModel.loadPokemonDetail(pokemonName)
    }

    pokemonDetail?.let { detail ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Imagen en la parte superior
            AsyncImage(
                model = detail.sprites.other.home.front_default,
                contentDescription = detail.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Crop/*,
                placeholder = painterResource(R.drawable.placeholder), // TODO Agrega un placeholder
                error = painterResource(R.drawable.error) // TODO Agrega una imagen de error*/
            )

            // Información del Pokémon en la parte inferior
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                Text(text = "Name: ${detail.name}", style = MaterialTheme.typography.h6)
                Text(text = "Height: ${detail.height} cm", style = MaterialTheme.typography.body1)
                Text(
                    text = "Weight: ${detail.weight} hectograms",
                    style = MaterialTheme.typography.body1
                )

                // Mostrar las estadísticas
                detail.stats.forEach { stat ->
                    Text(
                        text = "${stat.stat.name.capitalize()}: ${stat.base_stat}",
                        style = MaterialTheme.typography.body1
                    )
                }

                // Mostrar tipos
                val types = detail.types.joinToString(", ") { it.type.name.capitalize() }
                Text(text = "Types: $types", style = MaterialTheme.typography.body1)
            }
        }
    }
}

fun decryptUrl(encryptedUrl: String): String {
    val decodedBytes = Base64.decode(encryptedUrl, Base64.DEFAULT)
    return String(decodedBytes)
}
