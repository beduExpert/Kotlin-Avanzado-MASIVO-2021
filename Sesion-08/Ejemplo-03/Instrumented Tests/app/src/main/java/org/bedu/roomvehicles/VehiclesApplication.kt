package org.bedu.roomvehicles

import android.app.Application
import org.bedu.roomvehicles.data.Repository
import org.bedu.roomvehicles.data.ServiceLocator
import org.bedu.roomvehicles.data.VehicleRepository
import org.bedu.roomvehicles.room.VehicleDb

class VehiclesApplication: Application() {

    val vehicleRepository: Repository
        get() = ServiceLocator.provideRepository(this)

}