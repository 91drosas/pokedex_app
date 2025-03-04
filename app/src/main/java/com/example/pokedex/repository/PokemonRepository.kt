package com.example.pokedex.repository

import com.example.pokedex.api.PokemonListApi
import com.example.pokedex.model.PokemonList
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonListApi: PokemonListApi
) {
    suspend fun getPokemonList() : PokemonList {
        return pokemonListApi.getPokemonList()
    }
}