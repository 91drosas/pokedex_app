package com.example.pokedex.model

data class Pokemon(
    val name: String,
    val url: String,
    val imageUrl: String? = null,
    val type: List<PokemonType>? = null,
)

data class PokemonType(
    val slot: Int,
    val type: PokemonTypeDetail
)

data class PokemonTypeDetail(
    val name: String,
    val url: String
)

data class PokemonDetail(
    val sprites: PokemonSprites,
    val types: List<PokemonType> // Lista de tipos desde la API
)

data class PokemonSprites(
    val front_default: String?
)