package com.example.pokedex.repository

import com.example.pokedex.api.PokemonApi
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.utils.Constants.toPokemonDetailUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonListApi: PokemonApi
) {
    suspend fun getPokemonList(limit: Int, offset: Int): List<Pokemon> {
        val pokemonList = pokemonListApi.getPokemonList(limit, offset)
        return pokemonList.results.map { pokemon ->
            val pokemonDetail = pokemonListApi.getPokemonDetail(pokemon.url)
            val imageUrl = pokemonDetail.sprites.other.home.front_default
            val type = pokemonDetail.types
            Pokemon(pokemon.name, pokemon.url, imageUrl, type)
        }
    }

    suspend fun getPokemonDetail(pokemonName: String): PokemonDetail {
        val url = pokemonName.toPokemonDetailUrl()
        val response = withContext(Dispatchers.IO) {
            pokemonListApi.getPokemonDetail(url)
        }

        val pokemonDetail = response.copy(
            height = response.height,
            weight = response.weight
        )

        return pokemonDetail
    }
}