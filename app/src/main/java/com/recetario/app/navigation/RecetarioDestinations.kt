package com.recetario.app.navigation

object RecetarioDestinations {
    const val SPLASH = "splash"
    const val HOME = "home"
    const val RECIPE_LIST = "recipe_list"
    const val FAVORITES = "favorites"
    const val SETTINGS = "settings"
    const val CHEF_RECIPES = "chef_recipes"
    const val ADD_CHEF_RECIPE = "add_chef_recipe"
    const val ADD_CHEF_RECIPE_CAMERA = "add_chef_recipe_camera"

    private const val RECIPE_DETAIL_ROUTE = "recipe_detail"
    const val MEAL_ID_ARG = "mealId"
    const val RECIPE_DETAIL = "$RECIPE_DETAIL_ROUTE/{$MEAL_ID_ARG}"

    private const val CAMERA_ROUTE = "camera"
    const val CAMERA = "$CAMERA_ROUTE/{$MEAL_ID_ARG}"

    private const val CHEF_RECIPE_DETAIL_ROUTE = "chef_recipe_detail"
    const val CHEF_RECIPE_ID_ARG = "chefRecipeId"
    const val CHEF_RECIPE_DETAIL = "$CHEF_RECIPE_DETAIL_ROUTE/{$CHEF_RECIPE_ID_ARG}"

    private const val CAMERA_CHEF_ROUTE = "camera_chef"
    const val CAMERA_CHEF = "$CAMERA_CHEF_ROUTE/{$CHEF_RECIPE_ID_ARG}"

    fun recipeDetail(mealId: String) = "$RECIPE_DETAIL_ROUTE/$mealId"
    fun camera(mealId: String) = "$CAMERA_ROUTE/$mealId"
    fun chefRecipeDetail(id: String) = "$CHEF_RECIPE_DETAIL_ROUTE/$id"
    fun cameraChef(id: String) = "$CAMERA_CHEF_ROUTE/$id"
}
