package org.bedu.architecturecomponents.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.bedu.architecturecomponents.data.local.Vehicle

class FakeVehicleRepository: Repository {
    private var observableVehicles = MutableLiveData<List<Vehicle>>()

    override fun getVehicles(): LiveData<List<Vehicle>> = observableVehicles

    override suspend fun removeVehicle(vehicle: Vehicle) {
        val newList = observableVehicles.value?.toMutableList() ?: mutableListOf()
        newList.remove(vehicle)
        observableVehicles.value = newList
    }

    override suspend fun addVehicle(vehicle: Vehicle) {
        val newList = observableVehicles.value?.toMutableList() ?: mutableListOf()
        newList.add(vehicle)
        observableVehicles.value = newList
    }

    fun populateVehicles(vehicles: List<Vehicle>) {
        observableVehicles.value = vehicles
    }
}