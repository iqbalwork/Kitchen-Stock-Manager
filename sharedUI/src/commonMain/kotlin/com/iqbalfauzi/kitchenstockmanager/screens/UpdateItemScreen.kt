package com.iqbalfauzi.kitchenstockmanager.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Archive
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iqbalfauzi.kitchenstockmanager.components.AppTopBar
import com.iqbalfauzi.kitchenstockmanager.presentation.update.*
import com.iqbalfauzi.kitchenstockmanager.theme.Primary
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateItemScreen(
    itemId: String,
    onBack: () -> Unit,
    viewModel: UpdateItemViewModel = koinViewModel { parametersOf(itemId) }
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.viewEffect.collect { effect ->
            when (effect) {
                is UpdateItemEffect.NavigateBack -> onBack()
                is UpdateItemEffect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.message)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AppTopBar(
                title = "Update Item",
                showBackButton = true,
                onBackClick = onBack,
                showProfile = false
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = state.itemName,
                onValueChange = { viewModel.handleIntent(UpdateItemIntent.OnNameChange(it)) },
                label = { Text("Item Name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(Modifier.weight(1f))
            
            Button(
                onClick = { viewModel.handleIntent(UpdateItemIntent.OnUpdateClick) },
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary)
            ) {
                Icon(Icons.Default.Archive, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Update Details")
            }

            OutlinedButton(
                onClick = { viewModel.handleIntent(UpdateItemIntent.OnDeleteClick) },
                enabled = !state.isLoading,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color.Red.copy(alpha = 0.5f))
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(color = Color.Red, modifier = Modifier.size(24.dp))
                } else {
                    Icon(Icons.Default.Delete, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Delete Item")
                }
            }
        }
    }
}
