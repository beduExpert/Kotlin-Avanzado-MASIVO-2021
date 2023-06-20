package org.bedu.architecturecomponents.vehiclelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.bedu.architecturecomponents.data.local.Vehicle
import org.bedu.architecturecomponents.data.VehicleRepository

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