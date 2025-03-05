package com.example.pokedex.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.model.Pokemon

@Composable
fun PokemonScreen(
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    Log.d("pokemonScreen", "${state.size}")
    Column(
        modifier = Modifier.fillMaxSize() // Asegura que el Column ocupe toda la pantalla
    ) {
        // LazyColumn con weight para ocupar el espacio restante
        LazyColumn(
            modifier = Modifier.weight(1f) // Ocupa el espacio restante
        ) {
            items(state) { pokemon ->
                PokemonCard(pokemon)
            }
        }

        // Botones de paginación
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { viewModel.previousPage() }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = "Atrás"
                )
            }
            Button(onClick = { viewModel.nextPage() }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_next),
                    contentDescription = "Siguiente"
                )
            }
        }
    }
}

@Composable
fun PokemonCard(
    pokemon: Pokemon,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Column {
            Row {
                Surface(
                    modifier = modifier.size(120.dp),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                ) {
                    pokemon.imageUrl?.let { url ->
                        AsyncImage(
                            model = url,
                            contentDescription = pokemon.name,
                            contentScale = ContentScale.FillBounds
                        )
                    }
                }

                Column(
                    modifier
                        .padding(16.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(text = pokemon.name,
                        style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }
}
