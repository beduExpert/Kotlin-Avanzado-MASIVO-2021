package org.bedu.roomvehicles.addeditvehicle

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import androidx.fragment.app.Fragment
import org.bedu.roomvehicles.R


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


        }

        return view
    }
}