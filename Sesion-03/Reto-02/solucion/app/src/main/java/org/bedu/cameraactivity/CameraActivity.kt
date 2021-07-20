package org.bedu.cameraactivity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Size
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.File
import java.util.concurrent.Executors

val PERMISSION_ID = 34

class CameraActivity : AppCompatActivity(){

    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        if(checkCameraPermission()){
            startCamera()
        } else{
            requestPermissions()
            capture_button.setOnClickListener {
                requestPermissions()
            }
        }

    }


    fun startCamera(){

        //Creamos la configuración para el preview de nuestra cámara
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(640, 480)) //ajustando resolucción
        }.build()

        //Creamos nuestro Preview
        val preview = Preview(previewConfig)

        // Cuando nuestro preview se actualice, recreamos nuestro layout
        preview.setOnPreviewOutputUpdateListener {

            // desatamos y volvemos a atar nuestro preview del TextureView
            val parent =  camera_preview.parent as ViewGroup
            parent.removeView(camera_preview)
            parent.addView(camera_preview, 0)

            camera_preview.surfaceTexture = it.surfaceTexture

        }

        val imageCapture = captureConfig()

        //Agregamos la función de Preview y de Captura de imagen al ciclo de vida de la cámara
        CameraX.bindToLifecycle(this, preview,imageCapture)
    }


    fun captureConfig(): ImageCapture{
        // Creando el objeto para crear el caso de uso de captura de imagen
        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .apply {
                setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
            }.build()

        // Creamos nuestro objeto de captura con su configuración
        val imageCapture = ImageCapture(imageCaptureConfig)


        //Capturar y guardar imagen cuando se de click al botón de captura
        capture_button.setOnClickListener {
            //Creando el archivo en la ruta
            val file = File(externalMediaDirs.first(),
                "${System.currentTimeMillis()}.jpg")

            //Tomamos la afoto y asignamos el listener
            imageCapture.takePicture(file, executor,
                object : ImageCapture.OnImageSavedListener {

                    //Enviamos el error a log y a un Toast
                    override fun onError(
                        imageCaptureError: ImageCapture.ImageCaptureError,
                        message: String,
                        exc: Throwable?
                    ) {
                        val msg = "Photo capture failed: $message"
                        Log.e("CameraXApp", msg, exc)
                        camera_preview.post {
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                    //Mostramos un Toast y un log de imagen guardada
                    override fun onImageSaved(file: File) {
                        val msg = "¡Imagen guardada!: ${file.absolutePath}"
                        Log.d("Camera", msg)
                        camera_preview.post {
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                })
        }

        return imageCapture
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                startCamera()
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