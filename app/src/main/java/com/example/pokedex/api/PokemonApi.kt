package com.example.pokedex.api

import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.model.PokemonList
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET
    suspend fun getPokemonDetail(@Url url: String): PokemonDetail
}