package org.bedu.architecturecomponents.vehiclelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.bedu.architecturecomponents.R
import org.bedu.architecturecomponents.data.local.Vehicle
import org.bedu.architecturecomponents.databinding.VehicleItemBinding

class VehicleAdapter(
    private val viewModel: VehicleListViewModel,
) :
    ListAdapter<Vehicle, VehicleAdapter.ViewHolder>(VehicleDiffCallback()) {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder private constructor(val binding: VehicleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewModel: VehicleListViewModel, item: Vehicle) {
            binding.viewModel = viewModel
            binding.vehicle = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = VehicleItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(viewGroup)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = getItem(position)
        viewHolder.bind(viewModel,item)
    }

}

class VehicleDiffCallback : DiffUtil.ItemCallback<Vehicle>() {
    override fun areItemsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Vehicle, newItem: Vehicle): Boolean {
        return oldItem == newItem
    }
}