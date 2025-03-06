package com.example.pokedex.repository

import com.example.pokedex.api.PokemonApi
import com.example.pokedex.data.local.PokemonCache
import com.example.pokedex.data.local.PokemonDatabase
import com.example.pokedex.data.local.SearchHistory
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.model.PokemonList
import com.example.pokedex.utils.Constants.toPokemonDetailUrl
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    private val pokemonListApi: PokemonApi,
    private val database: PokemonDatabase
) {
    private val dao = database.pokemonDao()

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

    suspend fun searchPokemon(query: String): PokemonDetail? {
        return try {
            // Convertir la consulta a minúsculas
            val lowercaseQuery = query.lowercase().trim()
            // Verificar si la búsqueda está en caché
            val cachedPokemon = dao.getPokemonCache(lowercaseQuery)
            if (cachedPokemon != null) {
                // Convertir el JSON almacenado a PokemonDetail
                return deserializePokemonDetail(cachedPokemon.data)
            }

            // Si no está en caché, hacer la llamada a la API
            val url = lowercaseQuery.toPokemonDetailUrl()
            val response = pokemonListApi.getPokemonDetail(url)

            // Guardar en caché
            dao.insertPokemonCache(PokemonCache(lowercaseQuery, serializePokemonDetail(response)))

            // Guardar en el historial de búsquedas
            dao.insertSearchHistory(SearchHistory(lowercaseQuery, System.currentTimeMillis()))

            response
        } catch (e: Exception) {
            // Manejar el error
            null
        }
    }

    suspend fun getSearchHistory(): List<SearchHistory> {
        return dao.getSearchHistory()
    }

    // Funciones para serializar/deserializar (puedes usar Gson o Kotlinx.serialization)
    private fun serializePokemonDetail(pokemonDetail: PokemonDetail): String {
        return Gson().toJson(pokemonDetail)
    }

    private fun deserializePokemonDetail(json: String): PokemonDetail {
        return Gson().fromJson(json, PokemonDetail::class.java)
    }

    suspend fun deleteSearchHistory(query: String) {
        dao.deleteSearchHistory(query)
    }
}