package org.bedu.roomvehicles

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
import org.bedu.roomvehicles.room.Vehicle

class AddEditFragment : Fragment() {

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

        binding.buttonAddCar.setOnClickListener {addVehicle()}


        return binding.root
    }

    private fun addVehicle() {
        val vehicle = Vehicle(
            brand = binding.editBrand.text.toString(),
            platesNumber = binding.editPlates.text.toString(),
            model = binding.editModel.text.toString(),
            isWorking = binding.switchWorking.isEnabled
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                vehicleDao.insertVehicle(vehicle)
            }
            findNavController().navigate(
                R.id.action_addEditFragment_to_vehicleListFragment
            )
        }
    }
}