package com.iqbalfauzi.kitchenstockmanager.domain

enum class CategoryType {
    PRODUCE, DAIRY, MEAT, PANTRY, SPICES, GRAINS, FRUITS, VEGETABLES, OTHER
}

sealed class StockStatus {
    object Fresh : StockStatus()
    object UseToday : StockStatus()
    object ExpiringSoon : StockStatus()
    object Expired : StockStatus()
    data class LowStock(val percentage: Int) : StockStatus()
}

data class PantryItem(
    val id: String,
    val name: String,
    val quantity: String,
    val category: CategoryType,
    val status: StockStatus,
    val expiryDate: String? = null,
    val purchaseDate: String? = null,
    val progress: Float? = null,
    val price: String? = null,
    val isChecked: Boolean = false
)
