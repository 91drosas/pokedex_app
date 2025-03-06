package com.example.pokedex.model

// Modelo de datos para la respuesta de detalles
data class PokemonDetails(
    val name: String,
    val species: String,
    val height: Float,
    val weight: Float,
    val abilities: List<AbilityDetail>,
    val stats: List<StatPokemonDetail>,
    val sprites: SpritesDetail
)

data class AbilityDetail(val name: String)
data class StatPokemonDetail(val base_stat: Int, val stat: StatDetail)
data class StatDetail(val name: String)
data class SpritesDetail(val front_default: String)