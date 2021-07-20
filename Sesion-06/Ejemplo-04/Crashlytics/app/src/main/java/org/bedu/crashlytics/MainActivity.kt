package org.bedu.crashlytics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.crashlytics.ktx.setCustomKeys
import org.bedu.crashlytics.databinding.ActivityMainBinding
import java.lang.RuntimeException

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //throw RuntimeException("Ejemplo de crash")

        val crashlytics = FirebaseCrashlytics.getInstance()

        crashlytics.setUserId("Bedu-LmtvK4ge-Fqox-blRy")


        crashlytics.setCustomKey("email","manuel@bedu.org")
        crashlytics.setCustomKey("name","Manuel Bedu")
        crashlytics.setCustomKey("Edad", 23)

        crashlytics.setCustomKeys {
            key("Trabajo", "Developer")
            key("Bloqueado",false)
            key("Crédito",1350.23f)
        }

        binding.btnError.setOnClickListener{
            try {
                throw NullPointerException("Ejemplo de crash")
            } catch (ex: NullPointerException) {
                crashlytics.log("NullPointer Provocado para pruebas!")
                crashlytics.recordException(ex)
            }
        }
    }
}