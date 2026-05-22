package com.iqbalfauzi.kitchenstockmanager.di

import com.iqbalfauzi.kitchenstockmanager.initNapier
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.iqbalfauzi.kitchenstockmanager.domain.usecase.*
import com.iqbalfauzi.kitchenstockmanager.domain.model.*

fun initKoinIos() {
    initKoinLogic()
    initNapier()
}

class UseCaseHelper : KoinComponent {
    private val getDashboardDataUseCase: GetDashboardDataUseCase by inject()
    private val getInventoryItemsUseCase: GetInventoryItemsUseCase by inject()
    private val getShoppingListUseCase: GetShoppingListUseCase by inject()
    private val addPantryItemUseCase: AddPantryItemUseCase by inject()
    private val getCategoriesUseCase: GetCategoriesUseCase by inject()
    private val getStorageLocationsUseCase: GetStorageLocationsUseCase by inject()
    private val getItemDetailUseCase: GetItemDetailUseCase by inject()

    suspend fun getDashboardData(): DashboardData = getDashboardDataUseCase().getOrThrow()
    suspend fun getInventoryItems(): List<PantryItem> = getInventoryItemsUseCase().getOrThrow()
    suspend fun getShoppingList(): List<PantryItem> = getShoppingListUseCase().getOrThrow()
    suspend fun getItemDetail(itemId: String): PantryItem = getItemDetailUseCase(itemId).getOrThrow()
}
