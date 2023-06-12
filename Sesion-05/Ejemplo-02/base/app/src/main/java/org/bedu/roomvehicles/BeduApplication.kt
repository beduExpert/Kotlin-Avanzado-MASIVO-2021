package org.bedu.roomvehicles

import android.app.Application
import org.bedu.roomvehicles.room.VehicleDb
import org.bedu.roomvehicles.room.VehicleDao

class BeduApplication: Application() {
    private val database by lazy {VehicleDb.getInstance(this)}
    val vehicleDao
        get() = database.vehicleDao()
}