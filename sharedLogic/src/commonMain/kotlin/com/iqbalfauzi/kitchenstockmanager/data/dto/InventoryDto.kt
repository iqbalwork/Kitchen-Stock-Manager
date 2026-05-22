package com.iqbalfauzi.kitchenstockmanager.data.dto

import com.iqbalfauzi.kitchenstockmanager.domain.model.Inventory
import com.iqbalfauzi.kitchenstockmanager.domain.model.PantryItem
import com.iqbalfauzi.kitchenstockmanager.domain.model.StockStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InventoryDto(
    @SerialName("id") val id: String,
    @SerialName("product_id") val productId: String,
    @SerialName("storage_location_id") val storageLocationId: String? = null,
    @SerialName("quantity") val quantity: Double,
    @SerialName("expiry_date") val expiryDate: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null,
    // Joined data
    @SerialName("products") val product: ProductDto? = null
)

fun InventoryDto.toDomain() = Inventory(
    id = id,
    productId = productId,
    storageLocationId = storageLocationId,
    quantity = quantity,
    expiryDate = expiryDate,
    updatedAt = updatedAt
)

fun InventoryDto.toPantryItem() = PantryItem(
    id = id,
    productName = product?.name ?: "Unknown",
    quantity = quantity,
    unit = product?.unit ?: "",
    categoryName = "Pantry", // Should ideally join Category too
    status = StockStatus.Fresh, // Logic to determine based on expiry
    expiryDate = expiryDate,
    progress = 1.0f
)
