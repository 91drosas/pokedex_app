package com.example.pokedex.repository

import com.example.pokedex.api.PokemonApi
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.model.PokemonList
import com.example.pokedex.utils.Constants.toPokemonDetailUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonListApi: PokemonApi
) {
    suspend fun getPokemonList(limit: Int, offset: Int): PokemonList {
        return try {
            val pokemonList = pokemonListApi.getPokemonList(limit, offset)
            val pokemonDetails = pokemonList.results.map { pokemon ->
                val pokemonDetail = pokemonListApi.getPokemonDetail(pokemon.url)
                val imageUrl = pokemonDetail.sprites.other.home.front_default
                val type = pokemonDetail.types
                Pokemon(pokemon.name, pokemon.url, imageUrl, type)
            }
            // Devuelve un objeto PokemonList con los detalles mapeados
            PokemonList(pokemonList.count, pokemonList.next, pokemonList.previous, pokemonDetails)
        } catch (e: Exception) {
            // TODO Manejar el error
            PokemonList(0, null, null, emptyList())
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