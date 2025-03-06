package com.example.pokedex.screens

import PokemonDetail
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _pokemonDetail = MutableStateFlow<PokemonDetail?>(null)
    val pokemonDetail: StateFlow<PokemonDetail?> get() = _pokemonDetail

    fun loadPokemonDetail(pokemonName: String) {
        viewModelScope.launch {
            try {
                val detail = repository.getPokemonDetail(pokemonName)
                _pokemonDetail.value = detail
            } catch (e: Exception) {
                // Manejar el error (por ejemplo, mostrar un mensaje de error)
            }
        }
    }
}