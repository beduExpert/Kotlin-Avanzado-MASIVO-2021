package org.bedu.roomvehicles.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.bedu.roomvehicles.data.local.Vehicle
import org.bedu.roomvehicles.data.local.VehicleDao


// Requerido, aqui se declaran ciertos parámetros de la DB
@Database(entities = arrayOf(Vehicle::class), version = 2)
abstract class VehicleDb : RoomDatabase(){

    companion object {
        private var beduInstance: VehicleDb? = null

        const val DB_NAME = "VEHICLE_DB"

        fun getInstance(context: Context) : VehicleDb?{
            if(beduInstance==null){

                synchronized(VehicleDb::class){
                    Room.databaseBuilder(
                        context.applicationContext,
                        VehicleDb::class.java,
                        DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
                        .also { beduInstance = it }
                }
            }

            return beduInstance
        }
    }

    abstract fun vehicleDao(): VehicleDao

}