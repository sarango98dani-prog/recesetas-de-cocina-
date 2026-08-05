package com.recetario.app.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface MealApiService {

    @GET("search.php")
    suspend fun searchMeals(@Query("s") name: String): MealsResponseDto
}
