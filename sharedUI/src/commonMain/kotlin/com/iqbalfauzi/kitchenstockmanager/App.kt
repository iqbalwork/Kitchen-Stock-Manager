package com.iqbalfauzi.kitchenstockmanager

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.iqbalfauzi.kitchenstockmanager.screens.*
import com.iqbalfauzi.kitchenstockmanager.theme.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import androidx.navigation3.runtime.*
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import org.jetbrains.compose.resources.stringResource
import kitchenstockmanager.sharedui.generated.resources.*

@Serializable
sealed interface Route : NavKey {
    @Serializable object Dashboard : Route
    @Serializable object Inventory : Route
    @Serializable object Shopping : Route
    @Serializable object AddItem : Route
    @Serializable data class ItemDetail(val itemId: String) : Route
    @Serializable data class UpdateItem(val itemId: String) : Route
}

private val navConfig = SavedStateConfiguration {
    serializersModule = SerializersModule {
        polymorphic(NavKey::class) {
            subclass(Route.Dashboard::class, Route.Dashboard.serializer())
            subclass(Route.Inventory::class, Route.Inventory.serializer())
            subclass(Route.Shopping::class, Route.Shopping.serializer())
            subclass(Route.AddItem::class, Route.AddItem.serializer())
            subclass(Route.ItemDetail::class, Route.ItemDetail.serializer())
            subclass(Route.UpdateItem::class, Route.UpdateItem.serializer())
        }
    }
}

@Composable
@Preview
fun App() {
    AppTheme {
        val backStack = rememberNavBackStack(navConfig, Route.Dashboard)
        val currentRoute = backStack.lastOrNull() as? Route

        Scaffold(
            bottomBar = {
                if (currentRoute != Route.AddItem) {
                    NavigationBar(
                        containerColor = Color.White,
                        tonalElevation = 8.dp
                    ) {
                        NavigationBarItem(
                            selected = currentRoute == Route.Dashboard,
                            onClick = { 
                                if (currentRoute != Route.Dashboard) {
                                    backStack.clear()
                                    backStack.add(Route.Dashboard)
                                }
                            },
                            icon = { Icon(Icons.Default.GridView, contentDescription = null) },
                            label = { Text(stringResource(Res.string.nav_dashboard)) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = Primary,
                                indicatorColor = Primary
                            )
                        )
                        NavigationBarItem(
                            selected = currentRoute == Route.Inventory,
                            onClick = { 
                                if (currentRoute != Route.Inventory) {
                                    backStack.add(Route.Inventory)
                                }
                            },
                            icon = { Icon(Icons.Default.Inventory, contentDescription = null) },
                            label = { Text(stringResource(Res.string.nav_inventory)) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = Primary,
                                indicatorColor = Primary
                            )
                        )
                        NavigationBarItem(
                            selected = currentRoute == Route.Shopping,
                            onClick = { 
                                if (currentRoute != Route.Shopping) {
                                    backStack.add(Route.Shopping)
                                }
                            },
                            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = null) },
                            label = { Text(stringResource(Res.string.nav_shopping)) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color.White,
                                selectedTextColor = Primary,
                                indicatorColor = Primary
                            )
                        )
                    }
                }
            },
            floatingActionButton = {
                if (currentRoute != Route.AddItem) {
                    FloatingActionButton(
                        onClick = { backStack.add(Route.AddItem) },
                        containerColor = Primary,
                        contentColor = Color.White
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Add Item")
                    }
                }
            }
        ) { paddingValues ->
            NavDisplay<NavKey>(
                backStack = backStack,
                modifier = Modifier.fillMaxSize(),
                onBack = {
                    if (backStack.size > 1) {
                        backStack.removeAt(backStack.size - 1)
                    }
                }
            ) { key ->
                NavEntry(key) {
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = if (key != Route.AddItem) paddingValues.calculateBottomPadding() else 0.dp),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        when (key) {
                            is Route.Dashboard -> DashboardScreen(
                                onNavigateToDetail = { id -> backStack.add(Route.ItemDetail(id)) }
                            )
                            is Route.Inventory -> InventoryScreen(
                                onNavigateToDetail = { id -> backStack.add(Route.ItemDetail(id)) }
                            )
                            is Route.Shopping -> ShoppingScreen()
                            is Route.AddItem -> AddItemScreen(onBack = { 
                                if (backStack.size > 1) backStack.removeAt(backStack.size - 1)
                            })
                            is Route.ItemDetail -> ItemDetailScreen(
                                itemId = key.itemId,
                                onBack = { backStack.removeAt(backStack.size - 1) },
                                onNavigateToUpdate = { id -> backStack.add(Route.UpdateItem(id)) }
                            )
                            is Route.UpdateItem -> UpdateItemScreen(
                                itemId = key.itemId,
                                onBack = { backStack.removeAt(backStack.size - 1) }
                            )
                            else -> Text("Unknown Route")
                        }
                    }
                }
            }
        }
    }
}
