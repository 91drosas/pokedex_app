package com.example.pokedex.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.utils.Constants.typeColors
import com.example.pokedex.utils.Constants.typeTranslations

@Composable
fun PokemonScreen(
    navController: NavController,
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val searchResult by viewModel.searchResult.collectAsState()
    val searchHistory by viewModel.searchHistory.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var isSearchAreaVisible by remember { mutableStateOf(false) } // Estado para controlar la visibilidad

    Log.d("pokemonScreen", "${state.size}")

    // Cargar el historial de búsquedas al iniciar la pantalla
    LaunchedEffect(Unit) {
        viewModel.loadSearchHistory()
    }

    Column(
        modifier = Modifier
            .fillMaxSize() // Asegura que el Column ocupe toda la pantalla
            .background(Color.White) // Red
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Título
            Text(
                text = "Pokedex".uppercase(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black
            )

            // Botones de lupa y menú lateral
            Row {
                // Botón de lupa para mostrar/ocultar el área de búsqueda
                IconButton(onClick = { isSearchAreaVisible = !isSearchAreaVisible }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_search), // Ícono de lupa
                        contentDescription = if (isSearchAreaVisible) "Ocultar buscador" else "Mostrar buscador",
                        modifier = Modifier.size(32.dp)
                    )
                }

                // Botón del menú lateral
                IconButton(onClick = { /* Abrir menú lateral */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_pokeball),
                        contentDescription = "Menú lateral",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        // Área del buscador (visible u oculta)
        if (isSearchAreaVisible) {
            Column {
                // Campo de búsqueda
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    placeholder = { Text("Buscar Pokémon...") }
                )

                // Botón de búsqueda
                Button(
                    onClick = { viewModel.searchPokemon(searchQuery) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text("Buscar")
                }

                // Mostrar resultados de búsqueda
                searchResult?.let { pokemonDetail ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .clickable {
                                // Navegar a la pantalla de detalles
                                navController.navigate("pokemon_detail/${pokemonDetail.name}")
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = pokemonDetail.name.uppercase(),
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                text = "Tipo: ${pokemonDetail.types.joinToString(", ") { it.type.name.uppercase() }}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                // Mostrar historial de búsquedas
                LazyColumn {
                    items(searchHistory) { history ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Texto de la búsqueda
                            Text(
                                text = history.search_query,
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { searchQuery = history.search_query }
                            )

                            // Botón para eliminar la búsqueda
                            IconButton(
                                onClick = { viewModel.deleteSearchHistory(history.search_query) },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_close), // Ícono de "X"
                                    contentDescription = "Eliminar búsqueda"
                                )
                            }
                        }
                    }
                }
            }
        }

        // Content
        LazyColumn(
            modifier = Modifier
                .weight(1f) // Ocupa el espacio restante
                .background(Color.White)
        ) {
            items(state) { pokemon ->
                PokemonCard(
                    pokemon = pokemon,
                    onClick = {
                        navController.navigate("pokemon_detail/${pokemon.name}")
                    }
                )
            }
        }

        // Botones de paginación
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { viewModel.previousPage() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_back),
                    contentDescription = "Atrás"
                )
            }
            Button(
                onClick = { viewModel.nextPage() },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
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
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Obtener el primer tipo del Pokémon (puedes ajustar esto si hay múltiples tipos)
    val primaryType = pokemon.type?.firstOrNull()?.type?.name ?: "normal"

    // Obtener el color correspondiente al tipo, o un color por defecto si no se encuentra
    val cardColor = typeColors[primaryType] ?: Color.Gray

    // Traducir el tipo a español
    val translatedType = typeTranslations[primaryType] ?: "normal"

    Card(
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row {
            // Imagen con fondo blanco
            Surface(
                modifier = Modifier
                    .size(120.dp)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(16.dp)), // Redondear esquinas de la imagen
                color = Color.White // Fondo blanco para la imagen
            ) {
                pokemon.imageUrl?.let { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = pokemon.name,
                        contentScale = ContentScale.FillBounds/*,
                        placeholder = painterResource(R.drawable.placeholder), // TODO Agrega un placeholder
                        error = painterResource(R.drawable.error) // TODO Agrega una imagen de error*/
                    )
                }
            }

            // Bloque de información
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = pokemon.name.uppercase(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White // Texto en blanco
                )
                Text(
                    text = "Tipo: $translatedType", // Mostrar el tipo en español
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White // Texto en blanco
                )
            }
        }
    }
}

fun PokemonDetail.toPokemon(): Pokemon {
    return Pokemon(
        name = this.name,
        url = this.species.url, // Otra URL relevante si es necesario
        imageUrl = this.sprites.other.home.front_default,
        type = this.types
    )
}
