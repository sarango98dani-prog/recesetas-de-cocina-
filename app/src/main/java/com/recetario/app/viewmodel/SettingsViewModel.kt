package com.recetario.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recetario.app.data.datastore.UnitSystem
import com.recetario.app.data.datastore.UserPreferencesDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val preferences: UserPreferencesDataStore) : ViewModel() {

    val unitSystem: StateFlow<UnitSystem> = preferences.unitSystem
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UnitSystem.METRIC)

    fun setUnitSystem(unitSystem: UnitSystem) {
        viewModelScope.launch { preferences.setUnitSystem(unitSystem) }
    }
}
