package org.bedu.roomvehicles.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VehicleDao {

    @Insert
    suspend fun insertVehicle(vehicle: Vehicle)

    @Insert
    fun insertAll(vehicle: List<Vehicle>)

    @Update
    suspend fun updateVehicle(vehicle: Vehicle)

    @Delete
    suspend fun removeVehicle(vehicle: Vehicle)

    @Query("DELETE FROM Vehicle WHERE id=:id")
    suspend fun removeVehicleById(id: Int)

    @Delete
    suspend fun removeVehicles(vararg vehicles: Vehicle)

    @Query("SELECT * FROM Vehicle")
    fun getVehicles(): LiveData<List<Vehicle>>

    @Query("SELECT * FROM Vehicle WHERE id = :id")
    suspend fun getVehicleById(id: Int): Vehicle

    @Query("SELECT * FROM Vehicle WHERE plates_number = :platesNumber")
    suspend fun getVehicleByPlates(platesNumber: String) : Vehicle
}
