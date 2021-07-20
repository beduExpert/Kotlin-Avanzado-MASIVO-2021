package org.bedu.roomvehicles.vehiclelist

import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.bedu.roomvehicles.R
import org.bedu.roomvehicles.data.FakeVehicleAndroidRepository
import org.bedu.roomvehicles.data.ServiceLocator
import org.bedu.roomvehicles.data.VehicleHelper
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
class VehicleListFragmentTest{

    @Volatile
    private lateinit var vehicleRepository: FakeVehicleAndroidRepository
        @VisibleForTesting set


    @Before
    fun setup(){
        vehicleRepository = FakeVehicleAndroidRepository()
        ServiceLocator.repository = vehicleRepository
    }

    @After
    fun close(){
        ServiceLocator.resetRepository()
    }

    @Test
    fun removeButton_RemovesVehicle()= runBlockingTest{

        val vehicles = listOf(
            VehicleHelper.vento,
            VehicleHelper.jetta
        )

        vehicleRepository.populateVehicles(vehicles)


        launchFragmentInContainer<VehicleListFragment>(null, R.style.Theme_RoomVehicles)


        Thread.sleep(1500)

        Espresso.onView(withId(R.id.list))
            .perform(
                RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                    ViewMatchers.hasDescendant(
                        ViewMatchers.withText("Jetta")
                    ),
                    itemAction(R.id.button_delete)
                ))


        assertThat(vehicleRepository.getVehicles().value).doesNotContain(VehicleHelper.jetta)
    }


    fun itemAction(viewId: Int) = object : ViewAction{
        override fun getConstraints() = null

        override fun getDescription() = "Click on action button"

        override fun perform(uiController: UiController?, view: View?) {
            val button = view?.findViewById<ImageButton>(R.id.button_delete)
            button?.performClick()
        }

    }

}