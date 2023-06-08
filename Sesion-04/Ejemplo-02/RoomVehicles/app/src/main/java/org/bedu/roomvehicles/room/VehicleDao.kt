package org.bedu.roomvehicles.room

import androidx.room.*

@Dao
interface VehicleDao {

    @Insert
    suspend fun insertVehicle(vehicle: Vehicle)

    @Update
    fun updateVehicle(vehicle: Vehicle)

    @Delete
    fun removeVehicle(vehicle: Vehicle)

    @Query("DELETE FROM Vehicle WHERE id=:id")
    suspend fun removeVehicleById(id: Int)

    @Delete
    fun removeVehicles(vararg vehicles: Vehicle)

    @Query("SELECT * FROM Vehicle")
    fun getVehicles(): List<Vehicle>

    @Query("SELECT id,model,plates_number as platesNumber FROM Vehicle")
    suspend fun getReducedVehicles(): List<ReducedVehicle>


    @Query("SELECT * FROM Vehicle WHERE id = :id")
    fun getVehicleById(id: Int): Vehicle

    @Query("SELECT * FROM Vehicle WHERE plates_number = :platesNumber")
    fun getVehicleByPlates(platesNumber: String) : Vehicle
}
