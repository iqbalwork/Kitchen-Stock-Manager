package com.iqbalfauzi.kitchenstockmanager.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InsertProductDto(
    @SerialName("name") val name: String,
    @SerialName("category_id") val categoryId: String,
    @SerialName("unit") val unit: String,
    @SerialName("min_stock_level") val minStockLevel: Double,
    @SerialName("barcode") val barcode: String? = null
)

@Serializable
data class InsertInventoryDto(
    @SerialName("product_id") val productId: String,
    @SerialName("quantity") val quantity: Double,
    @SerialName("expiry_date") val expiryDate: String?,
    @SerialName("storage_location_id") val storageLocationId: String? = null
)
