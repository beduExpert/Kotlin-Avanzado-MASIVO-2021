package org.bedu.roomvehicles.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


// Requerido, aqui se declaran ciertos parámetros de la DB
@Database(entities = arrayOf(Vehicle::class), version = 2)
abstract class BeduDb : RoomDatabase(){

    companion object {
        private var beduInstance: BeduDb? = null

        const val DB_NAME = "Bedu_DB"

        fun getInstance(context: Context) : BeduDb?{
            if(beduInstance==null){

                synchronized(BeduDb::class){
                    beduInstance = Room.databaseBuilder(
                        context.applicationContext,
                        BeduDb::class.java,
                        DB_NAME)
                        .fallbackToDestructiveMigration()
                                .build()
                }
            }

            return beduInstance
        }
    }

    abstract fun vehicleDao(): VehicleDao

}