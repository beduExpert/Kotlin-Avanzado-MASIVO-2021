package org.bedu.roomvehicles.provider

import android.content.UriMatcher
import org.bedu.roomvehicles.room.Vehicle

const val VEHICLE_DIR = 1
const val VEHICLE_ITEM = 2

val vehicleMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
    addURI(VehicleProvider.AUTHORITY, "${Vehicle.TABLE_NAME}", VEHICLE_DIR)
    addURI(VehicleProvider.AUTHORITY, "${Vehicle.TABLE_NAME}/*", VEHICLE_ITEM)
}



