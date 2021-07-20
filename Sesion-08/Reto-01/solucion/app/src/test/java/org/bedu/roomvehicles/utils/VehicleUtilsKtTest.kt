package org.bedu.roomvehicles.utils

import org.bedu.roomvehicles.data.local.Vehicle
import org.junit.Assert.*
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class VehicleUtilsKtTest{

    @Test
    fun getNumberOfVehicles_empty_returnsZero(){
        val vehicles = listOf<Vehicle>()

        val result = getNumberOfVehicles(vehicles)

        assertThat(result).isEqualTo(0)
    }

    @Test
    fun getNumberOfVehicles_null_returnsZero(){
        val vehicles = null

        val result = getNumberOfVehicles(vehicles)

        assertThat(result).isEqualTo(0)
    }

    @Test
    fun getNumberOfVehicles_two_returnsTwo(){
        val vehicles = listOf(
            Vehicle(
                0,
                "pointer",
                "Volkswagen",
                "SMT01",
                true
            ),
            Vehicle(
                1,
                "Vento",
                "Volkswagen",
                "GTA05",
                true
            )
        )

        val result = getNumberOfVehicles(vehicles)

        assertThat(result).isEqualTo(2)
    }

    @Test
    fun activeVehiclesPercentage_empty_returnsZero(){
        val vehicles = listOf<Vehicle>()

        val result = activeVehiclesPercentage(vehicles)

        assertThat(result).isEqualTo(0)
    }

    @Test
    fun activeVehiclesPercentage_null_returnsZero(){
        val vehicles = null

        val result = activeVehiclesPercentage(vehicles)

        assertThat(result).isEqualTo(0)
    }

    @Test
    fun activeVehiclesPercentage_two_returnsFifty(){
        val vehicles = listOf(
                Vehicle(
                        0,
                        "pointer",
                        "Volkswagen",
                        "SMT01",
                        true
                ),
                Vehicle(
                        1,
                        "Vento",
                        "Volkswagen",
                        "GTA05",
                        false
                )
        )

        val result = activeVehiclesPercentage(vehicles)

        assertThat(result).isEqualTo(50f)
    }



}