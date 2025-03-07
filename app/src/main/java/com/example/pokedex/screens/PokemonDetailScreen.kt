package com.example.pokedex.screens

import android.util.Base64
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokedex.utils.Constants
import com.example.pokedex.utils.Constants.getTypeIcon

@Composable
fun PokemonDetailScreen(pokemonName: String, viewModel: PokemonDetailViewModel = hiltViewModel()) {
    val pokemonDetail by viewModel.pokemonDetail.collectAsState()

    // Cargar detalles del Pokémon
    LaunchedEffect(pokemonName) {
        viewModel.loadPokemonDetail(pokemonName)
    }

    pokemonDetail?.let { detail ->
        /*Column(
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
                contentScale = ContentScale.Crop*//*,
                placeholder = painterResource(R.drawable.placeholder), // TODO Agrega un placeholder
                error = painterResource(R.drawable.error) // TODO Agrega una imagen de error*//*
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
        }*/





        Column(modifier = Modifier.fillMaxSize()) {
            // Imagen en la parte superior
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .height(300.dp)
                    .background(Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                /*Image(
                    painter = rememberImagePainter(pokemon.imageUrl),
                    contentDescription = pokemon.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )*/

                // Imagen en la parte superior
                AsyncImage(
                    model = detail.sprites.other.home.front_default,
                    contentDescription = detail.name,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentScale = ContentScale.Crop/*,
                placeholder = painterResource(R.drawable.placeholder), // TODO Agrega un placeholder
                error = painterResource(R.drawable.error) // TODO Agrega una imagen de error*/
                )
            }

            // Datos en la parte inferior
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = detail.name.uppercase(),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    //Text(text = "ID: #${detail.id}", fontSize = 18.sp, fontWeight = FontWeight.Medium)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Altura: ${detail.height / 10.0} m", fontSize = 18.sp)
                    Text(text = "Peso: ${detail.weight / 10.0} kg", fontSize = 18.sp)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Tipos:", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        detail.types.forEach { type ->
                            Row(
                                modifier = Modifier
                                    .padding(end = 8.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.Gray.copy(alpha = 0.2f))
                                    .padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    painter = painterResource(id = getTypeIcon(type.type.name)),
                                    contentDescription = type.type.name,
                                    tint = Constants.typeColors[type.type.name] ?: Color.Black,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = type.type.name.uppercase(),
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun decryptUrl(encryptedUrl: String): String {
    val decodedBytes = Base64.decode(encryptedUrl, Base64.DEFAULT)
    return String(decodedBytes)
}
