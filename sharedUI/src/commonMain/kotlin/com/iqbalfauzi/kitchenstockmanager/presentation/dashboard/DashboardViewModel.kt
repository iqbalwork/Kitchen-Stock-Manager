package com.iqbalfauzi.kitchenstockmanager.presentation.dashboard

import androidx.lifecycle.viewModelScope
import com.iqbalfauzi.kitchenstockmanager.domain.usecase.GetDashboardDataUseCase
import com.iqbalfauzi.kitchenstockmanager.presentation.BaseViewModel
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getDashboardDataUseCase: GetDashboardDataUseCase
) : BaseViewModel<DashboardState, DashboardIntent, DashboardEffect>(DashboardState()) {

    init {
        handleIntent(DashboardIntent.LoadDashboard)
    }

    override fun handleIntent(intent: DashboardIntent) {
        when (intent) {
            is DashboardIntent.LoadDashboard -> loadDashboard()
            is DashboardIntent.Refresh -> loadDashboard()
            is DashboardIntent.OnItemClick -> setEffect(DashboardEffect.NavigateToDetail(intent.itemId))
        }
    }

    private fun loadDashboard() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            getDashboardDataUseCase().fold(
                onSuccess = { data ->
                    setState {
                        copy(
                            isLoading = false,
                            expiringSoon = data.expiringSoon,
                            runningLow = data.runningLow
                        )
                    }
                },
                onFailure = { error ->
                    setState { copy(isLoading = false, error = error.message) }
                    setEffect(DashboardEffect.ShowError(error.message ?: "Unknown error"))
                }
            )
        }
    }
}
