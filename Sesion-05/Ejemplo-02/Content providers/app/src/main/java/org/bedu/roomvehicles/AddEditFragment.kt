package org.bedu.roomvehicles

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.bedu.roomvehicles.provider.VehicleProvider
import org.bedu.roomvehicles.room.BeduDb
import org.bedu.roomvehicles.room.Vehicle
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors



class AddEditFragment : Fragment() {

    private lateinit var platesEdit: EditText
    private lateinit var brandEdit: EditText
    private lateinit var modelEdit: EditText
    private lateinit var workingSwitch: Switch
    private lateinit var addButton: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_edit, container, false)


        platesEdit = view.findViewById(R.id.edit_plates)
        brandEdit = view.findViewById(R.id.edit_brand)
        modelEdit = view.findViewById(R.id.edit_model)
        workingSwitch = view.findViewById(R.id.switch_working)
        addButton = view.findViewById(R.id.button_add_car)

        addButton.setOnClickListener{

            val executor: ExecutorService = Executors.newSingleThreadExecutor()

            executor.execute(Runnable {
                val values = ContentValues().apply {
                    put(Vehicle.COLUMN_BRAND, brandEdit.text.toString())
                    put(Vehicle.COLUMN_MODEL, modelEdit.text.toString())
                    put(Vehicle.COLUMN_PLATES, platesEdit.text.toString())
                    put(Vehicle.COLUMN_WORKING, workingSwitch.isEnabled)
                }


                context?.applicationContext?.contentResolver?.insert(
                        Uri.parse("${VehicleProvider.URI_VEHICLE}"),
                        values
                )

                Handler(Looper.getMainLooper()).post(Runnable {
                    findNavController().navigate(
                            R.id.action_addEditFragment_to_vehicleListFragment
                    )
                })
            })

        }

        return view
    }
}