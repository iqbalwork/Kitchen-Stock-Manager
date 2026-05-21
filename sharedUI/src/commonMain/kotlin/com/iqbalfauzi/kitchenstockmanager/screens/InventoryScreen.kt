package com.iqbalfauzi.kitchenstockmanager.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.iqbalfauzi.kitchenstockmanager.components.AppTopBar
import com.iqbalfauzi.kitchenstockmanager.components.PantryItemCard
import com.iqbalfauzi.kitchenstockmanager.theme.Primary

@Composable
fun InventoryScreen(
    onNavigateToDetail: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AppTopBar(title = "Kitchen Inventory")
        Spacer(modifier = Modifier.height(16.dp))
        FilterSection()
        Spacer(modifier = Modifier.height(16.dp))
        InventoryList(onNavigateToDetail)
    }
}

@Composable
fun FilterSection() {
    val filters = listOf("All Items", "Produce", "Dairy", "Meat", "Pantry")
    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(filters) { filter ->
            val isSelected = filter == "All Items"
            FilterChip(
                selected = isSelected,
                onClick = {},
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
fun InventoryList(onNavigateToDetail: (String) -> Unit) {
    val items = listOf(
        InventoryItemData("Hass Avocados", "4 units", "Produce", "Fresh", Color(0xFF4CAF50)),
        InventoryItemData("Organic Whole Milk", "200 ml", "Dairy", "Exp. Tomorrow", Color(0xFFF4D35E)),
        InventoryItemData("Chicken Breast", "500 g", "Meat", "Use Today", Color(0xFFE63946)),
        InventoryItemData("Fusilli Pasta", "1.2 kg", "Pantry", "Exp. Dec 2024", Color.Gray)
    )

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items) { item ->
            PantryItemCard(
                name = item.name,
                quantity = item.quantity,
                category = item.category,
                status = item.status,
                statusColor = item.statusColor,
                imagePlaceholder = item.name.take(1),
                onClick = { onNavigateToDetail(item.name) }
            )
        }
    }
}

data class InventoryItemData(
    val name: String,
    val quantity: String,
    val category: String,
    val status: String,
    val statusColor: Color
)
