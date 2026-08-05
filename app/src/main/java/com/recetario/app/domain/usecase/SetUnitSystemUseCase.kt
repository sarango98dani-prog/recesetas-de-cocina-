package com.recetario.app.domain.usecase

import com.recetario.app.data.repository.UserPreferencesRepository
import com.recetario.app.domain.model.UnitSystem

class SetUnitSystemUseCase(private val repository: UserPreferencesRepository) {
    suspend operator fun invoke(unitSystem: UnitSystem) = repository.setUnitSystem(unitSystem)
}
