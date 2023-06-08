package org.bedu.roomvehicles

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bedu.roomvehicles.databinding.FragmentVehicleListBinding
import org.bedu.roomvehicles.room.ReducedVehicle

class VehicleListFragment : Fragment(), ItemListener {

    private var _binding: FragmentVehicleListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: VehicleAdapter

    private val vehicleDao by lazy {
        (requireActivity().application as BeduApplication).vehicleDao }

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
        lifecycleScope.launch {
            val vehicleArray = withContext(Dispatchers.IO) {
               return@withContext vehicleDao.getReducedVehicles() as MutableList<ReducedVehicle>
            }
            adapter = VehicleAdapter(vehicleArray, this@VehicleListFragment)
            binding.list.adapter = adapter
        }
    }

    override fun onEdit(vehicle: ReducedVehicle) {
        findNavController().navigate(
            R.id.action_vehicleListFragment_to_addEditFragment,
            bundleOf("vehicle_id" to vehicle.id )
        )
    }

    override fun onDelete(vehicle: ReducedVehicle) {

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                vehicleDao.removeVehicleById(vehicle.id)
            }

            adapter.removeItem(vehicle)
            Toast.makeText(context, "Elemento eliminado!", Toast.LENGTH_SHORT).show()
        }
    }

}