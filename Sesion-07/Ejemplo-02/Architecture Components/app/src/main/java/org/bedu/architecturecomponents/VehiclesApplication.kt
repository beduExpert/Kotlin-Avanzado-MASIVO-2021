package org.bedu.architecturecomponents

import android.app.Application
import org.bedu.architecturecomponents.data.local.VehicleDb
import org.bedu.architecturecomponents.data.local.VehicleRepository

class VehiclesApplication: Application() {

    val vehicleRepository: VehicleRepository
        get() = VehicleRepository(
            VehicleDb.getInstance(this)!!.vehicleDao()
        )
}