package org.bedu.architecturecomponents.vehiclelist

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.matcher.ViewMatchers.withId
import android.view.View
import android.widget.ImageButton
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Test
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

import org.bedu.architecturecomponents.R
import org.bedu.architecturecomponents.data.FakeVehicleRepository
import org.bedu.architecturecomponents.data.local.Vehicle
import org.bedu.roomvehicles.data.ServiceLocator
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class VehicleListFragmentTest {

    @get:Rule val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule val coroutineTestRule = CoroutineTestRule()

    private lateinit var vehicleRepository: FakeVehicleRepository

    private val vento: Vehicle = Vehicle(model = "Vento",brand = "Volkswagen",platesNumber = "STF0321",isWorking = true)
    private val jetta = Vehicle(model = "Jetta",brand = "Volkswagen",platesNumber = "FBN6745",isWorking = true)

    @Before
    fun setup(){
        vehicleRepository = FakeVehicleRepository()
        val vehicles = listOf(vento, jetta)
        vehicleRepository.populateVehicles(vehicles)
        ServiceLocator.repository = vehicleRepository
    }

    @After
    fun tearDown() {
        ServiceLocator.resetRepository()
    }

    @Test
    fun removeButton_RemovesVehicle()  {
        launchFragmentInContainer<VehicleListFragment>(null, R.style.Theme_ArchitectureComponents)

        Thread.sleep(1000)

        Espresso.onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(
                        ViewMatchers.withText("Jetta")
                    ),
                    itemAction(R.id.button_delete)
                ))

        Thread.sleep(1000)

        assertThat(vehicleRepository.getVehicles().value).doesNotContain(jetta)
        assertThat(vehicleRepository.getVehicles().value).contains(vento)
    }
}

fun itemAction(viewId: Int) = object : ViewAction {
    override fun getConstraints() = null

    override fun getDescription() = "Click on action button"

    override fun perform(uiController: UiController?, view: View?) {
        val button = view?.findViewById<ImageButton>(viewId)
        button?.performClick()
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