package com.example.pokedex.repository

import PokemonDetail
import com.example.pokedex.api.PokemonListApi
import com.example.pokedex.model.Pokemon
import com.example.pokedex.utils.Constants.BASE_URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
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

    suspend fun getPokemonDetail(pokemonName: String): PokemonDetail {
        val response = withContext(Dispatchers.IO) {
            pokemonListApi.getPokemonDetail(BASE_URL + "pokemon/" + pokemonName + "/")
        }

        // Convertimos height y weight a enteros
        val pokemonDetail = response.copy(
            height = response.height.toInt(),  // Convertir de String a Int
            weight = response.weight.toInt()   // Convertir de String a Int
        )

        return pokemonDetail
    }
}