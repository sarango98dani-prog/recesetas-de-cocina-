package com.recetario.app.domain.usecase

import com.recetario.app.data.repository.UserPreferencesRepository
import com.recetario.app.domain.model.UnitSystem
import kotlinx.coroutines.flow.Flow

class GetUnitSystemUseCase(private val repository: UserPreferencesRepository) {
    operator fun invoke(): Flow<UnitSystem> = repository.unitSystem
}
