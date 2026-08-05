package com.recetario.app.domain.model

data class Recipe(
    val id: String,
    val name: String,
    val thumbnailUrl: String,
    val category: String,
    val area: String,
    val notes: String = "",
    val photoPath: String? = null
)
