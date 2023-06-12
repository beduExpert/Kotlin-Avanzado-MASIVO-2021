package org.bedu.roomvehicles

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bedu.roomvehicles.databinding.FragmentVehicleListBinding
import org.bedu.roomvehicles.provider.VehicleProvider
import org.bedu.roomvehicles.room.Vehicle

class VehicleListFragment : Fragment(), ItemListener {

    private var _binding: FragmentVehicleListBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: VehicleAdapter

    private val vehicleArray = mutableListOf<Vehicle>()

    private val resolver by lazy { context?.applicationContext?.contentResolver }

    private val vehicleDao by lazy {
        (requireActivity().applicationContext as BeduApplication).vehicleDao
    }

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
        LoaderManager.getInstance(this).initLoader(1,null,loaderCallbacks)
    }

    fun prepopulate(){
        val vehicles = listOf(
            Vehicle(model = "Vento",brand = "Volkswagen",platesNumber = "STF032",isWorking = true),
            Vehicle(model = "Jetta",brand = "Volkswagen",platesNumber = "FBN674",isWorking = true)
        )

        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                vehicleDao.insertAll(vehicles)
            }
        }
    }

    override fun onEdit(vehicle: Vehicle) {
        findNavController().navigate(
            R.id.action_vehicleListFragment_to_addEditFragment,
            bundleOf("vehicle_id" to vehicle.id )
        )
    }

    override fun onDelete(vehicle: Vehicle) {
        val selectionClause = "${Vehicle.COLUMN_PK} LIKE ?"
        val selectionArgs = arrayOf(vehicle.id.toString())

        lifecycleScope.launch{
            withContext(Dispatchers.IO) {
                context?.applicationContext?.contentResolver?.delete(
                    Uri.parse("${VehicleProvider.URI_VEHICLE}/${vehicle.id}"),
                    selectionClause,
                    selectionArgs
                )
            }
            adapter.removeItem(vehicle)
            Toast.makeText(context,"Elemento eliminado!",Toast.LENGTH_SHORT).show()
        }


    }



    private val loaderCallbacks: LoaderManager.LoaderCallbacks<Cursor> = object : LoaderManager.LoaderCallbacks<Cursor> {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor?> {
            Log.d("Vehicles", VehicleProvider.URI_VEHICLE)
            return CursorLoader(requireContext().applicationContext,
                Uri.parse(VehicleProvider.URI_VEHICLE),
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

            Log.d("Vehicles","termina, ${data?.count}")

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


            adapter = VehicleAdapter(vehicleArray, this@VehicleListFragment)
            binding.list.adapter = adapter
        }

        override fun onLoaderReset(loader: Loader<Cursor?>) {
            binding.list.adapter = null
        }
    }

}