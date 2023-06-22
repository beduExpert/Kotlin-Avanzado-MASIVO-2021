package org.bedu.architecturecomponents

import android.app.Application
import org.bedu.architecturecomponents.data.Repository
import org.bedu.roomvehicles.data.ServiceLocator

class VehiclesApplication: Application() {

    val vehicleRepository: Repository
        get() = ServiceLocator.provideRepository(this)
}