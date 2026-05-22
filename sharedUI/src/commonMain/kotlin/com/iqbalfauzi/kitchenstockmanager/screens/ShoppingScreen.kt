package com.iqbalfauzi.kitchenstockmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalfauzi.kitchenstockmanager.components.*
import com.iqbalfauzi.kitchenstockmanager.presentation.shopping.*
import com.iqbalfauzi.kitchenstockmanager.theme.*
import kitchenstockmanager.sharedui.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen(
    viewModel: ShoppingViewModel = koinViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AppTopBar(title = stringResource(Res.string.app_name))

        PullToRefreshBox(
            isRefreshing = state.isLoading,
            onRefresh = { viewModel.handleIntent(ShoppingIntent.Refresh) },
            modifier = Modifier.fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                SectionHeader(
                    title = "Shopping List",
                    actionText = "Clear All"
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFF2D6A4F)))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("${state.suggestedItems.size + state.customItems.size} items remaining", fontSize = 14.sp, color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(24.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (state.suggestedItems.isNotEmpty()) {
                        item {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFF4D35E), modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("SUGGESTED TO BUY", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                            }
                        }
                        items(state.suggestedItems) { item ->
                            PantryItemCard(
                                name = item.productName,
                                quantity = item.quantity.toString(), // Simplified
                                price = item.price ?: "~$0.00",
                                status = item.status,
                                statusColor = Color(0xFFE63946),
                                leadingContent = { Checkbox(checked = false, onCheckedChange = {}) }
                            )
                        }
                    }
                    
                    if (state.customItems.isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text("CUSTOM ITEMS", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                            Spacer(modifier = Modifier.height(12.dp))
                        }

                        items(state.customItems) { item ->
                            PantryItemCard(
                                name = item.productName,
                                quantity = item.quantity.toString(),
                                leadingContent = { Checkbox(checked = false, onCheckedChange = {}) },
                                trailingContent = { 
                                    IconButton(onClick = {}) {
                                        Icon(Icons.Default.Edit, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
