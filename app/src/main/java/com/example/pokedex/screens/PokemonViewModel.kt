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
    private val _state = MutableStateFlow(emptyList<Pokemon>())
    val state : StateFlow<List<Pokemon>>
        get() = _state

    init {
        viewModelScope.launch {
            _state.value = repo.getPokemonList().results
            Log.d("pokemonViewModel", repo.getPokemonList().results.toString())
        }
    }
}