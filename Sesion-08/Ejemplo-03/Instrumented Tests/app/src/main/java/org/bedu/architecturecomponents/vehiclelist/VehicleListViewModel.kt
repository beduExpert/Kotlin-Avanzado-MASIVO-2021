package org.bedu.architecturecomponents.vehiclelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.bedu.architecturecomponents.data.Repository
import org.bedu.architecturecomponents.data.local.Vehicle
import org.bedu.architecturecomponents.data.VehicleRepository
import org.bedu.architecturecomponents.utils.activeVehiclesPercentage
import org.bedu.architecturecomponents.utils.getNumberOfVehicles

class VehicleListViewModel(private val vehicleRepository: Repository): ViewModel() {


    var vehicleNumber = MutableLiveData(0)
    var activeVehicles = MutableLiveData(0f)

    val vehicleList = vehicleRepository.getVehicles()

    private var _editVehicleId = MutableLiveData<Int?>()
    val eventEditVehicle = _editVehicleId

    fun updateVehicleStats(vehicles:List<Vehicle>?){
        vehicleNumber.value = getNumberOfVehicles(vehicles)
        activeVehicles.value = activeVehiclesPercentage(vehicles)
    }


    fun removeVehicle(vehicle: Vehicle) = viewModelScope.launch{
        vehicleRepository.removeVehicle(vehicle)
    }

    fun onEdit(vehicleId: Int){
        eventEditVehicle.value = vehicleId
    }
}