package org.bedu.roomvehicles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import org.bedu.roomvehicles.databinding.FragmentVehicleListBinding
import org.bedu.roomvehicles.room.Vehicle

class VehicleListFragment : Fragment(), ItemListener {

    private var _binding: FragmentVehicleListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: VehicleAdapter

    private val vehicleArray = mutableListOf<Vehicle>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVehicleListBinding.inflate(inflater, container, false)

        binding.buttonAdd.setOnClickListener {
            findNavController().navigate(
                R.id.action_vehicleListFragment_to_addEditFragment
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateList()
    }

    private fun populateList() {
        // prepopulate()
    }

    fun prepopulate(){
        val vehicles = listOf(
            Vehicle(model = "Vento",brand = "Volkswagen",platesNumber = "STF0321",isWorking = true),
            Vehicle(model = "Jetta",brand = "Volkswagen",platesNumber = "FBN6745",isWorking = true)
        )
    }

    override fun onEdit(vehicle: Vehicle) {
        findNavController().navigate(
            R.id.action_vehicleListFragment_to_addEditFragment,
            bundleOf("vehicle_id" to vehicle.id )
        )
    }

    override fun onDelete(vehicle: Vehicle) {

    }

}