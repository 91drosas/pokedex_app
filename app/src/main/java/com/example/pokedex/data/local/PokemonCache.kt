package com.example.pokedex.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_cache")
data class PokemonCache(
    @PrimaryKey val name: String, // Nombre del Pokémon
    val data: String // Respuesta de la API en formato JSON
)