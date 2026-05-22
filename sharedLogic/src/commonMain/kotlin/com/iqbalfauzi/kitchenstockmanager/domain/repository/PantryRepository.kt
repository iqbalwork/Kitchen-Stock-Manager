package com.iqbalfauzi.kitchenstockmanager.domain.repository

import com.iqbalfauzi.kitchenstockmanager.domain.model.*

interface PantryRepository {
    suspend fun getCategories(): List<Category>
    suspend fun getStorageLocations(): List<StorageLocation>
    suspend fun getPantryItems(): List<PantryItem>
    suspend fun getExpiringSoonItems(): List<PantryItem>
    suspend fun getRunningLowItems(): List<PantryItem>
    suspend fun addProduct(product: Product, quantity: Double, expiryDate: String?, storageLocationId: String? = null): Result<Unit>
    suspend fun updateInventory(inventoryId: String, quantity: Double): Result<Unit>
    suspend fun deleteInventory(inventoryId: String): Result<Unit>
}
