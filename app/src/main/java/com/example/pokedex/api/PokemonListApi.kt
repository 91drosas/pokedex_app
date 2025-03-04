package com.example.pokedex.api

import com.example.pokedex.model.PokemonList
import retrofit2.http.GET

interface PokemonListApi {
    @GET
    suspend fun getPokemonList(): PokemonList
}