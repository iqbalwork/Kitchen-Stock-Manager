package com.iqbalfauzi.kitchenstockmanager.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalfauzi.kitchenstockmanager.components.AppTopBar
import com.iqbalfauzi.kitchenstockmanager.components.CategoryChip
import com.iqbalfauzi.kitchenstockmanager.presentation.add.*
import com.iqbalfauzi.kitchenstockmanager.theme.Primary
import kitchenstockmanager.sharedui.generated.resources.Res
import kitchenstockmanager.sharedui.generated.resources.app_name
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddItemScreen(
    onBack: () -> Unit,
    viewModel: AddItemViewModel = koinViewModel()
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    
    var showPurchaseDatePicker by remember { mutableStateOf(false) }
    var showExpiryDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.viewEffect.collect { effect ->
            when (effect) {
                is AddItemEffect.NavigateBack -> onBack()
                is AddItemEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    if (showPurchaseDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showPurchaseDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            val date = Instant.fromEpochMilliseconds(it)
                                .toLocalDateTime(TimeZone.UTC).date.toString()
                            viewModel.handleIntent(AddItemIntent.OnPurchaseDateChange(date))
                        }
                        showPurchaseDatePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showPurchaseDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showExpiryDatePicker) {
        val datePickerState = rememberDatePickerState()
        DatePickerDialog(
            onDismissRequest = { showExpiryDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            val date = Instant.fromEpochMilliseconds(it)
                                .toLocalDateTime(TimeZone.UTC).date.toString()
                            viewModel.handleIntent(AddItemIntent.OnExpiryDateChange(date))
                        }
                        showExpiryDatePicker = false
                    }
                ) { Text("OK") }
            },
            dismissButton = {
                TextButton(onClick = { showExpiryDatePicker = false }) { Text("Cancel") }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AppTopBar(
                title = stringResource(Res.string.app_name),
                showBackButton = true,
                onBackClick = onBack,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
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
                    value = state.itemName,
                    onValueChange = { viewModel.handleIntent(AddItemIntent.OnNameChange(it)) },
                    placeholder = { Text("e.g., Organic Honeycrisp Apples") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Category", fontWeight = FontWeight.Medium)
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.categories.forEach { category ->
                        CategoryChip(
                            label = category.name,
                            icon = when {
                                category.name.contains("Sayur", ignoreCase = true) -> Icons.Default.Eco
                                category.name.contains("Daging", ignoreCase = true) -> Icons.Default.Restaurant
                                category.name.contains("Sembako", ignoreCase = true) -> Icons.Default.Inventory
                                category.name.contains("Dairy", ignoreCase = true) -> Icons.Default.WaterDrop
                                else -> Icons.Default.Category
                            },
                            selected = state.categoryId == category.id,
                            onClick = { viewModel.handleIntent(AddItemIntent.OnCategorySelect(category.id)) }
                        )
                    }
                }
            }

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Storage Location", fontWeight = FontWeight.Medium)
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.storageLocations.forEach { location ->
                        CategoryChip(
                            label = location.name,
                            icon = when {
                                location.name.contains("Fridge", ignoreCase = true) || location.name.contains("Kulkas", ignoreCase = true) -> Icons.Default.Kitchen
                                location.name.contains("Freezer", ignoreCase = true) -> Icons.Default.AcUnit
                                else -> Icons.Default.Place
                            },
                            selected = state.storageLocationId == location.id,
                            onClick = { viewModel.handleIntent(AddItemIntent.OnStorageSelect(location.id)) }
                        )
                    }
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Quantity", fontWeight = FontWeight.Medium)
                    OutlinedTextField(
                        value = state.quantity,
                        onValueChange = { viewModel.handleIntent(AddItemIntent.OnQuantityChange(it)) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Unit", fontWeight = FontWeight.Medium)
                    OutlinedTextField(
                        value = state.unit,
                        onValueChange = { viewModel.handleIntent(AddItemIntent.OnUnitChange(it)) },
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
                                value = state.purchaseDate,
                                onValueChange = { },
                                readOnly = true,
                                trailingIcon = { 
                                    IconButton(onClick = { showPurchaseDatePicker = true }) {
                                        Icon(Icons.Default.CalendarMonth, null)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Expiry Date", fontSize = 12.sp)
                            OutlinedTextField(
                                value = state.expiryDate,
                                onValueChange = { },
                                readOnly = true,
                                trailingIcon = { 
                                    IconButton(onClick = { showExpiryDatePicker = true }) {
                                        Icon(Icons.Default.CalendarMonth, null)
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { viewModel.handleIntent(AddItemIntent.OnSaveClick) },
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Icon(Icons.Default.Inventory, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Save to Pantry")
                }
            }
        }
    }
}
