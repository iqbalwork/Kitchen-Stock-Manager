package com.iqbalfauzi.kitchenstockmanager.presentation.inventory

import com.iqbalfauzi.kitchenstockmanager.domain.model.PantryItem
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewEffect
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewIntent
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewState

data class InventoryState(
    val isLoading: Boolean = false,
    val items: List<PantryItem> = emptyList(),
    val selectedFilter: String = "All Items",
    val error: String? = null
) : ViewState

sealed interface InventoryIntent : ViewIntent {
    data object LoadInventory : InventoryIntent
    data object Refresh : InventoryIntent
    data class OnFilterSelected(val filter: String) : InventoryIntent
    data class OnItemClick(val itemId: String) : InventoryIntent
}

sealed interface InventoryEffect : ViewEffect {
    data class NavigateToDetail(val itemId: String) : InventoryEffect
    data class ShowError(val message: String) : InventoryEffect
}
