package org.bedu.roomvehicles.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Brand (
    @PrimaryKey val brandId: Int,
    @ColumnInfo(name = "brand_name") val brandName: String
)