package org.bedu.roomvehicles.vehiclelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import org.bedu.roomvehicles.CoroutineTestRule
import org.bedu.roomvehicles.data.FakeVehicleRepository
import org.bedu.roomvehicles.data.local.Vehicle
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VehicleListViewModelTest{

    // Repositorio de vehículos fake
    private lateinit var vehicleRepository: FakeVehicleRepository

    // Sujeto sometido a pruebas
    private lateinit var viewModel: VehicleListViewModel

    // testing vehicles
    private val vento = Vehicle(model = "Vento",brand = "Volkswagen",platesNumber = "STF0321",isWorking = true)
    private val jetta = Vehicle(model = "Jetta",brand = "Volkswagen",platesNumber = "FBN6745",isWorking = true)
    private val tsuru = Vehicle(model = "Tsuru",brand = "Nissan",platesNumber = "RFG4583",isWorking = true)

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutineTestRule = CoroutineTestRule()

    @Before
    fun setup(){
        vehicleRepository = FakeVehicleRepository()

        val vehicles = listOf(vento,jetta)

        vehicleRepository.populateVehicles(vehicles)
        viewModel = VehicleListViewModel(vehicleRepository)
    }

    @Test
    fun removeVehicle_removesVehicle(){
        //Agregamos un nuevo vehículo a través del viewModel

        val observer = Observer<List<Vehicle>>{}

        try {

            viewModel.vehicleList.observeForever(observer)

            // When: Cuando probamos agregar un nuevo evento con nuestro ViewModel
            viewModel.removeVehicle(jetta)

            //Then: Entonces el evento fue disparado (eso provoca que no sea nulo y que tenga alguno de los estados:
            //      loading, success, error)
            val vehicles = viewModel.vehicleList.value

            assertThat(vehicles).doesNotContain(jetta)

        } finally {
            viewModel.vehicleList.removeObserver(observer) // eliminamos el observer para evitar memory leaks
        }
        viewModel.removeVehicle(jetta)
    }



}