package com.iqbalfauzi.kitchenstockmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iqbalfauzi.kitchenstockmanager.components.AppTopBar
import com.iqbalfauzi.kitchenstockmanager.components.SectionHeader
import com.iqbalfauzi.kitchenstockmanager.components.PantryItemCard
import com.iqbalfauzi.kitchenstockmanager.components.AppSearchBar
import com.iqbalfauzi.kitchenstockmanager.components.InfoCard
import com.iqbalfauzi.kitchenstockmanager.theme.*
import kitchenstockmanager.sharedui.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun DashboardScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        AppTopBar(title = stringResource(Res.string.app_name))
        
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
                ExpiringSoonCard()
            }
            item {
                RunningLowSection()
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

@Composable
fun DashboardHeader() {
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
        OutlinedTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(stringResource(Res.string.search_hint)) },
            leadingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )
    }
}

@Composable
fun ExpiringSoonCard() {
    Card(
        colors = CardDefaults.cardColors(containerColor = WarningContainer),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(ExpiringSoon),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Warning, contentDescription = null, tint = Color.White)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = stringResource(Res.string.expiring_soon_title),
                    color = WarningText,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(Res.string.expiring_soon_desc),
                    color = WarningText,
                    fontSize = 14.sp
                )
                TextButton(onClick = {}, contentPadding = PaddingValues(0.dp)) {
                    Text(
                        text = stringResource(Res.string.review_items) + " →",
                        color = WarningText,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun RunningLowSection() {
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
                PantryItemCard(
                    name = "Olive Oil",
                    quantity = "15% left",
                    status = "15% left",
                    progress = 0.15f,
                    icon = Icons.Default.Opacity,
                    iconBg = Color(0xFFF4D35E),
                    statusColor = LowStock,
                    showProgress = true
                )
                PantryItemCard(
                    name = "Organic Eggs",
                    quantity = "2 remaining",
                    status = "2 remaining",
                    progress = 0.2f,
                    icon = Icons.Default.Egg,
                    iconBg = Color(0xFFA8E6CF),
                    statusColor = LowStock,
                    showProgress = true
                )
                PantryItemCard(
                    name = "Whole Wheat Flour",
                    quantity = "Low",
                    status = "Low",
                    progress = 0.3f,
                    icon = Icons.Default.BakeryDining,
                    iconBg = Color(0xFFE0E0E0),
                    statusColor = LowStock,
                    showProgress = true
                )
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
