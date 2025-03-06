package com.example.pokedex.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.pokedex.R

@Composable
fun PokemonDetailScreen(
    pokemonId: String,
    navController: NavController
) {
    // Aquí puedes obtener los detalles del Pokémon usando el ID (por ejemplo, desde un ViewModel)
    val viewModel: PokemonDetailViewModel = hiltViewModel()
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()

    // Simular la carga de detalles
    LaunchedEffect(pokemonId) {
        viewModel.loadPokemonDetail(pokemonId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Botón para regresar
        IconButton(onClick = { navController.popBackStack() }) {
            Icon(
                painter = painterResource(id = R.drawable.icon_back),
                contentDescription = "Volver"
            )
        }

        // Mostrar detalles del Pokémon
        if (pokemonDetail != null) {
            Text(
                text = pokemonDetail!!.name.uppercase(),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Tipo: ${pokemonDetail!!.type}",
                style = MaterialTheme.typography.bodyLarge
            )
            // Agrega más detalles aquí (imagen, estadísticas, etc.)
        } else {
            Text("Cargando...")
        }
    }
}