package com.recetario.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recetario.app.domain.model.UnitSystem
import com.recetario.app.domain.usecase.GetUnitSystemUseCase
import com.recetario.app.domain.usecase.SetUnitSystemUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(
    getUnitSystemUseCase: GetUnitSystemUseCase,
    private val setUnitSystemUseCase: SetUnitSystemUseCase
) : ViewModel() {

    val unitSystem: StateFlow<UnitSystem> = getUnitSystemUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), UnitSystem.METRIC)

    fun setUnitSystem(unitSystem: UnitSystem) {
        viewModelScope.launch { setUnitSystemUseCase(unitSystem) }
    }
}
