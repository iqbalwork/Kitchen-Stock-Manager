package com.iqbalfauzi.kitchenstockmanager.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iqbalfauzi.kitchenstockmanager.components.AppTopBar
import com.iqbalfauzi.kitchenstockmanager.components.CategoryChip
import com.iqbalfauzi.kitchenstockmanager.theme.Primary
import kitchenstockmanager.sharedui.generated.resources.Res
import kitchenstockmanager.sharedui.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddItemScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            AppTopBar(
                title = stringResource(Res.string.app_name),
                showBackButton = true,
                onBackClick = onBack
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Add New Item", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Text("Log your groceries to track freshness.", fontSize = 14.sp, color = Color.Gray)

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                    .background(Color(0xFFF8F9FA), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.QrCodeScanner, contentDescription = null, tint = Primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("SCAN BARCODE", fontWeight = FontWeight.Bold, color = Primary)
                }
            }

            Text("OR ENTER MANUALLY", modifier = Modifier.align(Alignment.CenterHorizontally), fontSize = 12.sp, color = Color.Gray)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Item Name", fontWeight = FontWeight.Medium)
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("e.g., Organic Honeycrisp Apples") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Category", fontWeight = FontWeight.Medium)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    CategoryChip("Produce", Icons.Default.Eco, true)
                    CategoryChip("Dairy", Icons.Default.WaterDrop, false)
                    CategoryChip("Meat", Icons.Default.Restaurant, false)
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Quantity", fontWeight = FontWeight.Medium)
                    OutlinedTextField(
                        value = "1",
                        onValueChange = {},
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Unit", fontWeight = FontWeight.Medium)
                    OutlinedTextField(
                        value = "Pieces",
                        onValueChange = {},
                        trailingIcon = { Icon(Icons.Default.KeyboardArrowDown, null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9E6)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CalendarToday, null, tint = Color(0xFFF4D35E), modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Freshness Tracking", fontWeight = FontWeight.Bold)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Purchase Date", fontSize = 12.sp)
                            OutlinedTextField(
                                value = "05/19/2026",
                                onValueChange = {},
                                trailingIcon = { Icon(Icons.Default.CalendarMonth, null) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Expiry Date", fontSize = 12.sp)
                            OutlinedTextField(
                                value = "mm/dd/yyyy",
                                onValueChange = {},
                                trailingIcon = { Icon(Icons.Default.CalendarMonth, null) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Inventory, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Save to Pantry")
            }
        }
    }
}
