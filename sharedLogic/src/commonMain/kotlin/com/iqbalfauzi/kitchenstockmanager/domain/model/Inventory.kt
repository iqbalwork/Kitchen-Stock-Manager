package com.iqbalfauzi.kitchenstockmanager.domain.model

data class Inventory(
    val id: String,
    val productId: String,
    val storageLocationId: String? = null,
    val quantity: Double,
    val expiryDate: String? = null,
    val updatedAt: String? = null
)

sealed class StockStatus {
    data class LowStock(val percentage: Int) : StockStatus()
    object ExpiringSoon : StockStatus()
    object UseToday : StockStatus()
    object Fresh : StockStatus()
}

// UI helper model that combines Product and Inventory info
data class PantryItem(
    val id: String,
    val productName: String,
    val quantity: Double,
    val unit: String,
    val categoryName: String,
    val status: StockStatus,
    val expiryDate: String? = null,
    val progress: Float? = null,
    val price: String? = null,
    val isChecked: Boolean = false
) {
    // For backward compatibility or easy access in Swift
    val name: String get() = productName
    val category: CategoryHelper get() = CategoryHelper(categoryName)
}

data class CategoryHelper(val name: String)
