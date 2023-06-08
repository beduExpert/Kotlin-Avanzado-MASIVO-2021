package org.bedu.roomvehicles

import android.app.Application
import org.bedu.roomvehicles.room.BeduDb
import org.bedu.roomvehicles.room.VehicleDao

class BeduApplication: Application() {
    val database by lazy {BeduDb.getInstance(this)}
}