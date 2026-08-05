package com.recetario.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_recipes")
data class RecipeEntity(
    @PrimaryKey val mealId: String,
    val name: String,
    val thumbnailUrl: String,
    val category: String,
    val area: String,
    val notes: String = "",
    val photoPath: String? = null
)
