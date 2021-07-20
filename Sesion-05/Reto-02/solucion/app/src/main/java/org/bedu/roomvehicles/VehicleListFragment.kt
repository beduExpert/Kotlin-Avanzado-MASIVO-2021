package org.bedu.roomvehicles

import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.bedu.roomvehicles.provider.VehicleProvider
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

    private val loaderCallbacks: LoaderManager.LoaderCallbacks<Cursor> = object : LoaderManager.LoaderCallbacks<Cursor> {
        override fun onCreateLoader(id: Int, @Nullable args: Bundle?): Loader<Cursor?> {
            return CursorLoader(requireContext().applicationContext,
                    Uri.parse("${VehicleProvider.URI_VEHICLE}"),
                    arrayOf(
                            Vehicle.COLUMN_PK,
                            Vehicle.COLUMN_BRAND,
                            Vehicle.COLUMN_MODEL,
                            Vehicle.COLUMN_PLATES,
                            Vehicle.COLUMN_WORKING
                    ),
                    null,
                    null,
                    null
            )
        }

        override fun onLoadFinished(loader: Loader<Cursor?>, data: Cursor?) {

            data?.apply {
                // Determine the column index of the column named "word"
                val pkIndex: Int = getColumnIndex(Vehicle.COLUMN_PK)
                val brandIndex: Int = getColumnIndex(Vehicle.COLUMN_BRAND)
                val modelIndex: Int = getColumnIndex(Vehicle.COLUMN_MODEL)
                val platesIndex: Int = getColumnIndex(Vehicle.COLUMN_PLATES)
                val workingIndex: Int = getColumnIndex(Vehicle.COLUMN_WORKING)

                while (moveToNext()) {
                    // Gets the value from the column.
                    val pk = getInt(pkIndex)
                    val brand = getString(brandIndex)
                    val model = getString(modelIndex)
                    val plates = getString(platesIndex)

                    val vehicle = Vehicle(
                            id = pk,
                            brand = brand,
                            model = model,
                            platesNumber = plates,
                            isWorking = true
                    )


                    vehicleArray.add(vehicle)
                }
            }




            adapter = VehicleAdapter(vehicleArray, getListener())
            recyclerVehicle.adapter = adapter
        }

        override fun onLoaderReset(loader: Loader<Cursor?>) {
            recyclerVehicle.adapter = null
        }
    }


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

        LoaderManager.getInstance(this).initLoader(1,null,loaderCallbacks)


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
        findNavController().navigate(
                R.id.action_vehicleListFragment_to_addEditFragment,
                bundleOf("edit_vehicle" to vehicle)
        )
    }

    override fun onDelete(vehicle: Vehicle) {

        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        executor.execute(Runnable {
            val selectionClause = "${Vehicle.COLUMN_PK} LIKE ?"
            val selectionArgs = arrayOf(vehicle.id.toString())


            context?.applicationContext?.contentResolver?.delete(
                    Uri.parse("${VehicleProvider.URI_VEHICLE}/${vehicle.id}"),
                    selectionClause,
                    selectionArgs

            )



            Handler(Looper.getMainLooper()).post(Runnable {
                adapter.removeItem(vehicle)
                Toast.makeText(context,"Elemento eliminado!",Toast.LENGTH_SHORT).show()
            })
        })


    }

}