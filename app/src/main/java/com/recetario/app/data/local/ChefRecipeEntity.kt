package com.recetario.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chef_recipes")
data class ChefRecipeEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val imagePath: String?,
    // Listas serializadas a JSON (ver ChefRecipeRepository) para evitar TypeConverters extra.
    val ingredientsJson: String,
    val stepsJson: String,
    val prepTimeMinutes: Int,
    val difficulty: String,
    val isFavorite: Boolean,
    val isUserCreated: Boolean
)
