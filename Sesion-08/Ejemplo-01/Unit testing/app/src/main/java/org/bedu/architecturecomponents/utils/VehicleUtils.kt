package org.bedu.architecturecomponents.utils

import org.bedu.architecturecomponents.data.local.Vehicle

internal fun getNumberOfVehicles(vehicles: List<Vehicle>?) = vehicles?.size ?: 0

internal fun activeVehiclesPercentage(vehicles: List<Vehicle>?): Float{

    val activeVehicles = vehicles?.count{it.isWorking} ?: 0
    val totalVehicles = vehicles?.size ?: 0
    return ( activeVehicles/totalVehicles.toFloat() ) * 100f
}
