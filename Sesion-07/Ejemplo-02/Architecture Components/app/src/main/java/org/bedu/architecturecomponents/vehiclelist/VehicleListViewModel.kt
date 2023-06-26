package org.bedu.architecturecomponents.vehiclelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bedu.architecturecomponents.data.local.Vehicle
import org.bedu.architecturecomponents.data.local.VehicleRepository

class VehicleListViewModel(
    private val vehicleRepository: VehicleRepository
): ViewModel(){

    val vehicleList = vehicleRepository.getVehicles()

    private var _editVehicleId = MutableLiveData<Int?>()
    val eventEditVehicle = _editVehicleId

    fun onEdit(vehicleId: Int){
        eventEditVehicle.value = vehicleId
    }

    init{
        // prepopulate()
    }

    fun prepopulate() {
        val vehicles = listOf(
            Vehicle(
                model = "Vento",
                brand = "Volkswagen",
                platesNumber = "STF0321",
                isWorking = true
            ),
            Vehicle(
                model = "Jetta",
                brand = "Volkswagen",
                platesNumber = "FBN6745",
                isWorking = true
            )
        )

        viewModelScope.launch {
            vehicleRepository.populateVehicles(vehicles)
        }
    }

    fun removeVehicle(vehicle: Vehicle) = viewModelScope.launch{
        vehicleRepository.removeVehicle(vehicle)
    }

}