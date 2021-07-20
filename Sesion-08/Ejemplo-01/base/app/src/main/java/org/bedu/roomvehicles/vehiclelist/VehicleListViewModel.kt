package org.bedu.roomvehicles.vehiclelist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.bedu.roomvehicles.data.local.Vehicle
import org.bedu.roomvehicles.data.VehicleRepository
import org.bedu.roomvehicles.utils.activeVehiclesPercentage
import org.bedu.roomvehicles.utils.getNumberOfVehicles

class VehicleListViewModel(private val vehicleRepository: VehicleRepository): ViewModel() {


    val vehicleList = vehicleRepository.getVehicles()
    var vehicleNumber = MutableLiveData(0)
    var activeVehicles = MutableLiveData(0f)

    private var _editVehicleId = MutableLiveData<Int?>()
    val eventEditVehicle = _editVehicleId

    fun updateVehicleStats(vehicles:List<Vehicle>?){
        vehicleNumber.value = getNumberOfVehicles(vehicles)
        activeVehicles.value = activeVehiclesPercentage(vehicles)
        Log.d("hola","vehiculos update: $vehicleNumber , $activeVehicles")
    }


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