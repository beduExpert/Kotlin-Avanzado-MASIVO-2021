package org.bedu.roomvehicles;

import org.bedu.roomvehicles.room.Vehicle

interface ItemListener {
    fun onEdit(vehicle: Vehicle)

    fun onDelete(vehicle: Vehicle)

}
