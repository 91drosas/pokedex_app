package com.example.pokedex.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokedex.screens.PokemonDetailScreen
import com.example.pokedex.screens.PokemonScreen
import com.example.pokedex.utils.AppRoutes

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    // Definición de las rutas de navegación
    NavHost(
        navController = navController,
        startDestination = AppRoutes.POKEMON_LIST
    ) {
        // Pantalla de la lista de Pokémon
        composable(route = AppRoutes.POKEMON_LIST) {
            PokemonScreen(navController = navController)
        }

        // Pantalla de detalles del Pokémon
        composable(
            route = AppRoutes.POKEMON_DETAIL + "/{pokemonName}"
        ) { backStackEntry ->
            val pokemonName = backStackEntry.arguments?.getString("pokemonName") ?: ""
            if (pokemonName != null) {
                PokemonDetailScreen(pokemonName = pokemonName)
            } else {
                // TODO Manejar el error
            }
        }
    }
}