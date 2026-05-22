package com.iqbalfauzi.kitchenstockmanager.presentation.dashboard

import com.iqbalfauzi.kitchenstockmanager.domain.model.PantryItem
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewEffect
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewIntent
import com.iqbalfauzi.kitchenstockmanager.presentation.ViewState

data class DashboardState(
    val isLoading: Boolean = false,
    val expiringSoon: List<PantryItem> = emptyList(),
    val runningLow: List<PantryItem> = emptyList(),
    val error: String? = null
) : ViewState

sealed interface DashboardIntent : ViewIntent {
    data object LoadDashboard : DashboardIntent
    data object Refresh : DashboardIntent
    data class OnItemClick(val itemId: String) : DashboardIntent
}

sealed interface DashboardEffect : ViewEffect {
    data class NavigateToDetail(val itemId: String) : DashboardEffect
    data class ShowError(val message: String) : DashboardEffect
}
