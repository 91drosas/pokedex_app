package com.example.pokedex.model

import PokemonType

data class Pokemon(
    val name: String,
    val url: String,
    val imageUrl: String? = null,
    val type: List<PokemonType>
)

/*data class PokemonType(
    val slot: Int,
    val type: PokemonTypeDetail
)*/

data class PokemonTypeDetail(
    val name: String,
    val url: String
)

/*data class PokemonDetail(
    val name: String,
    val species: Species,
    val height: String,
    val weight: String,
    val type: String,
    val imageUrl: String,
    val stats: List<Stats>,
    val sprites: PokemonSprites,
    val types: List<PokemonType>? = null,
    val abilities: List<PokemonAbilities>? = null
)*/

data class Species(
    val name: String?,
    val url: String?
)

data class PokemonSprites(
    val front_default: String?
)

data class PokemonAbilities(
    val name: String?
)

data class Stats(
    val stat: Stat?,
    val base_stat: String?
)

data class Stat(
    val name: String?,
    val url: String?
)