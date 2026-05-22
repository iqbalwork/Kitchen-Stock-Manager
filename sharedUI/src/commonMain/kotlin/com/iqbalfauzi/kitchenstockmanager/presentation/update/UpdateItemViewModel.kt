package com.iqbalfauzi.kitchenstockmanager.presentation.update

import androidx.lifecycle.viewModelScope
import com.iqbalfauzi.kitchenstockmanager.domain.repository.PantryRepository
import com.iqbalfauzi.kitchenstockmanager.presentation.BaseViewModel
import kotlinx.coroutines.launch

class UpdateItemViewModel(
    private val itemId: String,
    private val repository: PantryRepository
) : BaseViewModel<UpdateItemState, UpdateItemIntent, UpdateItemEffect>(UpdateItemState()) {

    override fun handleIntent(intent: UpdateItemIntent) {
        when (intent) {
            is UpdateItemIntent.OnNameChange -> setState { copy(itemName = intent.name) }
            is UpdateItemIntent.OnUpdateClick -> updateItem()
            is UpdateItemIntent.OnDeleteClick -> deleteItem()
        }
    }

    private fun updateItem() {
        // Implementation for updating item name if needed
        setEffect(UpdateItemEffect.NavigateBack)
    }

    private fun deleteItem() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            repository.deleteInventory(itemId).fold(
                onSuccess = {
                    setState { copy(isLoading = false) }
                    setEffect(UpdateItemEffect.NavigateBack)
                },
                onFailure = { error ->
                    setState { copy(isLoading = false, error = error.message) }
                    setEffect(UpdateItemEffect.ShowError(error.message ?: "Delete failed"))
                }
            )
        }
    }
}
