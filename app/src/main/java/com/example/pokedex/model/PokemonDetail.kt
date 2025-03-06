package com.example.pokedex.model

data class PokemonDetail(
    val name: String,
    val species: Species,
    val height: Int,
    val weight: Int,
    val stats: List<Stat>,
    val sprites: PokemonSprites,
    val types: List<PokemonType>
)

data class Species(
    val name: String,
    val url: String
)

data class Stat(
    val stat: StatInfo,
    val base_stat: Int
)

data class StatInfo(
    val name: String,
    val url: String
)

data class PokemonSprites(
    val front_default: String,
    val other: Other
)

data class Other(
    val home: Home,
)

data class Home(
    val front_default: String
)

data class PokemonType(
    val slot: Int,
    val type: PokemonTypeDetail
)

data class PokemonTypeDetail(
    val name: String,
    val url: String
)
