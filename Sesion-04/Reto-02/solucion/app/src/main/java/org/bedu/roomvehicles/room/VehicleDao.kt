package org.bedu.roomvehicles.room

import androidx.room.*

@Dao
interface VehicleDao {

    @Insert
    suspend fun insertVehicle(vehicle: Vehicle)

    @Update
    suspend fun updateVehicle(vehicle: Vehicle)

    @Delete
    fun removeVehicle(vehicle: Vehicle)

    @Query("DELETE FROM Vehicle WHERE id=:id")
    suspend fun removeVehicleById(id: Int)

    @Delete
    suspend fun removeVehicles(vararg vehicles: Vehicle)

    @Query("SELECT * FROM Vehicle")
    suspend fun getVehicles(): List<Vehicle>

    @Query("SELECT id,model,plates_number as platesNumber FROM Vehicle")
    suspend fun getReducedVehicles(): List<ReducedVehicle>


    @Query("SELECT * FROM Vehicle WHERE id = :id")
    suspend fun getVehicleById(id: Int): Vehicle

    @Query("SELECT * FROM Vehicle WHERE plates_number = :platesNumber")
    suspend fun getVehicleByPlates(platesNumber: String) : Vehicle
}
