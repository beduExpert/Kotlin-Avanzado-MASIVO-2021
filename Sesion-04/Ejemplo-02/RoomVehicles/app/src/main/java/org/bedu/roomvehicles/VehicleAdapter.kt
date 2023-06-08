package org.bedu.roomvehicles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.bedu.roomvehicles.room.ReducedVehicle
import org.bedu.roomvehicles.room.Vehicle

class VehicleAdapter(
    private val vehicleArray: MutableList<ReducedVehicle>?,
    private val itemListener : ItemListener
    ) :
    RecyclerView.Adapter<VehicleAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val modelName: TextView
        val platesNumber: TextView
        val editButton: ImageButton
        val deleteButton: ImageButton

        init {
            modelName = view.findViewById(R.id.model_name)
            platesNumber = view.findViewById(R.id.plates_number)
            editButton  = view.findViewById(R.id.button_edit)
            deleteButton  = view.findViewById(R.id.button_delete)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.vehicle_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.modelName.text = vehicleArray?.get(position)?.model ?: ""
        viewHolder.platesNumber.text = vehicleArray?.get(position)?.platesNumber ?: ""

        viewHolder.deleteButton.setOnClickListener{
            vehicleArray?.get(position)?.let {
                    vehicle -> itemListener.onDelete(vehicle)
            }
        }

        viewHolder.editButton.setOnClickListener{
            vehicleArray?.get(position)?.let {
                    vehicle -> itemListener.onEdit(vehicle)
            }
        }
    }

    fun removeItem(vehicle: ReducedVehicle){
        vehicleArray?.let {
            val position = it.indexOf(vehicle)
            vehicleArray.remove(vehicle)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,vehicleArray.size)
        }
    }

    override fun getItemCount() = vehicleArray?.size ?: 0

}
