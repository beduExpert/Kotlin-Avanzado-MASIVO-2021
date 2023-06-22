package org.bedu.architecturecomponents.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import org.bedu.architecturecomponents.data.local.Vehicle
import org.bedu.architecturecomponents.data.local.VehicleDao

class VehicleRepository(
    private val vehicleDao: VehicleDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {

    fun getVehicles(): LiveData<List<Vehicle>> {
        return vehicleDao.getVehicles()
    }

    suspend fun removeVehicle(vehicle: Vehicle){
        coroutineScope {
            launch { vehicleDao.removeVehicle(vehicle ) }
        }
    }

    suspend fun addVehicle(vehicle: Vehicle){
        coroutineScope {
            launch { vehicleDao.insertVehicle(vehicle ) }
        }
    }

    fun populateVehicles(vehicles: List<Vehicle>) {
        return vehicleDao.insertAll(vehicles)
    }

}