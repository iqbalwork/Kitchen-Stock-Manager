package com.iqbalfauzi.kitchenstockmanager.domain.usecase

import com.iqbalfauzi.kitchenstockmanager.domain.model.PantryItem
import com.iqbalfauzi.kitchenstockmanager.domain.repository.PantryRepository

class GetItemDetailUseCase(private val repository: PantryRepository) {
    suspend operator fun invoke(itemId: String): Result<PantryItem> {
        return try {
            val item = repository.getPantryItems().find { it.id == itemId }
            if (item != null) Result.success(item)
            else Result.failure(Exception("Item not found"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
