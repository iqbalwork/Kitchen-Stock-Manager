package com.iqbalfauzi.kitchenstockmanager.domain.usecase

import com.iqbalfauzi.kitchenstockmanager.domain.model.Product
import com.iqbalfauzi.kitchenstockmanager.domain.repository.PantryRepository

data class AddItemParams(
    val name: String,
    val categoryId: String,
    val quantity: Double,
    val unit: String,
    val expiryDate: String?,
    val storageLocationId: String? = null
)

class AddPantryItemUseCase(private val repository: PantryRepository) {
    suspend operator fun invoke(params: AddItemParams): Result<Unit> {
        return try {
            // Mapping params to Product model for the repository
            val product = Product(
                id = "", // repository handles ID generation or it's a new product
                name = params.name,
                categoryId = params.categoryId,
                unit = params.unit,
                minStockLevel = 0.0 // Default
            )
            repository.addProduct(product, params.quantity, params.expiryDate, params.storageLocationId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
