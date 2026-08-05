package com.recetario.app.navigation

object RecetarioDestinations {
    const val SPLASH = "splash"
    const val RECIPE_LIST = "recipe_list"
    const val FAVORITES = "favorites"
    const val SETTINGS = "settings"

    private const val RECIPE_DETAIL_ROUTE = "recipe_detail"
    const val MEAL_ID_ARG = "mealId"
    const val RECIPE_DETAIL = "$RECIPE_DETAIL_ROUTE/{$MEAL_ID_ARG}"

    private const val CAMERA_ROUTE = "camera"
    const val CAMERA = "$CAMERA_ROUTE/{$MEAL_ID_ARG}"

    fun recipeDetail(mealId: String) = "$RECIPE_DETAIL_ROUTE/$mealId"
    fun camera(mealId: String) = "$CAMERA_ROUTE/$mealId"
}
