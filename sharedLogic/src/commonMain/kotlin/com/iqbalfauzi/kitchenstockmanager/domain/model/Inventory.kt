package com.iqbalfauzi.kitchenstockmanager.domain.model

data class Inventory(
    val id: String,
    val productId: String,
    val storageLocationId: String? = null,
    val quantity: Double,
    val expiryDate: String? = null,
    val updatedAt: String? = null
)

// UI helper model that combines Product and Inventory info
data class PantryItem(
    val id: String,
    val productName: String,
    val quantity: Double,
    val unit: String,
    val categoryName: String,
    val status: String, // e.g. "Fresh", "Expiring Soon"
    val expiryDate: String? = null,
    val progress: Float = 1.0f,
    val price: String? = null
)
