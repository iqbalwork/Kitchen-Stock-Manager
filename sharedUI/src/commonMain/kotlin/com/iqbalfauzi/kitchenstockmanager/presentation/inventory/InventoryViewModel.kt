package com.iqbalfauzi.kitchenstockmanager.presentation.inventory

import androidx.lifecycle.viewModelScope
import com.iqbalfauzi.kitchenstockmanager.domain.usecase.GetInventoryItemsUseCase
import com.iqbalfauzi.kitchenstockmanager.presentation.BaseViewModel
import kotlinx.coroutines.launch

class InventoryViewModel(
    private val getInventoryItemsUseCase: GetInventoryItemsUseCase
) : BaseViewModel<InventoryState, InventoryIntent, InventoryEffect>(InventoryState()) {

    init {
        handleIntent(InventoryIntent.LoadInventory)
    }

    override fun handleIntent(intent: InventoryIntent) {
        when (intent) {
            is InventoryIntent.LoadInventory -> loadInventory()
            is InventoryIntent.Refresh -> loadInventory()
            is InventoryIntent.OnFilterSelected -> setState { copy(selectedFilter = intent.filter) }
            is InventoryIntent.OnItemClick -> setEffect(InventoryEffect.NavigateToDetail(intent.itemId))
        }
    }

    private fun loadInventory() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            getInventoryItemsUseCase().fold(
                onSuccess = { items ->
                    setState { copy(isLoading = false, items = items) }
                },
                onFailure = { error ->
                    setState { copy(isLoading = false, error = error.message) }
                    setEffect(InventoryEffect.ShowError(error.message ?: "Unknown error"))
                }
            )
        }
    }
}
