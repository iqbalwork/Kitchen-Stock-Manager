package com.iqbalfauzi.kitchenstockmanager.domain.usecase

import com.iqbalfauzi.kitchenstockmanager.domain.model.PantryItem
import com.iqbalfauzi.kitchenstockmanager.domain.repository.PantryRepository

class GetShoppingListUseCase(private val repository: PantryRepository) {
    suspend operator fun invoke(): Result<List<PantryItem>> {
        return try {
            // In a real app, logic would be more complex (e.g. products where stock < min_stock)
            Result.success(repository.getRunningLowItems())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
