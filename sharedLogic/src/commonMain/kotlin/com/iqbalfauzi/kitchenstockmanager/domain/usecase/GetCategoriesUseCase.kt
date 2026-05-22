package com.iqbalfauzi.kitchenstockmanager.domain.usecase

import com.iqbalfauzi.kitchenstockmanager.domain.model.Category
import com.iqbalfauzi.kitchenstockmanager.domain.repository.PantryRepository

class GetCategoriesUseCase(private val repository: PantryRepository) {
    suspend operator fun invoke(): Result<List<Category>> {
        return try {
            Result.success(repository.getCategories())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
