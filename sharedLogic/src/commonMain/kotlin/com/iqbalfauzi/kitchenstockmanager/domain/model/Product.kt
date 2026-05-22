package com.iqbalfauzi.kitchenstockmanager.domain.model

data class Product(
    val id: String,
    val name: String,
    val categoryId: String,
    val barcode: String? = null,
    val unit: String,
    val minStockLevel: Double,
    val imageUrl: String? = null
)
