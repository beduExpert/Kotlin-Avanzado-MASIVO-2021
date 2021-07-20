package org.bedu.gplocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object{
        const val PERMISSION_ID = 33
    }

    //Obeto que obtiene la localización
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //agregando un nuevo cliete de localización
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        btnLocate.setOnClickListener {
            getLocation() //get current position on gps and show it
        }
    }

    //Si tenemos permisos y la ubicación está habilitada, obtener la última localización
    @SuppressLint("MissingPermission") // no detecta bien que estamos requiriendo permisos
    private fun getLocation() {
        if (checkPermissions()) { //verificamos si tenemos permisos
            if (isLocationEnabled()) { //localizamos sólo si el GPS está encendido

                mFusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->

                   tvLatitude.text = location?.latitude.toString()
                    tvLongitude.text = location?.longitude.toString()

                }
            }
        } else{
            //si no se tiene permiso, pedirlo
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        if ( checkGranted(Manifest.permission.ACCESS_COARSE_LOCATION) &&
            checkGranted(Manifest.permission.ACCESS_FINE_LOCATION) ){
            return true
        }
        return false
    }

    //checa si el gps está apagado
    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    //Pedir los permisos requeridos para que funcione la localización
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    private fun checkGranted(permission: String): Boolean{
        return ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
    }

}
