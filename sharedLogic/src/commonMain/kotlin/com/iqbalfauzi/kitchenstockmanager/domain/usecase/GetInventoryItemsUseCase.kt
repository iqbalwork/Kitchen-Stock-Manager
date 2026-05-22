package com.iqbalfauzi.kitchenstockmanager.domain.usecase

import com.iqbalfauzi.kitchenstockmanager.domain.model.PantryItem
import com.iqbalfauzi.kitchenstockmanager.domain.repository.PantryRepository

class GetInventoryItemsUseCase(private val repository: PantryRepository) {
    suspend operator fun invoke(): Result<List<PantryItem>> {
        return try {
            Result.success(repository.getPantryItems())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
