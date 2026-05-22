package com.iqbalfauzi.kitchenstockmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalfauzi.kitchenstockmanager.components.AppTopBar
import com.iqbalfauzi.kitchenstockmanager.presentation.detail.*
import com.iqbalfauzi.kitchenstockmanager.theme.LowStock
import com.iqbalfauzi.kitchenstockmanager.theme.Primary
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    itemId: String,
    onBack: () -> Unit,
    onNavigateToUpdate: (String) -> Unit,
    viewModel: ItemDetailViewModel = koinViewModel(parameters = { parametersOf(itemId) })
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.viewEffect.collect { effect ->
            when (effect) {
                is ItemDetailEffect.NavigateBack -> onBack()
                is ItemDetailEffect.ShowError -> {
                    // Show error snackbar
                }
            }
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Item Details",
                showBackButton = true,
                onBackClick = onBack,
                showProfile = false,
                actions = {
                    IconButton(onClick = { onNavigateToUpdate(itemId) }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More", tint = Primary)
                    }
                }
            )
        }
    ) { padding ->
        PullToRefreshBox(
            isRefreshing = state.isLoading,
            onRefresh = { viewModel.handleIntent(ItemDetailIntent.Refresh) },
            modifier = Modifier.padding(padding).fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                val item = state.item
                
                // Placeholder Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(24.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Image Placeholder", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(item?.productName ?: "Loading...", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        Text("${item?.categoryName ?: ""} • Whole Foods Market", fontSize = 14.sp, color = Color.Gray)
                    }
                    
                    Surface(
                        color = Primary,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(item?.quantity?.toString() ?: "0", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                            Text(item?.unit ?: "units", fontSize = 12.sp, color = Color.White)
                        }
                    }
                }

                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("STOCK LEVEL", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                            Text("${item?.quantity ?: 0} remaining", fontSize = 12.sp, color = Color.Gray)
                        }
                        
                        LinearProgressIndicator(
                            progress = { item?.progress ?: 0f },
                            modifier = Modifier.fillMaxWidth().height(12.dp),
                            color = LowStock,
                            trackColor = Color(0xFFEEEEEE),
                            strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                        )

                        Row(
                            modifier = Modifier.padding(top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = { viewModel.handleIntent(ItemDetailIntent.MarkAsUsed) },
                                modifier = Modifier.weight(1f).height(48.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Primary)
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = null)
                                Spacer(Modifier.width(8.dp))
                                Text("Mark as Used (1)")
                            }

                            Surface(
                                onClick = { viewModel.handleIntent(ItemDetailIntent.IncreaseQuantity) },
                                modifier = Modifier.size(48.dp),
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant,
                                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Add, contentDescription = null)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
