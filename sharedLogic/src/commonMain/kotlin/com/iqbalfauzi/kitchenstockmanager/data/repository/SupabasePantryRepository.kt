package com.iqbalfauzi.kitchenstockmanager.data.repository

import com.iqbalfauzi.kitchenstockmanager.data.dto.CategoryDto
import com.iqbalfauzi.kitchenstockmanager.data.dto.InventoryDto
import com.iqbalfauzi.kitchenstockmanager.data.dto.InsertProductDto
import com.iqbalfauzi.kitchenstockmanager.data.dto.InsertInventoryDto
import com.iqbalfauzi.kitchenstockmanager.data.dto.ProductDto
import com.iqbalfauzi.kitchenstockmanager.data.dto.toDomain
import com.iqbalfauzi.kitchenstockmanager.data.dto.toPantryItem
import com.iqbalfauzi.kitchenstockmanager.domain.model.Category
import com.iqbalfauzi.kitchenstockmanager.domain.model.PantryItem
import com.iqbalfauzi.kitchenstockmanager.domain.model.Product
import com.iqbalfauzi.kitchenstockmanager.domain.model.StorageLocation
import com.iqbalfauzi.kitchenstockmanager.domain.repository.PantryRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class SupabasePantryRepository(
    private val client: SupabaseClient
) : PantryRepository {

    override suspend fun getCategories(): List<Category> {
        return try {
            client.from("categories")
                .select()
                .decodeList<CategoryDto>()
                .map { it.toDomain() }
        } catch (e: Exception) {
            println("Error fetching categories: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getStorageLocations(): List<StorageLocation> {
        return try {
            client.from("storage_locations")
                .select()
                .decodeList<StorageLocation>()
        } catch (e: Exception) {
            println("Error fetching storage locations: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getPantryItems(): List<PantryItem> {
        return try {
            client.from("inventory")
                .select(Columns.raw("*, products(*)"))
                .decodeList<InventoryDto>()
                .map { it.toPantryItem() }
        } catch (e: Exception) {
            println("Error fetching pantry items: ${e.message}")
            emptyList()
        }
    }

    override suspend fun getExpiringSoonItems(): List<PantryItem> {
        // Mocking logic for now, in real app would filter by date
        return getPantryItems().take(3)
    }

    override suspend fun getRunningLowItems(): List<PantryItem> {
        // Mocking logic for now
        return getPantryItems().filter { it.quantity < 5.0 }
    }

    override suspend fun addProduct(product: Product, quantity: Double, expiryDate: String?, storageLocationId: String?): Result<Unit> {
        return try {
            // 1. Insert product
            val insertProductDto = InsertProductDto(
                name = product.name,
                categoryId = product.categoryId,
                unit = product.unit,
                minStockLevel = product.minStockLevel,
                barcode = product.barcode
            )
            val productDto = client.from("products").insert(insertProductDto) {
                select()
            }.decodeSingle<ProductDto>()

            // 2. Insert into inventory
            val insertInventoryDto = InsertInventoryDto(
                productId = productDto.id,
                quantity = quantity,
                expiryDate = expiryDate,
                storageLocationId = storageLocationId
            )
            client.from("inventory").insert(insertInventoryDto)

            Result.success(Unit)
        } catch (e: Exception) {
            println("Error adding product: ${e.message}")
            Result.failure(e)
        }
    }

    override suspend fun updateInventory(inventoryId: String, quantity: Double): Result<Unit> {
        return try {
            client.from("inventory")
                .update(
                    {
                        set("quantity", quantity)
                    }
                ) {
                    filter {
                        eq("id", inventoryId)
                    }
                }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteInventory(inventoryId: String): Result<Unit> {
        return try {
            client.from("inventory")
                .delete {
                    filter {
                        eq("id", inventoryId)
                    }
                }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
