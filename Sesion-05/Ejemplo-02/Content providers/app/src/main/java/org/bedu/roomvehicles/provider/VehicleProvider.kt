package org.bedu.roomvehicles.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.util.Log
import org.bedu.roomvehicles.BeduApplication
import org.bedu.roomvehicles.room.Vehicle
import org.bedu.roomvehicles.room.VehicleDao
import java.lang.IllegalArgumentException

class VehicleProvider: ContentProvider() {

    // Guarda la instancia de nuestro DAO
    private lateinit var vehicleDao: VehicleDao

    companion object{
        const val AUTHORITY = "org.bedu.roomvehicles.provider"
        const val URI_STRING = "content://$AUTHORITY/"
        const val URI_VEHICLE = "$URI_STRING${Vehicle.TABLE_NAME}"
        const val MIME_CONTENT_TYPE = "vnd.android.cursor.dir/$URI_VEHICLE"
        const val MIME_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/$URI_VEHICLE"
    }

    override fun onCreate(): Boolean {
        vehicleDao = (context as BeduApplication).vehicleDao
        return true
    }

    // Select
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        when(vehicleMatcher.match(uri)){
            VEHICLE_ITEM,VEHICLE_DIR -> {
                val code = vehicleMatcher.match(uri)
                Log.d("Vehicles","code: $code")
                val cursor=
                        if (code== VEHICLE_DIR) vehicleDao.getVehicles()
                        else vehicleDao.getVehicleById(ContentUris.parseId(uri).toInt())
                Log.d("Vehicles","cursor: ${cursor.count}")
                cursor.setNotificationUri(context?.contentResolver, uri)
                return cursor
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    // muestra el MIME correspondiente al URI
    override fun getType(uri: Uri): String {
        return when(vehicleMatcher.match(uri)){
            VEHICLE_DIR -> MIME_CONTENT_TYPE
            VEHICLE_ITEM -> MIME_CONTENT_ITEM_TYPE
            else -> throw IllegalArgumentException("Unknown Uri: $uri")
        }
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri {
        when(vehicleMatcher.match(uri)){
            VEHICLE_DIR -> {
                val vehicle = Vehicle(
                    model = values?.getAsString(Vehicle.COLUMN_MODEL),
                    brand = values?.getAsString(Vehicle.COLUMN_BRAND),
                    platesNumber = values?.getAsString(Vehicle.COLUMN_PLATES)
                )
                val id = context?.let { vehicleDao.insertVehicle(vehicle) }
                context?.contentResolver?.notifyChange(uri,null)
                return ContentUris.withAppendedId(uri, id!!.toLong())
            }

            VEHICLE_ITEM -> throw IllegalArgumentException("Invalid URI: id should not be provided")

            else -> throw IllegalArgumentException("Unknown Uri: $uri")
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        when(vehicleMatcher.match(uri)){
            VEHICLE_DIR -> throw IllegalArgumentException("Invalid URI: id should be provided")

            VEHICLE_ITEM -> {

                val id = ContentUris.parseId(uri)
                val count = vehicleDao.removeVehicleById(id.toInt())
                return count
            }

            else -> throw IllegalArgumentException("Unknown Uri: $uri")
        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        when(vehicleMatcher.match(uri)){
            VEHICLE_DIR -> throw IllegalArgumentException("Invalid URI: id should be provided")

            VEHICLE_ITEM -> {

                val vehicle = Vehicle(
                        id = ContentUris.parseId(uri).toInt(),
                        model = values?.getAsString(Vehicle.COLUMN_MODEL),
                        brand = values?.getAsString(Vehicle.COLUMN_BRAND),
                        platesNumber = values?.getAsString(Vehicle.COLUMN_PLATES)
                )

                return vehicleDao.updateVehicle(vehicle)

            }

            else -> throw IllegalArgumentException("Unknown Uri: $uri")
        }
    }
}