package com.iqbalfauzi.kitchenstockmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalfauzi.kitchenstockmanager.components.*
import com.iqbalfauzi.kitchenstockmanager.domain.model.PantryItem
import com.iqbalfauzi.kitchenstockmanager.presentation.dashboard.*
import com.iqbalfauzi.kitchenstockmanager.theme.*
import kitchenstockmanager.sharedui.generated.resources.*
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    onNavigateToDetail: (String) -> Unit,
    viewModel: DashboardViewModel = koinViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    
    LaunchedEffect(Unit) {
        viewModel.viewEffect.collect { effect ->
            when (effect) {
                is DashboardEffect.NavigateToDetail -> onNavigateToDetail(effect.itemId)
                is DashboardEffect.ShowError -> {
                    // Handle error
                }
            }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(title = stringResource(Res.string.app_name))
        
        PullToRefreshBox(
            isRefreshing = state.isLoading,
            onRefresh = { viewModel.handleIntent(DashboardIntent.Refresh) },
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                item {
                    DashboardHeader()
                }
                item {
                    ExpiringSoonCard(state.expiringSoon)
                }
                item {
                    RunningLowSection(state.runningLow, onNavigateToDetail)
                }
                item {
                    CategoriesSection()
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun DashboardHeader() {
    var searchQuery by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(Res.string.greeting),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = stringResource(Res.string.subtitle),
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))
        AppSearchBar(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = stringResource(Res.string.search_hint)
        )
    }
}

@Composable
fun ExpiringSoonCard(items: List<PantryItem>) {
    if (items.isEmpty()) return
    
    val isDark = isSystemInDarkTheme()
    val containerColor = if (isDark) WarningContainerDark else WarningContainer
    val textColor = if (isDark) WarningTextDark else WarningText
    val iconBgColor = if (isDark) Color(0xFFE63946) else ExpiringSoon

    InfoCard(
        title = stringResource(Res.string.expiring_soon_title),
        description = "${items.size} items need your attention within the next 48 hours.",
        icon = Icons.Default.Warning,
        iconBgColor = iconBgColor,
        containerColor = containerColor,
        contentColor = textColor,
        actionText = stringResource(Res.string.review_items),
        onActionClick = {}
    )
}

@Composable
fun RunningLowSection(items: List<PantryItem>, onNavigateToDetail: (String) -> Unit) {
    if (items.isEmpty()) return

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionHeader(
            title = stringResource(Res.string.running_low),
            actionText = stringResource(Res.string.view_all)
        )
        
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items.forEach { item ->
                    PantryItemCard(
                        name = item.productName,
                        quantity = "${item.quantity} ${item.unit}",
                        status = item.status,
                        progress = item.progress,
                        icon = Icons.Default.Opacity, // In real app use icon mapping
                        iconBg = Color(0xFFF4D35E),
                        statusColor = LowStock,
                        showProgress = true,
                        onClick = { onNavigateToDetail(item.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun CategoriesSection() {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(Res.string.categories),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CategoryItem(stringResource(Res.string.spices), Icons.Default.Restaurant, Color(0xFFFFF9E6), Modifier.weight(1f))
            CategoryItem(stringResource(Res.string.grains), Icons.Default.Grain, Color(0xFFFFF9E6), Modifier.weight(1f))
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            CategoryItem(stringResource(Res.string.fruits), Icons.Default.Kitchen, Color(0xFFFFE5E5), Modifier.weight(1f))
            CategoryItem(stringResource(Res.string.vegetables), Icons.Default.Eco, Color(0xFFE5F9F0), Modifier.weight(1f))
        }
    }
}

@Composable
fun CategoryItem(name: String, icon: ImageVector, bgColor: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = Color.DarkGray)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = name, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }
    }
}
