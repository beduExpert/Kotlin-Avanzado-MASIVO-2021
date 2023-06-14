package org.bedu.firebasenotifications


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import org.bedu.firebasenotifications.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        executeOrRequestPermission(this) {
            FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("Error", "Fetching FCM registration token failed", task.exception)
                    return@OnCompleteListener
                }

                val token = task.result

                Log.d("FCM_TOKEN",token)
                Toast.makeText(baseContext,"FCM token: $token", Toast.LENGTH_SHORT).show()
            })
        }
    }

}
