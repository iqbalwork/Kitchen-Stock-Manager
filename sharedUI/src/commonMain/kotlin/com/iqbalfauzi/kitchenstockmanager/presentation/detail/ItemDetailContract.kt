package com.iqbalfauzi.kitchenstockmanager.presentation.detail

import com.iqbalfauzi.kitchenstockmanager.domain.model.PantryItem
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewEffect
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewIntent
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewState

data class ItemDetailState(
    val isLoading: Boolean = false,
    val item: PantryItem? = null,
    val error: String? = null
) : ViewState

sealed interface ItemDetailIntent : ViewIntent {
    data class LoadItem(val itemId: String) : ItemDetailIntent
    object Refresh : ItemDetailIntent
    object MarkAsUsed : ItemDetailIntent
    object IncreaseQuantity : ItemDetailIntent
}

sealed interface ItemDetailEffect : ViewEffect {
    data class ShowError(val message: String) : ItemDetailEffect
    object NavigateBack : ItemDetailEffect
}
