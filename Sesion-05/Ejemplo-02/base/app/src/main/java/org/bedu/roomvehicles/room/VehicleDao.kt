package org.bedu.roomvehicles.room

import android.database.Cursor
import androidx.room.*

@Dao
interface VehicleDao {

    @Insert
    fun insertAll(vehicle: List<Vehicle>)

    @Insert
    fun insertVehicle(vehicle: Vehicle): Long

    @Update
    fun updateVehicle(vehicle: Vehicle): Int

    @Delete
    fun removeVehicle(vehicle: Vehicle)

    @Query("DELETE FROM ${Vehicle.TABLE_NAME} WHERE ${Vehicle.COLUMN_PK}=:id")
    fun removeVehicleById(id: Int): Int

    @Delete
    fun removeVehicles(vararg vehicles: Vehicle)

    @Query("SELECT * FROM ${Vehicle.TABLE_NAME}")
    fun getVehicles(): Cursor

    @Query("SELECT * FROM ${Vehicle.TABLE_NAME} WHERE ${Vehicle.COLUMN_PK} = :id")
    fun getVehicleById(id: Int): Cursor

    @Query("SELECT * FROM ${Vehicle.TABLE_NAME} WHERE plates_number = :platesNumber")
    fun getVehicleByPlates(platesNumber: String) : Cursor
}
