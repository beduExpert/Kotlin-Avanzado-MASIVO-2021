package org.bedu.roomvehicles

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.bedu.roomvehicles.room.BeduDb
import org.bedu.roomvehicles.room.Vehicle
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * A fragment representing a list of Items.
 */
class VehicleListFragment : Fragment(), ItemListener {


    private lateinit var addButton: FloatingActionButton
    private lateinit var recyclerVehicle: RecyclerView
    private lateinit var adapter: VehicleAdapter

    private val vehicleArray = mutableListOf<Vehicle>()


    fun getListener(): ItemListener{
        return this
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vehicle_list, container, false)

        //prepopulate()
        addButton = view.findViewById(R.id.button_add)
        recyclerVehicle = view.findViewById(R.id.list)

        addButton.setOnClickListener {
            findNavController().navigate(
                    R.id.action_vehicleListFragment_to_addEditFragment
            )
        }

        return view
    }

    fun prepopulate(){

        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        executor.execute(Runnable {

            val vehicles = listOf(
                    Vehicle(model = "Vento",brand = "Volkswagen",platesNumber = "STF0321",isWorking = true),
                    Vehicle(model = "Jetta",brand = "Volkswagen",platesNumber = "FBN6745",isWorking = true)
            )

            BeduDb
                    .getInstance(context = requireContext())
                    ?.vehicleDao()
                    ?.insertAll(vehicles)

            Handler(Looper.getMainLooper()).post(Runnable {

            })
        })

    }

    override fun onEdit(vehicle: Vehicle) {


    }

    override fun onDelete(vehicle: Vehicle) {


    }

}