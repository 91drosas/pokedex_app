package com.example.pokedex.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.pokedex.R

object Constants {
    const val BASE_URL = "https://pokeapi.co/api/v2/"

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

    @Composable
    fun getTypeIcon(type: String): Int {
        return when (type) {
            "fire" -> R.drawable.ic_type_fire
            "water" -> R.drawable.ic_type_water
            "grass" -> R.drawable.ic_type_grass
            "electric" -> R.drawable.ic_type_electric
            "ice" -> R.drawable.ic_type_ice
            "fighting" -> R.drawable.ic_type_fighting
            "poison" -> R.drawable.ic_type_poison
            "ground" -> R.drawable.ic_type_ground
            "flying" -> R.drawable.ic_type_flying
            "psychic" -> R.drawable.ic_type_psychic
            "bug" -> R.drawable.ic_type_bug
            "rock" -> R.drawable.ic_type_rock
            "ghost" -> R.drawable.ic_type_ghost
            "dragon" -> R.drawable.ic_type_dragon
            "dark" -> R.drawable.ic_type_dark
            "steel" -> R.drawable.ic_type_steel
            "fairy" -> R.drawable.ic_type_fairy
            else -> R.drawable.ic_type_unknown
        }
    }

    fun String.toPokemonDetailUrl(): String {
        return "$BASE_URL/pokemon/$this/"
    }
}