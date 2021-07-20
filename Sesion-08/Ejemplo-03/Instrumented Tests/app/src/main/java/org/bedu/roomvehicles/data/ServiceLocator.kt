package org.bedu.roomvehicles.data

import android.content.Context
import org.bedu.roomvehicles.room.VehicleDb

object ServiceLocator {

    private var database: VehicleDb? = null
    var repository: Repository? = null

    private val lock = Any()

    fun resetRepository(){

        synchronized(lock){
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            repository = null
        }

    }

    fun provideRepository(context: Context): Repository{
        synchronized(lock){
            return repository ?: createRepository(context)
        }
    }

    private fun createRepository(context: Context): Repository{
        database = VehicleDb.getInstance(context)
        val repo = VehicleRepository(database!!.vehicleDao())
        repository = repo
        return repo
    }

}