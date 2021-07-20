package org.bedu.roomvehicles.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.*
import org.bedu.roomvehicles.data.local.Vehicle
import org.bedu.roomvehicles.data.local.VehicleDao

class VehicleRepository(
    private val vehicleDao: VehicleDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    ) : Repository {


    override fun getVehicles(): LiveData<List<Vehicle>> {
        return vehicleDao.getVehicles()
    }

    override suspend fun removeVehicle(vehicle: Vehicle){
        coroutineScope {
            launch { vehicleDao.removeVehicle(vehicle ) }
        }
    }

    override suspend fun addVehicle(vehicle: Vehicle){
        coroutineScope {
            launch { vehicleDao.insertVehicle(vehicle ) }
        }
    }

    override fun populateVehicles(vehicles: List<Vehicle>) {
        return vehicleDao.insertAll(vehicles)
    }

}