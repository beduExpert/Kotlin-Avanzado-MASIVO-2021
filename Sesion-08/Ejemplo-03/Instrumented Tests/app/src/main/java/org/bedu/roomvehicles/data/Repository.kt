package org.bedu.roomvehicles.data

import androidx.lifecycle.LiveData
import org.bedu.roomvehicles.data.local.Vehicle

interface Repository {
    fun getVehicles(): LiveData<List<Vehicle>>

    suspend fun removeVehicle(vehicle: Vehicle)
    suspend fun addVehicle(vehicle: Vehicle)
    fun populateVehicles(vehicles: List<Vehicle>)
}