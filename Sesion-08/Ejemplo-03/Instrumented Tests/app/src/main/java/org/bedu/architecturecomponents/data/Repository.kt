package org.bedu.architecturecomponents.data

import androidx.lifecycle.LiveData
import org.bedu.architecturecomponents.data.local.Vehicle

interface Repository {
    fun getVehicles(): LiveData<List<Vehicle>>
    suspend fun removeVehicle(vehicle: Vehicle)
    suspend fun addVehicle(vehicle: Vehicle)
}