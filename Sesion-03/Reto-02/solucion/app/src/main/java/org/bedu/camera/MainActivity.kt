package org.bedu.camera

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import org.bedu.camera.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val PERMISSION_ID = 34
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOpenCamera.setOnClickListener {
            if(checkCameraPermission()){
                openCamera()
            } else{
                requestPermissions()
            }
        }

    }

    private fun openCamera(){
        val intent = Intent(this, CameraActivity::class.java)
        startActivity(intent)
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                openCamera()
            } else {
                Toast.makeText(this,"Aun requieres permiso", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkCameraPermission(): Boolean{
        return ActivityCompat
            .checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }


}
