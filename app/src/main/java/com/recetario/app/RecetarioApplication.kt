package com.recetario.app

import android.app.Application
import com.recetario.app.data.datastore.UserPreferencesDataStore
import com.recetario.app.data.local.AppDatabase

class RecetarioApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }
    val userPreferences: UserPreferencesDataStore by lazy { UserPreferencesDataStore(this) }
}
