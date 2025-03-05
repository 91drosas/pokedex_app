package com.example.pokedex.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.Pokemon
import com.example.pokedex.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(
    private val repo: PokemonRepository
) : ViewModel() {
    private val _state = MutableStateFlow<List<Pokemon>>(emptyList())
    val state : StateFlow<List<Pokemon>>
        get() = _state

    private var offset = 0
    private val limit = 20

    init {
        loadPokemonList()
    }

    private fun loadPokemonList() {
        viewModelScope.launch {
            val pokemonList = repo.getPokemonList(limit, offset)
            _state.value = pokemonList
            Log.d("pokemonViewModel", pokemonList.toString())
        }
    }

    fun nextPage() {
        offset += limit
        loadPokemonList()
    }

    fun previousPage() {
        if (offset >= limit) {
            offset -= limit
            loadPokemonList()
        }
    }
}