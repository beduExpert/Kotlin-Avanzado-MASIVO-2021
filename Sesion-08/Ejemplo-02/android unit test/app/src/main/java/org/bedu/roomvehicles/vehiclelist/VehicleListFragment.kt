package org.bedu.roomvehicles.vehiclelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import org.bedu.roomvehicles.R
import org.bedu.roomvehicles.VehiclesApplication
import org.bedu.roomvehicles.databinding.FragmentVehicleListBinding


/**
 * A fragment representing a list of Items.
 */
class VehicleListFragment : Fragment(){


    private lateinit var adapter: VehicleAdapter
    private lateinit var viewModel: VehicleListViewModel
    private lateinit var binding: FragmentVehicleListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_vehicle_list,
            container,
            false
        )

        viewModel = VehicleListViewModel(
            (requireContext().applicationContext as VehiclesApplication).vehicleRepository
        )

        binding.lifecycleOwner = this
        binding.vehicleListViewModel = viewModel

        setupEditVehicle()


        binding.buttonAdd.setOnClickListener {
            findNavController().navigate(
                    R.id.action_vehicleListFragment_to_addEditFragment
            )
        }



        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupVehicleList()

    }

    private fun setupVehicleList(){
        if(viewModel!=null){
            adapter = VehicleAdapter(viewModel)
            binding.list.adapter = adapter

            viewModel.vehicleList.observe(viewLifecycleOwner, Observer {
                it?.let {
                    adapter.submitList(it)
                    viewModel.updateVehicleStats(it)
                }
            })

        }
    }

    private fun setupEditVehicle(){
        if(viewModel!=null){

            with(viewModel){

                eventEditVehicle.observe(viewLifecycleOwner, Observer {
                    if(eventEditVehicle.value!=null){
                        findNavController().navigate(
                                R.id.action_vehicleListFragment_to_addEditFragment,
                                bundleOf("vehicle_id" to eventEditVehicle.value!!)
                        )
                        eventEditVehicle.value = null
                    }
                })
            }
        }
    }

}