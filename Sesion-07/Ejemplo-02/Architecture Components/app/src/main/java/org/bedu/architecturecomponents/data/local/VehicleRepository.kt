package org.bedu.architecturecomponents.data.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VehicleRepository(
    private val vehicleDao: VehicleDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getVehicles() = vehicleDao.getVehicles()

    suspend fun populateVehicles(vehicles: List<Vehicle>) = withContext(ioDispatcher){
        return@withContext vehicleDao.insertAll(vehicles)
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

}