package com.iqbalfauzi.kitchenstockmanager.di

import com.iqbalfauzi.kitchenstockmanager.presentation.add.AddItemViewModel
import com.iqbalfauzi.kitchenstockmanager.presentation.dashboard.DashboardViewModel
import com.iqbalfauzi.kitchenstockmanager.presentation.detail.ItemDetailViewModel
import com.iqbalfauzi.kitchenstockmanager.presentation.inventory.InventoryViewModel
import com.iqbalfauzi.kitchenstockmanager.presentation.shopping.ShoppingViewModel
import com.iqbalfauzi.kitchenstockmanager.presentation.update.UpdateItemViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { DashboardViewModel(get()) }
    viewModel { InventoryViewModel(get()) }
    viewModel { ShoppingViewModel(get()) }
    viewModel { AddItemViewModel(get(), get(), get()) }
    viewModel { (itemId: String) -> ItemDetailViewModel(itemId, get()) }
    viewModel { (itemId: String) -> UpdateItemViewModel(itemId, get()) }
}
