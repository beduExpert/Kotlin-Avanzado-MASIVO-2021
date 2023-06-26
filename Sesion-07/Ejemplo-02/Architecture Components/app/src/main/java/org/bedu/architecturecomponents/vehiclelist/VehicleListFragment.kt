package org.bedu.architecturecomponents.vehiclelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bedu.architecturecomponents.R
import org.bedu.architecturecomponents.VehiclesApplication
import org.bedu.architecturecomponents.data.local.Vehicle
import org.bedu.architecturecomponents.databinding.FragmentVehicleListBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * A fragment representing a list of Items.
 */
class VehicleListFragment : Fragment(){


    private lateinit var binding: FragmentVehicleListBinding
    private lateinit var adapter: VehicleAdapter
    private lateinit var viewModel: VehicleListViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_vehicle_list,
            container,
            false
        )

        viewModel = VehicleListViewModel(
            (requireContext().applicationContext as VehiclesApplication).vehicleRepository
        )

        binding.buttonAdd.setOnClickListener {
            findNavController().navigate(
                R.id.action_vehicleListFragment_to_addEditFragment
            )
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupVehicleList()

        viewModel.eventEditVehicle.observe(viewLifecycleOwner) {
            if (viewModel.eventEditVehicle.value != null) {
                findNavController().navigate(
                    R.id.action_vehicleListFragment_to_addEditFragment,
                    bundleOf("vehicle_id" to viewModel.eventEditVehicle.value!!)
                )
                viewModel.eventEditVehicle.value = null
            }
        }
    }

    private fun setupVehicleList(){
        adapter = VehicleAdapter(viewModel)
        binding.list.adapter = adapter

        viewModel.vehicleList.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })
    }

}