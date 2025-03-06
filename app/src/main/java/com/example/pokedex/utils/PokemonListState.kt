package com.example.pokedex.utils

import com.example.pokedex.model.Pokemon

sealed class PokemonListState {
    object Loading : PokemonListState()
    data class Success(val pokemonList: List<Pokemon>) : PokemonListState()
    data class Error(val message: String) : PokemonListState()
}