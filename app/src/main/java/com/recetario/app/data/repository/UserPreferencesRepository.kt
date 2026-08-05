package com.recetario.app.data.repository

import com.recetario.app.data.datastore.UserPreferencesDataStore
import com.recetario.app.domain.model.UnitSystem
import kotlinx.coroutines.flow.Flow

class UserPreferencesRepository(private val dataStore: UserPreferencesDataStore) {

    val unitSystem: Flow<UnitSystem> = dataStore.unitSystem

    suspend fun setUnitSystem(unitSystem: UnitSystem) {
        dataStore.setUnitSystem(unitSystem)
    }
}
