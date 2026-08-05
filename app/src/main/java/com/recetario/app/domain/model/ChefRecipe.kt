package com.recetario.app.domain.model

data class ChefRecipe(
    val id: String,
    val name: String,
    val category: String,
    // Ruta local (galería/CameraX) de la foto propia. Las recetas precargadas del
    // catálogo de la chef no traen imagen: la UI usa un ícono decorativo por categoría.
    val imagePath: String? = null,
    val ingredients: List<Ingredient>,
    val steps: List<String>,
    val prepTimeMinutes: Int,
    val difficulty: Difficulty,
    val isFavorite: Boolean = false,
    val isUserCreated: Boolean = false
)
