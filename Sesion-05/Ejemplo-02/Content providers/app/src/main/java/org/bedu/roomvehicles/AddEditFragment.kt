package org.bedu.roomvehicles

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bedu.roomvehicles.databinding.FragmentAddEditBinding
import org.bedu.roomvehicles.provider.VehicleProvider
import org.bedu.roomvehicles.room.Vehicle

class AddEditFragment : Fragment() {

    private var editVehicle: Vehicle? = null
    private var _binding: FragmentAddEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditBinding.inflate(inflater, container, false)


        val vehicleId: Int? = arguments?.getInt("vehicle_id")

        vehicleId?.let { vId ->
            lifecycleScope.launch {
                setEditVehicleInViews(vId)
            }
        }

        binding.buttonAddCar.setOnClickListener {
            lifecycleScope.launch{
                editVehicle?.let { editVehicle() } ?: run { addVehicle() }

                findNavController().navigate(
                    R.id.action_addEditFragment_to_vehicleListFragment
                )
            }
        }


        return binding.root
    }

    private suspend fun setEditVehicleInViews(vehicleId: Int) {
        /*Log.d("Vehicle", "$editVehicle")
        binding.editPlates.setText(editVehicle?.platesNumber)
        binding.editBrand.setText(editVehicle?.brand)
        binding.editModel.setText(editVehicle?.model)
        binding.switchWorking.isChecked = editVehicle?.isWorking ?: false
        binding.buttonAddCar.text = "Actualizar"*/
    }

    private suspend fun editVehicle() {
        editVehicle =  Vehicle(
            id = editVehicle?.id!!,
            brand = binding.editBrand.text.toString(),
            platesNumber = binding.editPlates.text.toString(),
            model = binding.editModel.text.toString(),
            isWorking = binding.switchWorking.isEnabled
        )


    }

    private suspend fun addVehicle() {

        val values = ContentValues().apply {
            put(Vehicle.COLUMN_BRAND, binding.editBrand.text.toString())
            put(Vehicle.COLUMN_MODEL, binding.editModel.text.toString())
            put(Vehicle.COLUMN_PLATES, binding.editPlates.text.toString())
            put(Vehicle.COLUMN_WORKING, binding.switchWorking.isChecked)
        }

        withContext(Dispatchers.IO) {
            context?.applicationContext?.contentResolver?.insert(
                Uri.parse(VehicleProvider.URI_VEHICLE),
                values
            )
        }
    }
}