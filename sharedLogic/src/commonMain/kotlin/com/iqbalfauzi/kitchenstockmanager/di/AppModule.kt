package com.iqbalfauzi.kitchenstockmanager.di

import com.iqbalfauzi.kitchenstockmanager.AppConfig
import com.iqbalfauzi.kitchenstockmanager.data.repository.SupabasePantryRepository
import com.iqbalfauzi.kitchenstockmanager.domain.repository.PantryRepository
import com.iqbalfauzi.kitchenstockmanager.domain.usecase.GetDashboardDataUseCase
import com.iqbalfauzi.kitchenstockmanager.domain.usecase.GetInventoryItemsUseCase
import com.iqbalfauzi.kitchenstockmanager.domain.usecase.GetShoppingListUseCase
import com.iqbalfauzi.kitchenstockmanager.domain.usecase.AddPantryItemUseCase
import com.iqbalfauzi.kitchenstockmanager.domain.usecase.GetCategoriesUseCase
import com.iqbalfauzi.kitchenstockmanager.domain.usecase.GetStorageLocationsUseCase
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.storage.Storage
import org.koin.dsl.module

val appModule = module {
    // Supabase
    single<SupabaseClient> {
        createSupabaseClient(
            supabaseUrl = "https://${AppConfig.SUPABASE_ID}.supabase.co",
            supabaseKey = AppConfig.SUPABASE_ANON_KEY
        ) {
            install(Postgrest)
            install(Auth)
            install(Realtime)
            install(Storage)
        }
    }

    // Repositories
    single<PantryRepository> { SupabasePantryRepository(get()) }

    // UseCases
    factory { GetDashboardDataUseCase(get()) }
    factory { GetInventoryItemsUseCase(get()) }
    factory { GetShoppingListUseCase(get()) }
    factory { AddPantryItemUseCase(get()) }
    factory { GetCategoriesUseCase(get()) }
    factory { GetStorageLocationsUseCase(get()) }
}
