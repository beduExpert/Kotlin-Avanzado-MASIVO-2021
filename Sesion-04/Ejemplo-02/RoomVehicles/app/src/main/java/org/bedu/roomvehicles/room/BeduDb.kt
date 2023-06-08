package org.bedu.roomvehicles.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Vehicle::class], version = 1)
abstract class BeduDb : RoomDatabase(){

    abstract fun vehicleDao(): VehicleDao

    companion object {
        @Volatile
        private var beduInstance: BeduDb? = null

        const val DB_NAME = "Bedu_DB"

        fun getInstance(context: Context) : BeduDb {

            return beduInstance?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BeduDb::class.java,
                    DB_NAME
                ).build()
                beduInstance = instance
                // return instance
                instance
            }
        }
    }
}