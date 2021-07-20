package org.bedu.roomvehicles.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["plates_number"], unique = true)])
data class Vehicle(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo val model: String?,
    @ColumnInfo val brand: String?,
    @ColumnInfo(name = "plates_number") val platesNumber: String?,
    @ColumnInfo(name="is_working") val isWorking: Boolean
)