package org.bedu.architecturecomponents.utils

import org.bedu.architecturecomponents.data.local.Vehicle
import org.junit.Test
import com.google.common.truth.Truth.assertThat


class VehicleUtilsKtTest {

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
}