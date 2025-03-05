package com.example.pokedex.repository

import com.example.pokedex.api.PokemonListApi
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonList
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonListApi: PokemonListApi
) {
    /*suspend fun getPokemonList() : PokemonList {
        return pokemonListApi.getPokemonList(10, 0)
    }*/

    suspend fun getPokemonList(limit: Int, offset: Int): List<Pokemon> {
        val pokemonList = pokemonListApi.getPokemonList(limit, offset)
        return pokemonList.results.map { pokemon ->
            val pokemonDetail = pokemonListApi.getPokemonDetail(pokemon.url)
            val imageUrl = pokemonDetail.sprites.front_default
            Pokemon(pokemon.name, pokemon.url, imageUrl)
        }
    }
}