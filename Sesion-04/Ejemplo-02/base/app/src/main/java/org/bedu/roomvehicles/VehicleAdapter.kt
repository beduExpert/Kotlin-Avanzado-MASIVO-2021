package org.bedu.roomvehicles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.bedu.roomvehicles.room.Vehicle

class VehicleAdapter(
    private val vehicleArray: MutableList<Vehicle>?,
    val itemListener : ItemListener
    ) :
    RecyclerView.Adapter<VehicleAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val modelName: TextView
        val platesNumber: TextView
        val editButton: ImageButton
        val deleteButton: ImageButton


        init {
            // Define click listener for the ViewHolder's View.
            modelName = view.findViewById(R.id.model_name)
            platesNumber = view.findViewById(R.id.plates_number)
            editButton  = view.findViewById(R.id.button_edit)
            deleteButton  = view.findViewById(R.id.button_delete)



        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.vehicle_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
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

    fun removeItem(vehicle: Vehicle){
        vehicleArray?.let {
            val position = it.indexOf(vehicle)
            vehicleArray?.remove(vehicle)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,vehicleArray.size)
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = vehicleArray?.size ?: 0

}
