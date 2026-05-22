package com.iqbalfauzi.kitchenstockmanager.presentation.shopping

import androidx.lifecycle.viewModelScope
import com.iqbalfauzi.kitchenstockmanager.domain.usecase.GetShoppingListUseCase
import com.iqbalfauzi.kitchenstockmanager.presentation.BaseViewModel
import kotlinx.coroutines.launch

class ShoppingViewModel(
    private val getShoppingListUseCase: GetShoppingListUseCase
) : BaseViewModel<ShoppingState, ShoppingIntent, ShoppingEffect>(ShoppingState()) {

    init {
        handleIntent(ShoppingIntent.LoadShoppingList)
    }

    override fun handleIntent(intent: ShoppingIntent) {
        when (intent) {
            is ShoppingIntent.LoadShoppingList -> loadShoppingList()
            is ShoppingIntent.Refresh -> loadShoppingList()
            is ShoppingIntent.OnToggleItem -> { /* Toggle check logic */ }
        }
    }

    private fun loadShoppingList() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            getShoppingListUseCase().fold(
                onSuccess = { items ->
                    setState { 
                        copy(
                            isLoading = false, 
                            suggestedItems = items,
                            customItems = emptyList() // Example separation
                        ) 
                    }
                },
                onFailure = { error ->
                    setState { copy(isLoading = false, error = error.message) }
                    setEffect(ShoppingEffect.ShowError(error.message ?: "Unknown error"))
                }
            )
        }
    }
}
