package org.bedu.roomvehicles

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
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
import org.bedu.roomvehicles.room.Vehicle
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class AddEditFragment : Fragment() {

    private var editVehicle: Vehicle? = null
    private var _binding: FragmentAddEditBinding? = null
    private val binding get() = _binding!!
    private val vehicleDao by lazy {
        (requireActivity().application as BeduApplication).vehicleDao
    }

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
        withContext(Dispatchers.IO) {
            editVehicle = vehicleDao.getVehicleById(vehicleId)
        }
        Log.d("Vehicle", "$editVehicle")
        binding.editPlates.setText(editVehicle?.platesNumber)
        binding.editBrand.setText(editVehicle?.brand)
        binding.editModel.setText(editVehicle?.model)
        binding.switchWorking.isChecked = editVehicle?.isWorking ?: false
        binding.buttonAddCar.text = "Actualizar"
    }

    private suspend fun editVehicle() {
        editVehicle =  Vehicle(
            id = editVehicle?.id!!,
            brand = binding.editBrand.text.toString(),
            platesNumber = binding.editPlates.text.toString(),
            model = binding.editModel.text.toString(),
            isWorking = binding.switchWorking.isEnabled
        )

        withContext(Dispatchers.IO) {
            vehicleDao.updateVehicle(editVehicle!!)
        }
    }

    private suspend fun addVehicle() {
        val vehicle = Vehicle(
            brand = binding.editBrand.text.toString(),
            platesNumber = binding.editPlates.text.toString(),
            model = binding.editModel.text.toString(),
            isWorking = binding.switchWorking.isChecked
        )

        withContext(Dispatchers.IO) {
            vehicleDao.insertVehicle(vehicle)
        }
    }

}