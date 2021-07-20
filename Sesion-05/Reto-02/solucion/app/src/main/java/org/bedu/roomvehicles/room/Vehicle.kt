package org.bedu.roomvehicles.room

import android.os.Parcelable
import android.provider.BaseColumns
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import org.bedu.roomvehicles.room.Vehicle.Companion.TABLE_NAME

@Parcelize
@Entity(tableName = TABLE_NAME,indices = [Index(value = ["plates_number"], unique = true)])
data class Vehicle(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true,name = COLUMN_PK) val id: Int = 0,
    @ColumnInfo(name = COLUMN_MODEL) val model: String?,
    @ColumnInfo(name = COLUMN_BRAND) val brand: String?,
    @ColumnInfo(name = COLUMN_PLATES) val platesNumber: String?,
    @ColumnInfo(name= COLUMN_WORKING) val isWorking: Boolean = true
) : Parcelable {

    companion object{
        const val TABLE_NAME = "vehicles"
        const val COLUMN_PK = BaseColumns._ID
        const val COLUMN_BRAND = "brand"
        const val COLUMN_MODEL = "model"
        const val COLUMN_PLATES = "plates_number"
        const val COLUMN_WORKING = "is_working"
    }

}