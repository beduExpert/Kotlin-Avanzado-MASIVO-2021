package org.bedu.roomvehicles.vehiclelist

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.bedu.roomvehicles.R
import org.bedu.roomvehicles.data.local.Vehicle
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * A fragment representing a list of Items.
 */
class VehicleListFragment : Fragment(), ItemListener {


    private lateinit var addButton: FloatingActionButton
    private lateinit var recyclerVehicle: RecyclerView
    private lateinit var adapter: VehicleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun getListener(): ItemListener {
        return this
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


        val executor: ExecutorService = Executors.newSingleThreadExecutor()


        return view
    }

    override fun onEdit(vehicle: Vehicle) {


    }

    override fun onDelete(vehicle: Vehicle) {

    }

}