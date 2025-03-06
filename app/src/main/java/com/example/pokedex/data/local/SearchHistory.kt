package com.example.pokedex.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_history")
data class SearchHistory(
    @PrimaryKey val search_query: String, // Texto buscado
    val timestamp: Long // Fecha y hora de la búsqueda
)