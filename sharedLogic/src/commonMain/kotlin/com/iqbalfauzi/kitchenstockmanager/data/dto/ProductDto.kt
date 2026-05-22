package com.iqbalfauzi.kitchenstockmanager.data.dto

import com.iqbalfauzi.kitchenstockmanager.domain.model.Product
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("category_id") val categoryId: String,
    @SerialName("barcode") val barcode: String? = null,
    @SerialName("unit") val unit: String,
    @SerialName("min_stock_level") val minStockLevel: Double,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("user_id") val userId: String? = null
)

fun ProductDto.toDomain() = Product(
    id = id,
    name = name,
    categoryId = categoryId,
    barcode = barcode,
    unit = unit,
    minStockLevel = minStockLevel,
    imageUrl = imageUrl
)
