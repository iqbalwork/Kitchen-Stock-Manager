package com.iqbalfauzi.kitchenstockmanager.presentation.add

import com.iqbalfauzi.kitchenstockmanager.domain.model.Category
import com.iqbalfauzi.kitchenstockmanager.domain.model.StorageLocation
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewEffect
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewIntent
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewState

data class AddItemState(
    val itemName: String = "",
    val categoryId: String = "",
    val categories: List<Category> = emptyList(),
    val storageLocationId: String? = null,
    val storageLocations: List<StorageLocation> = emptyList(),
    val quantity: String = "1",
    val unit: String = "Pieces",
    val purchaseDate: String = "",
    val expiryDate: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) : ViewState

sealed interface AddItemIntent : ViewIntent {
    data object LoadInitialData : AddItemIntent
    data class OnNameChange(val name: String) : AddItemIntent
    data class OnCategorySelect(val categoryId: String) : AddItemIntent
    data class OnStorageSelect(val storageId: String) : AddItemIntent
    data class OnQuantityChange(val quantity: String) : AddItemIntent
    data class OnUnitChange(val unit: String) : AddItemIntent
    data class OnPurchaseDateChange(val date: String) : AddItemIntent
    data class OnExpiryDateChange(val date: String) : AddItemIntent
    data object OnSaveClick : AddItemIntent
}

sealed interface AddItemEffect : ViewEffect {
    data object NavigateBack : AddItemEffect
    data class ShowError(val message: String) : AddItemEffect
}
