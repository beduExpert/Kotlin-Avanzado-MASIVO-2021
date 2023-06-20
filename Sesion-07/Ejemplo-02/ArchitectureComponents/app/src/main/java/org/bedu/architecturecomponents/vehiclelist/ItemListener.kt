package org.bedu.architecturecomponents.vehiclelist;

import org.bedu.architecturecomponents.data.local.Vehicle

interface ItemListener {
    fun onEdit(vehicle: Vehicle)

    fun onDelete(vehicle: Vehicle)

}
