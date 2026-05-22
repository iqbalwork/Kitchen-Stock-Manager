package com.iqbalfauzi.kitchenstockmanager.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalfauzi.kitchenstockmanager.components.AppTopBar
import com.iqbalfauzi.kitchenstockmanager.components.PantryItemCard
import com.iqbalfauzi.kitchenstockmanager.presentation.inventory.*
import com.iqbalfauzi.kitchenstockmanager.theme.Primary
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    onNavigateToDetail: (String) -> Unit,
    viewModel: InventoryViewModel = koinViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.viewEffect.collect { effect ->
            when (effect) {
                is InventoryEffect.NavigateToDetail -> onNavigateToDetail(effect.itemId)
                is InventoryEffect.ShowError -> {
                    // Handle error
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopBar(title = "Kitchen Inventory")
        
        PullToRefreshBox(
            isRefreshing = state.isLoading,
            onRefresh = { viewModel.handleIntent(InventoryIntent.Refresh) },
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                FilterSection(
                    selectedFilter = state.selectedFilter,
                    onFilterSelected = { viewModel.handleIntent(InventoryIntent.OnFilterSelected(it)) }
                )
                Spacer(modifier = Modifier.height(16.dp))
                InventoryList(
                    items = state.items,
                    onItemClick = { viewModel.handleIntent(InventoryIntent.OnItemClick(it)) }
                )
            }
        }
    }
}

@Composable
fun FilterSection(
    selectedFilter: String,
    onFilterSelected: (String) -> Unit
) {
    val filters = listOf("All Items", "Produce", "Dairy", "Meat", "Pantry")
    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters) { filter ->
            val isSelected = filter == selectedFilter
            FilterChip(
                selected = isSelected,
                onClick = { onFilterSelected(filter) },
                label = { Text(filter) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = Primary,
                    selectedLabelColor = Color.White
                ),
                shape = RoundedCornerShape(20.dp)
            )
        }
    }
}

@Composable
fun InventoryList(
    items: List<com.iqbalfauzi.kitchenstockmanager.domain.model.PantryItem>,
    onItemClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->
            PantryItemCard(
                name = item.productName,
                quantity = "${item.quantity} ${item.unit}",
                category = item.categoryName,
                status = item.status,
                imagePlaceholder = item.productName.take(1),
                onClick = { onItemClick(item.id) }
            )
        }
    }
}
