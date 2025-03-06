package com.example.pokedex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PokemonDao {
    // Operaciones para el historial de búsquedas
    @Insert
    suspend fun insertSearchHistory(searchHistory: SearchHistory)

    @Query("SELECT * FROM search_history ORDER BY timestamp DESC")
    suspend fun getSearchHistory(): List<SearchHistory>

    // Operaciones para la caché de Pokémon
    @Insert
    suspend fun insertPokemonCache(pokemonCache: PokemonCache)

    @Query("SELECT * FROM pokemon_cache WHERE name = :name")
    suspend fun getPokemonCache(name: String): PokemonCache?

    @Query("DELETE FROM search_history WHERE search_query = :query")
    suspend fun deleteSearchHistory(query: String)
}