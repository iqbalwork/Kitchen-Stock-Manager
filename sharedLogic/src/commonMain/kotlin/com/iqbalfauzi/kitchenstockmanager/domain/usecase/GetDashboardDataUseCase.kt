package com.iqbalfauzi.kitchenstockmanager.domain.usecase

import com.iqbalfauzi.kitchenstockmanager.domain.model.PantryItem
import com.iqbalfauzi.kitchenstockmanager.domain.repository.PantryRepository

data class DashboardData(
    val expiringSoon: List<PantryItem>,
    val runningLow: List<PantryItem>
)

class GetDashboardDataUseCase(private val repository: PantryRepository) {
    suspend operator fun invoke(): Result<DashboardData> {
        return try {
            val expiring = repository.getExpiringSoonItems()
            val runningLow = repository.getRunningLowItems()
            Result.success(DashboardData(expiring, runningLow))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
