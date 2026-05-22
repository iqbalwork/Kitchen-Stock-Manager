package com.iqbalfauzi.kitchenstockmanager.data.dto

import com.iqbalfauzi.kitchenstockmanager.domain.model.Category
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("icon") val icon: String,
    @SerialName("user_id") val userId: String? = null
)

fun CategoryDto.toDomain() = Category(
    id = id,
    name = name,
    icon = icon
)
