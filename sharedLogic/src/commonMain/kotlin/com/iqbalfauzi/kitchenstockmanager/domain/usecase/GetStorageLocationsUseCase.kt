package com.iqbalfauzi.kitchenstockmanager.domain.usecase

import com.iqbalfauzi.kitchenstockmanager.domain.model.StorageLocation
import com.iqbalfauzi.kitchenstockmanager.domain.repository.PantryRepository

class GetStorageLocationsUseCase(private val repository: PantryRepository) {
    suspend operator fun invoke(): Result<List<StorageLocation>> {
        return try {
            Result.success(repository.getStorageLocations())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
