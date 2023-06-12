package org.bedu.roomvehicles.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Vehicle::class], version = 1)
abstract class VehicleDb : RoomDatabase(){

    abstract fun vehicleDao(): VehicleDao

    companion object {
        @Volatile
        private var dbInstance: VehicleDb? = null

        private const val DB_NAME = "vehicle_db"

        fun getInstance(context: Context) : VehicleDb {

            return dbInstance?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    VehicleDb::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                dbInstance = instance

                instance
            }
        }
    }
}