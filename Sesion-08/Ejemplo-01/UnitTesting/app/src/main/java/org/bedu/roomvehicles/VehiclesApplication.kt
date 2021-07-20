package org.bedu.roomvehicles

import android.app.Application
import org.bedu.roomvehicles.data.VehicleRepository
import org.bedu.roomvehicles.room.VehicleDb

class VehiclesApplication: Application() {

    val vehicleRepository: VehicleRepository
        get() = VehicleRepository(
            VehicleDb.getInstance(this)!!.vehicleDao()
        )
}