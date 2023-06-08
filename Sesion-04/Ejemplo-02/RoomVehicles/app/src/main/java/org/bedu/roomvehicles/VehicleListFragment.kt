package org.bedu.roomvehicles

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bedu.roomvehicles.room.BeduDb
import org.bedu.roomvehicles.room.ReducedVehicle
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class VehicleListFragment : Fragment(), ItemListener {


    private lateinit var addButton: FloatingActionButton
    private lateinit var recyclerVehicle: RecyclerView
    private lateinit var adapter: VehicleAdapter

    private var vehicleArray: MutableList<ReducedVehicle>? = null
    private val  db by lazy {(requireActivity().application as BeduApplication).database}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vehicle_list, container, false)

        addButton = view.findViewById(R.id.button_add)
        recyclerVehicle = view.findViewById(R.id.list)

        addButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_vehicleListFragment_to_addEditFragment
            )
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        populateList()
    }

    private fun populateList() {
        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        executor.execute(Runnable {

            this@VehicleListFragment.vehicleArray = db.vehicleDao()
                .getReducedVehicles() as MutableList<ReducedVehicle>

            Handler(Looper.getMainLooper()).post(Runnable {
                adapter = VehicleAdapter(vehicleArray, this@VehicleListFragment)
                recyclerVehicle.adapter = adapter
            })
        })
    }

    override fun onEdit(vehicle: ReducedVehicle) {


    }

    override fun onDelete(vehicle: ReducedVehicle) {

        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        executor.execute(Runnable {

            BeduDb
                .getInstance(context = requireContext())
                .vehicleDao()
                .removeVehicleById(vehicle.id)

            Handler(Looper.getMainLooper()).post(Runnable {
                adapter.removeItem(vehicle)
                Toast.makeText(context,"Elemento eliminado!",Toast.LENGTH_SHORT).show()
            })
        })
    }

}