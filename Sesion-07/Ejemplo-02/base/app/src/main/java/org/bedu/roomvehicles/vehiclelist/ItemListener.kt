package org.bedu.roomvehicles.vehiclelist;

import org.bedu.roomvehicles.data.local.Vehicle

interface ItemListener {
    fun onEdit(vehicle: Vehicle)

    fun onDelete(vehicle: Vehicle)

}
