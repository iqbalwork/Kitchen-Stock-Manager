package com.iqbalfauzi.kitchenstockmanager.presentation.detail

import androidx.lifecycle.viewModelScope
import com.iqbalfauzi.kitchenstockmanager.domain.usecase.GetItemDetailUseCase
import com.iqbalfauzi.kitchenstockmanager.presentation.BaseViewModel
import kotlinx.coroutines.launch

class ItemDetailViewModel(
    private val itemId: String,
    private val getItemDetailUseCase: GetItemDetailUseCase
) : BaseViewModel<ItemDetailState, ItemDetailIntent, ItemDetailEffect>(ItemDetailState()) {

    init {
        handleIntent(ItemDetailIntent.LoadItem(itemId))
    }

    override fun handleIntent(intent: ItemDetailIntent) {
        when (intent) {
            is ItemDetailIntent.LoadItem -> loadItem(intent.itemId)
            is ItemDetailIntent.Refresh -> loadItem(itemId)
            is ItemDetailIntent.MarkAsUsed -> { /* Mark as used logic */ }
            is ItemDetailIntent.IncreaseQuantity -> { /* Increase quantity logic */ }
        }
    }

    private fun loadItem(id: String) {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            getItemDetailUseCase(id).fold(
                onSuccess = { item ->
                    setState { copy(isLoading = false, item = item) }
                },
                onFailure = { error ->
                    setState { copy(isLoading = false, error = error.message) }
                    setEffect(ItemDetailEffect.ShowError(error.message ?: "Unknown error"))
                }
            )
        }
    }
}
