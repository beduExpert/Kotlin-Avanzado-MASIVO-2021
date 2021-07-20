package org.bedu.roomvehicles.addeditvehicle

import android.text.Editable
import android.util.Log
import android.widget.CompoundButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.bedu.roomvehicles.data.VehicleRepository
import org.bedu.roomvehicles.data.local.Vehicle

class AddEditViewModel(private val vehicleRepository: VehicleRepository): ViewModel() {


    private var _vehicleDone = MutableLiveData<Boolean>(false)
    val vehicleDone = _vehicleDone


    var model: String? = null
    var brand: String? = null
    var platesNumber: String? = null
    var isWorking = false

    fun setPlates(s: CharSequence, start:Int, before: Int, count:Int){
        platesNumber = s.toString()
    }

    fun setBrandName(s: CharSequence, start:Int, before: Int, count:Int){
        brand = s.toString()
    }

    fun setModelName(s: CharSequence, start:Int, before: Int, count:Int){
            model = s.toString()
        }

    fun setIsWorking(button: CompoundButton,value: Boolean){
        isWorking = value
    }

    fun newVehicle() = viewModelScope.launch{
        if ( !model.isNullOrBlank() && !brand.isNullOrBlank() && !platesNumber.isNullOrBlank() ){
            val vehicle = Vehicle(
                    brand = brand,
                    model = model,
                    platesNumber = platesNumber,
                    isWorking = isWorking
            )

            vehicleRepository.addVehicle(vehicle)

            _vehicleDone.value = true
        }
    }



}

