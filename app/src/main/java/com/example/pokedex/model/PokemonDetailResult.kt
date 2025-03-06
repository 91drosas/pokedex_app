package com.example.pokedex.model

data class PokemonDetailResult(
    val abilities: List<Ability>,
    val base_experience: Int,
    val cries: Cries,
    val forms: List<Form>,
    val game_indices: List<GameIndice>,
    val height: Int,
    val held_items: List<Any?>,
    val id: Int,
    val is_default: Boolean,
    val location_area_encounters: String,
    val moves: List<Move>,
    val name: String,
    val order: Int,
    val past_abilities: List<Any?>,
    val past_types: List<Any?>,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stat>,
    val types: List<PokemonType>,
    val weight: Int
)

data class Ability(
    val name: String
)

data class Cries(
    val name: String
)

data class Form(
    val name: String
)

data class GameIndice(
    val name: String
)

data class Move(
    val name: String
)

data class Species(
    val name: String
)

data class Sprites(
    val name: String,
    val front_default: String
)

data class Stat(
    val name: String
)