package com.example.pokedex.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.model.PokemonDetail
import com.example.pokedex.model.PokemonSelectedDetail
import com.example.pokedex.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
) : ViewModel() {

    private val _pokemonDetail = MutableStateFlow<PokemonSelectedDetail?>(null)
    val pokemonDetail: StateFlow<PokemonSelectedDetail?> get() = _pokemonDetail

    fun loadPokemonDetail(pokemonId: String) {
        viewModelScope.launch {
            val detail = repository.getPokemonDetail(pokemonId)
            _pokemonDetail.value = detail
        }
    }
}