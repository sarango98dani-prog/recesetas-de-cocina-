package com.recetario.app.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "user_preferences")

class UserPreferencesDataStore(private val context: Context) {

    private object Keys {
        val UNIT_SYSTEM = stringPreferencesKey("unit_system")
    }

    val unitSystem: Flow<UnitSystem> = context.dataStore.data.map { prefs ->
        val stored = prefs[Keys.UNIT_SYSTEM]
        UnitSystem.entries.find { it.name == stored } ?: UnitSystem.METRIC
    }

    suspend fun setUnitSystem(unitSystem: UnitSystem) {
        context.dataStore.edit { prefs ->
            prefs[Keys.UNIT_SYSTEM] = unitSystem.name
        }
    }
}
