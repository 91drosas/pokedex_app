package com.example.pokedex.repository

import com.example.pokedex.api.PokemonListApi
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonDetail
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonListApi: PokemonListApi
) {
    suspend fun getPokemonList(limit: Int, offset: Int): List<Pokemon> {
        val pokemonList = pokemonListApi.getPokemonList(limit, offset)
        return pokemonList.results.map { pokemon ->
            val pokemonDetail = pokemonListApi.getPokemonDetail(pokemon.url)
            val imageUrl = pokemonDetail.sprites.front_default
            val type = pokemonDetail.types
            Pokemon(pokemon.name, pokemon.url, imageUrl, type)
        }
    }

    suspend fun getPokemonDetail(pokemonId: String): PokemonDetail {
        val response = pokemonListApi.getPokemonDetail(pokemonId)
        return PokemonDetail(
            name = response.name,
            type = response.type,
            imageUrl = response.imageUrl,
            sprites = response.sprites,
            types = response.types
        )
    }
}