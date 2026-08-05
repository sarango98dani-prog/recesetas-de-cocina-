package com.recetario.app.data.remote

import com.recetario.app.domain.model.Recipe

fun MealDto.toDomain(): Recipe = Recipe(
    id = idMeal,
    name = strMeal,
    thumbnailUrl = strMealThumb.orEmpty(),
    category = strCategory.orEmpty(),
    area = strArea.orEmpty()
)
