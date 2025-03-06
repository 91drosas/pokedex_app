package com.example.pokedex

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.screens.PokemonDetailScreen
import com.example.pokedex.screens.PokemonScreen
import com.example.pokedex.ui.theme.PokedexTheme
import com.example.pokedex.utils.AppRoutes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = AppRoutes.POKEMON_LIST
            ) {
                // Pantalla principal (lista de Pokémon)
                composable(AppRoutes.POKEMON_LIST) {
                    PokemonScreen(navController)
                }

                // Pantalla de detalles del Pokémon
                composable(AppRoutes.POKEMON_DETAIL) { backStackEntry ->
                    val pokemonId = backStackEntry.arguments?.getString("pokemonId") ?: ""
                    PokemonDetailScreen(pokemonId, navController)
                }
            }
        }
    }
}