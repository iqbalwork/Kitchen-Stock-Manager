package com.iqbalfauzi.kitchenstockmanager.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class StorageLocation(
    val id: String,
    val name: String,
    val description: String? = null
)
