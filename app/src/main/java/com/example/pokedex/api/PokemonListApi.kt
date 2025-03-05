package com.example.pokedex.api

import com.example.pokedex.model.PokemonList
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonListApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int, // Parámetros opcionales (si los necesitas)
        @Query("offset") offset: Int
    ): PokemonList
}