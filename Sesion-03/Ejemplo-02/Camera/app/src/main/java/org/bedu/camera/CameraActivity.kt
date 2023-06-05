package org.bedu.camera

import android.content.ContentValues
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.concurrent.futures.await
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.bedu.camera.databinding.ActivityCameraBinding
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity(){

    private lateinit var binding: ActivityCameraBinding
    private var imageCapture: ImageCapture? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraBinding.inflate(layoutInflater)

        setContentView(binding.root)


        lifecycleScope.launch {
            startCamera()
        }

        binding.captureButton.setOnClickListener {
            takePhoto()
        }
    }


    private suspend fun startCamera(){

        val cameraProvider = ProcessCameraProvider.getInstance(this).await()

        // Construimos el preview (aquí podemos hacer configuraciones)
        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(binding.cameraPreview.surfaceProvider)
            }

        // Seleccionamos la cámara trasera
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            // Atamos nuestra cámara al ciclo de vida de nuestro Activity
            cameraProvider.run {
                unbindAll()
                imageCapture = ImageCapture.Builder().build()
                bindToLifecycle(this@CameraActivity, cameraSelector, preview,  imageCapture)
            }

        } catch(exc: Exception) {
            Toast.makeText(this, "No se pudo hacer bind al lifecycle", Toast.LENGTH_SHORT).show()
        }
    }

    fun takePhoto() {
        val format = SimpleDateFormat("dd-MM-yyyyy-HH:mm:ss:SSS", Locale.US)
            .format(System.currentTimeMillis())
        // Create time stamped name and MediaStore entry.
        val name = "beduPhoto $format"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image") // La carpeta donde se guarda
            }
        }

        // Create output options object which contains file + metadata.
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            .build()

        // Set up image capture listener, which is triggered after photo has
        // been taken.
        imageCapture?.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(e: ImageCaptureException) {
                    Toast.makeText(
                        baseContext,
                        "Error al capturar imagen",
                        Toast.LENGTH_SHORT).show()
                    Log.e("CameraX",e.toString())
                }

                override fun onImageSaved(
                    output: ImageCapture.OutputFileResults
                ) {
                    Toast.makeText(
                        baseContext,
                        "La imagen ${output.savedUri} se guardó correctamente!",
                        Toast.LENGTH_SHORT).show()
                    Log.d("CameraX", output.savedUri.toString())
                }
            }
        )
    }
}
