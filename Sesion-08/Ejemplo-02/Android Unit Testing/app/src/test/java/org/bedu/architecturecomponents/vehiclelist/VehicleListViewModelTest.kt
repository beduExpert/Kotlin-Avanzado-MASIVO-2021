package org.bedu.architecturecomponents.vehiclelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.bedu.architecturecomponents.data.FakeVehicleRepository
import org.bedu.architecturecomponents.data.local.Vehicle

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class VehicleListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    private lateinit var vehicleRepository: FakeVehicleRepository
    private lateinit var viewModel: VehicleListViewModel

    private val vento = Vehicle(model = "Vento",brand = "Volkswagen",platesNumber = "STF0321",isWorking = true)
    private val jetta = Vehicle(model = "Jetta",brand = "Volkswagen",platesNumber = "FBN6745",isWorking = true)
    private val tsuru = Vehicle(model = "Tsuru",brand = "Nissan",platesNumber = "RFG4583",isWorking = true)

    @Before
    fun setup(){
        vehicleRepository = FakeVehicleRepository()

        val vehicles = listOf(vento,jetta)

        vehicleRepository.populateVehicles(vehicles)
        viewModel = VehicleListViewModel(vehicleRepository)
    }

    @Test
    fun removeVehicle_removesVehicle() {
        val observer = Observer<List<Vehicle>>{}

        try {
            viewModel.vehicleList.observeForever(observer)

            // When
            viewModel.removeVehicle(jetta)

            //Then
            val vehicles = viewModel.vehicleList.value
            assertThat(vehicles).contains(vento)
            assertThat(vehicles).doesNotContain(jetta)

        } finally {
            viewModel.vehicleList.removeObserver(observer) // eliminamos el observer para evitar memory leaks
        }
        viewModel.removeVehicle(jetta)
    }
}


@OptIn(ExperimentalCoroutinesApi::class)
class CoroutineTestRule(private val dispatcher: CoroutineDispatcher = UnconfinedTestDispatcher() )
    : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }

}