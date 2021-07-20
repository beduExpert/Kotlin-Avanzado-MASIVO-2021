package org.bedu.gplocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        //Esta condicionante implica que se respondió una petición de permisos GPS
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                Log.d("Location","Location after granted OK!")
                getLocation()
            }
        }
    }

    //Si tenemos permisos y la ubicación está habilitada, obtener la última localización
    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions()) { //verificamos si tenemos permisos
            if (isLocationEnabled()) { //localizamos sólo si el GPS está encendido

                mFusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->

                    if(location!=null){
                        tvLatitude.text = location?.latitude.toString()
                        tvLongitude.text = location?.longitude.toString()
                    } else{
                        Toast.makeText(
                            this,
                            "Aún no se ha detectado una posición de localización",
                            Toast.LENGTH_SHORT)
                            .show()
                    }

                }
            } else{
                goToTurnLocation()
            }
        } else{
            //si no se tiene permiso, pedirlo
            requestPermissions()
        }
    }

    private fun goToTurnLocation(){
        Toast.makeText(this, "Debes prender el servicio de GPS", Toast.LENGTH_LONG).show()
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    private fun checkPermissions(): Boolean {
        if ( checkGranted(Manifest.permission.ACCESS_COARSE_LOCATION) &&
            checkGranted(Manifest.permission.ACCESS_COARSE_LOCATION) ){
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
