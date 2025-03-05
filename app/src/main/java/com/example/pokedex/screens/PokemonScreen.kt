package com.example.pokedex.screens

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokedex.R
import com.example.pokedex.model.Pokemon
import java.util.Locale

@Composable
fun PokemonScreen(
    viewModel: PokemonViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current
    val view = LocalView.current
    Log.d("pokemonScreen", "${state.size}")

    // Ocultar la barra de navegación y la barra de notificaciones
    DisposableEffect(Unit) {
        val window = (context as Activity).window
        val insetsController = WindowCompat.getInsetsController(window, view)

        // Configurar el comportamiento de las barras del sistema
        insetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // Ocultar la barra de notificaciones y la barra de navegación
        insetsController.hide(WindowInsetsCompat.Type.systemBars())

        // Restaurar la visibilidad de las barras al salir de la pantalla
        onDispose {
            insetsController.show(WindowInsetsCompat.Type.systemBars())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize() // Asegura que el Column ocupe toda la pantalla
            .background(Color.Red)
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
                color = Color.White
            )

            // Botón del menú lateral
            IconButton(onClick = { /* Abrir menú lateral */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.icon_pokeball),
                    contentDescription = "Menú lateral",
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }
        // Content
        LazyColumn(
            modifier = Modifier
                .weight(1f) // Ocupa el espacio restante
                .background(Color.White)
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
            Button(onClick = { viewModel.previousPage() },
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
    modifier: Modifier = Modifier
) {
    // Mapa de colores para cada tipo de Pokémon
    val typeColors = mapOf(
        "steel" to Color(0xFF60A1B8),
        "water" to Color(0xFF2980EF),
        "bug" to Color(0xFF91A119),
        "dragon" to Color(0xFF5061E1),
        "electric" to Color(0xFFFAC000),
        "ghost" to Color(0xFF704170),
        "fire" to Color(0xFFE62829),
        "fairy" to Color(0xFFEF71EF),
        "ice" to Color(0xFF3FD8FF),
        "fighting" to Color(0xFFFF8000),
        "normal" to Color(0xFF9FA19F),
        "grass" to Color(0xFF3FA129),
        "psychic" to Color(0xFFEF4179),
        "rock" to Color(0xFFAFA981),
        "dark" to Color(0xFF50413F),
        "ground" to Color(0xFF915121),
        "poison" to Color(0xFF8F41CB),
        "flying" to Color(0xFF81B9EF),
        "egg" to Color(0xFF6BA294)
    )

    // Mapa de traducciones (inglés -> español)
    val typeTranslations = mapOf(
        "steel" to "acero",
        "water" to "agua",
        "bug" to "bicho",
        "dragon" to "dragón",
        "electric" to "eléctrico",
        "ghost" to "fantasma",
        "fire" to "fuego",
        "fairy" to "hada",
        "ice" to "hielo",
        "fighting" to "lucha",
        "normal" to "normal",
        "grass" to "planta",
        "psychic" to "psíquico",
        "rock" to "roca",
        "dark" to "siniestro",
        "ground" to "tierra",
        "poison" to "veneno",
        "flying" to "volador",
        "egg" to "huevo"
    )

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
            .padding(horizontal = 8.dp, vertical = 4.dp),
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
                        contentScale = ContentScale.FillBounds
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
