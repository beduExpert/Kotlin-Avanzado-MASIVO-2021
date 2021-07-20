package org.bedu.roomvehicles;

import org.bedu.roomvehicles.room.ReducedVehicle;
import org.bedu.roomvehicles.room.Vehicle

interface ItemListener {
    fun onEdit(vehicle: ReducedVehicle)

    fun onDelete(vehicle: ReducedVehicle)

}
