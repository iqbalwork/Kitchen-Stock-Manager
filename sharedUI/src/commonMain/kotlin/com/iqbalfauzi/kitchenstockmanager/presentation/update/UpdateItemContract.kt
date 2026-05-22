package com.iqbalfauzi.kitchenstockmanager.presentation.update

import com.iqbalfauzi.kitchenstockmanager.presentation.ViewEffect
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewIntent
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewState

data class UpdateItemState(
    val itemName: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
) : ViewState

sealed interface UpdateItemIntent : ViewIntent {
    data class OnNameChange(val name: String) : UpdateItemIntent
    data object OnUpdateClick : UpdateItemIntent
    data object OnDeleteClick : UpdateItemIntent
}

sealed interface UpdateItemEffect : ViewEffect {
    data object NavigateBack : UpdateItemEffect
    data class ShowError(val message: String) : UpdateItemEffect
}
