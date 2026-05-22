package com.iqbalfauzi.kitchenstockmanager.presentation.shopping

import com.iqbalfauzi.kitchenstockmanager.domain.model.PantryItem
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewEffect
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewIntent
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewState

data class ShoppingState(
    val isLoading: Boolean = false,
    val suggestedItems: List<PantryItem> = emptyList(),
    val customItems: List<PantryItem> = emptyList(),
    val error: String? = null
) : ViewState

sealed interface ShoppingIntent : ViewIntent {
    data object LoadShoppingList : ShoppingIntent
    data object Refresh : ShoppingIntent
    data class OnToggleItem(val itemId: String) : ShoppingIntent
}

sealed interface ShoppingEffect : ViewEffect {
    data class ShowError(val message: String) : ShoppingEffect
}
