package com.example.pokedex.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.data.local.SearchHistory
import com.example.pokedex.model.Pokemon
import com.example.pokedex.model.PokemonDetail
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
    private var hasNextPage = true

    private val _searchResult = MutableStateFlow<PokemonDetail?>(null)
    val searchResult: StateFlow<PokemonDetail?> get() = _searchResult

    private val _searchHistory = MutableStateFlow<List<SearchHistory>>(emptyList())
    val searchHistory: StateFlow<List<SearchHistory>> get() = _searchHistory

    init {
        loadPokemonList()
    }

    private fun loadPokemonList() {
        viewModelScope.launch {
            try {
                val pokemonListResponse = repo.getPokemonList(limit, offset)
                _state.value = pokemonListResponse.results
                hasNextPage = pokemonListResponse.next != null
            } catch (e: Exception) {
                // TODO Manejar el error
                Log.e("PokemonViewModel", "Error loading Pokémon list", e)
            }
        }
    }

    fun nextPage() {
        if (hasNextPage) {
            offset += limit
            loadPokemonList()
        }
    }

    fun previousPage() {
        if (offset >= limit) {
            offset -= limit
            loadPokemonList()
        }
    }

    fun searchPokemon(query: String) {
        viewModelScope.launch {
            _searchResult.value = repo.searchPokemon(query)
            _searchHistory.value = repo.getSearchHistory() // Actualizar el historial después de buscar
        }
    }

    fun loadSearchHistory() {
        viewModelScope.launch {
            _searchHistory.value = repo.getSearchHistory()
        }
    }

    fun deleteSearchHistory(query: String) {
        viewModelScope.launch {
            repo.deleteSearchHistory(query)
            loadSearchHistory() // Recargar el historial después de eliminar
        }
    }
}