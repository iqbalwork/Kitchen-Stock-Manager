package com.iqbalfauzi.kitchenstockmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iqbalfauzi.kitchenstockmanager.components.AppTopBar
import com.iqbalfauzi.kitchenstockmanager.components.PantryItemCard
import com.iqbalfauzi.kitchenstockmanager.components.SectionHeader
import kitchenstockmanager.sharedui.generated.resources.Res
import kitchenstockmanager.sharedui.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        AppTopBar(title = stringResource(Res.string.app_name))

        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            SectionHeader(
                title = "Shopping List",
                actionText = "Clear All"
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(Color(0xFF2D6A4F)))
                Spacer(modifier = Modifier.width(4.dp))
                Text("4 items remaining", fontSize = 14.sp, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = Color(0xFFF4D35E), modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("SUGGESTED TO BUY", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            }

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item {
                    PantryItemCard(
                        name = "Organic Whole Milk",
                        quantity = "Need: 1 Gallon",
                        price = "~$4.99",
                        status = "Out",
                        statusColor = Color(0xFFE63946),
                        leadingContent = { Checkbox(checked = false, onCheckedChange = {}) }
                    )
                }
                item {
                    PantryItemCard(
                        name = "Free-Range Eggs",
                        quantity = "Need: 1 Dozen",
                        price = "~$6.50",
                        status = "Low Stock",
                        statusColor = Color(0xFFF4D35E),
                        leadingContent = { Checkbox(checked = false, onCheckedChange = {}) }
                    )
                }
                
                item {
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("CUSTOM ITEMS", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                item {
                    PantryItemCard(
                        name = "Sourdough Bread",
                        quantity = "Bakery section",
                        leadingContent = { Checkbox(checked = false, onCheckedChange = {}) },
                        trailingContent = { 
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.Edit, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                            }
                        }
                    )
                }
                item {
                    PantryItemCard(
                        name = "Fresh Basil",
                        quantity = "Produce - 1 bunch",
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
