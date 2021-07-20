package org.bedu.roomvehicles.vehiclelist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.bedu.roomvehicles.data.local.Vehicle
import org.bedu.roomvehicles.data.VehicleRepository

class VehicleListViewModel(private val vehicleRepository: VehicleRepository): ViewModel() {


    val vehicleList = vehicleRepository.getVehicles()

    private var _editVehicleId = MutableLiveData<Int?>()
    val eventEditVehicle = _editVehicleId


    fun removeVehicle(vehicle: Vehicle) = viewModelScope.launch{
        vehicleRepository.removeVehicle(vehicle)
    }

    fun onEdit(vehicleId: Int){
        eventEditVehicle.value = vehicleId
    }

    fun prepopulate(){

        val vehicles = listOf(
            Vehicle(model = "Vento",brand = "Volkswagen",platesNumber = "STF0321",isWorking = true),
            Vehicle(model = "Jetta",brand = "Volkswagen",platesNumber = "FBN6745",isWorking = true)
        )
        vehicleRepository.populateVehicles(vehicles)

    }



}