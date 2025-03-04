package com.example.pokedex.model

data class PokemonList(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Pokemon>
)