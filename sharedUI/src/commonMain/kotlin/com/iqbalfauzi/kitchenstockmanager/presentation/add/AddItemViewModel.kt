package com.iqbalfauzi.kitchenstockmanager.presentation.add

import androidx.lifecycle.viewModelScope
import com.iqbalfauzi.kitchenstockmanager.domain.usecase.AddItemParams
import com.iqbalfauzi.kitchenstockmanager.domain.usecase.AddPantryItemUseCase
import com.iqbalfauzi.kitchenstockmanager.domain.usecase.GetCategoriesUseCase
import com.iqbalfauzi.kitchenstockmanager.domain.usecase.GetStorageLocationsUseCase
import com.iqbalfauzi.kitchenstockmanager.presentation.BaseViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AddItemViewModel(
    private val addPantryItemUseCase: AddPantryItemUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getStorageLocationsUseCase: GetStorageLocationsUseCase
) : BaseViewModel<AddItemState, AddItemIntent, AddItemEffect>(AddItemState()) {

    init {
        handleIntent(AddItemIntent.LoadInitialData)
    }

    override fun handleIntent(intent: AddItemIntent) {
        when (intent) {
            is AddItemIntent.LoadInitialData -> loadInitialData()
            is AddItemIntent.OnNameChange -> setState { copy(itemName = intent.name) }
            is AddItemIntent.OnCategorySelect -> setState { copy(categoryId = intent.categoryId) }
            is AddItemIntent.OnStorageSelect -> setState { copy(storageLocationId = intent.storageId) }
            is AddItemIntent.OnQuantityChange -> setState { copy(quantity = intent.quantity) }
            is AddItemIntent.OnUnitChange -> setState { copy(unit = intent.unit) }
            is AddItemIntent.OnPurchaseDateChange -> setState { copy(purchaseDate = intent.date) }
            is AddItemIntent.OnExpiryDateChange -> setState { copy(expiryDate = intent.date) }
            is AddItemIntent.OnSaveClick -> saveItem()
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            val categoriesDeferred = async { getCategoriesUseCase() }
            val storageDeferred = async { getStorageLocationsUseCase() }

            val categoriesResult = categoriesDeferred.await()
            val storageResult = storageDeferred.await()

            categoriesResult.onSuccess { categories ->
                setState { copy(categories = categories) }
                if (currentState.categoryId.isEmpty() && categories.isNotEmpty()) {
                    setState { copy(categoryId = categories.first().id) }
                }
            }

            storageResult.onSuccess { locations ->
                setState { copy(storageLocations = locations) }
                if (currentState.storageLocationId == null && locations.isNotEmpty()) {
                    setState { copy(storageLocationId = locations.first().id) }
                }
            }
        }
    }

    private fun saveItem() {
        val quantity = currentState.quantity.toDoubleOrNull() ?: 0.0
        if (currentState.itemName.isBlank()) {
            setEffect(AddItemEffect.ShowError("Item name cannot be empty"))
            return
        }

        if (currentState.categoryId.isBlank()) {
            setEffect(AddItemEffect.ShowError("Please select a category"))
            return
        }

        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val params = AddItemParams(
                name = currentState.itemName,
                categoryId = currentState.categoryId,
                quantity = quantity,
                unit = currentState.unit,
                expiryDate = currentState.expiryDate.ifBlank { null },
                storageLocationId = currentState.storageLocationId
            )
            addPantryItemUseCase(params).fold(
                onSuccess = {
                    setState { copy(isLoading = false) }
                    setEffect(AddItemEffect.NavigateBack)
                },
                onFailure = { error ->
                    setState { copy(isLoading = false, error = error.message) }
                    setEffect(AddItemEffect.ShowError(error.message ?: "Failed to save item"))
                }
            )
        }
    }
}
