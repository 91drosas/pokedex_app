package com.example.pokedex.model

data class Pokemon(
    val name: String,
    val url: String,
    val imageUrl: String? = null,
    val type: List<PokemonType>
)